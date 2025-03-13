package rbTree;

/*
    Aluno: Emerson Henrique Sulpino de Araújo
    Email: emerson.henrique.sulpino.araujo@ccc.ufcg.edu.br
    Matrícula: 123210141
    User do GitHub: Emerson349
*/

public class RbTree {
    private Node root; // Nó raiz da árvore
    private Node nil; // Nó NIL (representa folhas)

    public RbTree() {
        nil = new Node(0);
        nil.color = false; // NIL sempre preto
        root = nil; // Inicialmente, a árvore está vazia
    }

    public void insert(int value) {
        Node newNode = new Node(value);
        newNode.left = nil; // Novos nós apontam para NIL
        newNode.right = nil;
        newNode.father = null;

        root = insertRec(newNode, root); // Insere recursivamente
        balanceTree(newNode); // Balanceia a árvore após inserção
    }

    private Node insertRec(Node newNode, Node current) {
        if (current == nil) return newNode; // Caso base: inserção na folha

        if (newNode.value < current.value) {
            current.left = insertRec(newNode, current.left);
            current.left.father = current;
        } else if (newNode.value > current.value) {
            current.right = insertRec(newNode, current.right);
            current.right.father = current;
        }
        return current;
    }

    // Requilibrar arvore após inserção de um nó
    private void balanceTree(Node newNode) {
        while (newNode.father != null && newNode.father.color) {
            Node father = newNode.father;
            Node parent = father.father;

            if (father == parent.right) { // Caso o pai seja filho direito
                Node uncle = parent.left;

                if (uncle.color) { // Caso 1: tio vermelho
                    uncle.color = false;
                    father.color = false;
                    parent.color = true;
                    newNode = parent;
                } else {
                    if (newNode == parent.left) { // Caso 2: rotação à direita
                        newNode = father;
                        rotateRight(father);
                    }
                    father.color = false; // Caso 3: rotação à esquerda
                    parent.color = true;
                    rotateLeft(parent);
                }
            } else { // Caso simétrico (pai é filho esquerdo)
                Node uncle = parent.right;
                if (uncle.color) {
                    uncle.color = false;
                    father.color = false;
                    parent.color = true;
                    newNode = parent;
                } else {
                    if (newNode == parent.right) {
                        newNode = father;
                        rotateLeft(father);
                    }
                    father.color = false;
                    parent.color = true;
                    rotateRight(parent);
                }
            }
        }
        root.color = false; // Raiz sempre preta
    }

    private Node searchNode(int value, Node node) {
        if (node == nil) return nil; // Caso base: não encontrado
        if (value == node.value) return node;
        return value < node.value ? searchNode(value, node.left) : searchNode(value, node.right);
    }

    public void remove(int value) {
        Node nodeToRemove = searchNode(value, root);
        if (nodeToRemove == nil) return; // Se não encontrado, não faz nada
        deleteNode(nodeToRemove);
    }

    private void deleteNode(Node node) {
        Node x, y = node;
        boolean yOriginalColor = y.color;

        if (node.left == nil) { // Caso 1: sem filho esquerdo
            x = node.right;
            transplant(node, node.right);
        } else if (node.right == nil) { // Caso 2: sem filho direito
            x = node.left;
            transplant(node, node.left);
        } else { // Caso 3: nó com dois filhos
            y = successor(node.right);
            yOriginalColor = y.color;
            x = y.right;
            if (y.father == node) {
                x.father = y;
            } else {
                transplant(y, y.right);
                y.right = node.right;
                y.right.father = y;
            }
            transplant(node, y);
            y.left = node.left;
            y.left.father = y;
            y.color = node.color;
        }
        if (!yOriginalColor) {
            balanceDelete(x); // Chama o método de balanceamento da remoção
        }
    }

    private Node successor(Node node) {
        while (node.left != nil) { // Encontra o menor nó na subárvore direita
            node = node.left;
        }
        return node;
    }

    // substitui uma subárvore enraizada em um nó u por uma subárvore enraizada em um nó v
    private void transplant(Node u, Node v) {
        if (u.father == null)
            root = v;
        else if (u == u.father.left)
            u.father.left = v;
        else
            u.father.right = v;
        if (v != null) v.father = u.father;
    }

    // Reequilibrar a arvore após a remoção de um nó
    private void balanceDelete(Node x) {
        while (x != root && !x.color) {
            if (x == x.father.left) { // Caso 1: x é filho esquerdo
                Node w = x.father.right; // irmão vai ser o filho direito
                if (w.color) { // Caso 1.1 o irmão é vermelho
                    w.color = false;
                    x.father.color = true;
                    rotateLeft(x.father);
                    w = x.father.right;
                }
                if (!w.left.color && !w.right.color) { // Caso 1.2 ambos filhos do irmão são pretos
                    w.color = true;
                    x = x.father;
                } else { // Caso 1.3 Pelo menos um dos filhos do irmão não é preto
                    if (!w.right.color) {
                        w.left.color = false;
                        w.color = true;
                        rotateRight(w);
                        w = x.father.right;
                    }
                    w.color = x.father.color;
                    x.father.color = false;
                    w.right.color = false;
                    rotateLeft(x.father);
                    x = root;
                }
            } else { // Caso 2: x é filho direito
                Node w = x.father.left; // então o irmão será filho esquerdo
                // O caso 2 vai ter operações simétricas ao caso 1, mas, com rotações e verificações invertidas
                if (w.color) {
                    w.color = false;
                    x.father.color = true;
                    rotateRight(x.father);
                    w = x.father.left;
                }
                if (!w.right.color && !w.left.color) {
                    w.color = true;
                    x = x.father;
                } else {
                    if (!w.left.color) {
                        w.right.color = false;
                        w.color = true;
                        rotateLeft(w);
                        w = x.father.left;
                    }
                    w.color = x.father.color;
                    x.father.color = false;
                    w.left.color = false;
                    rotateRight(x.father);
                    x = root;
                }
            }
        }
        x.color = false;
    }

    // Rotação à esquerda
    private void rotateLeft(Node node) {
        Node right = node.right;
        node.right = right.left;
        if (right.left != nil) {
            right.left.father = node; // Atualiza o pai do filho esquerdo do filho direito
        }
        right.father = node.father;
        if (node.father == null) {
            root = right; // Se 'node' era raiz, o filho direito vira raiz
        } else if (node == node.father.left) {
            node.father.left = right; // Filho direito vira filho esquerdo do pai de 'node'
        } else {
            node.father.right = right; // Filho direito vira filho direito do pai de 'node'
        }
        right.left = node;
        node.father = right;
    }

    // Rotação à direita
    private void rotateRight(Node node) {
        Node left = node.left;
        node.left = left.right;
        if (left.right != nil) { // Atualiza o pai do filho direito do filho esquerdo
            left.right.father = node;
        }
        left.father = node.father;
        if (node.father == null) {
            root = left; // Se 'node' era raiz, o filho esquerdo vira raiz
        } else if (node == node.father.right) {
            node.father.right = left; // Filho esquerdo vira filho direito do pai de 'node'
        } else {
            node.father.left = left; // Filho esquerdo vira filho esquerdo do pai de 'node'
        }
        left.right = node;
        node.father = left;
    }

    // Método que usei para testes, mas é uma boa representação visual da àrvore
    public void printTree() {
        printTree(root, 0);
    }

    private void printTree(Node node, int level) {
        if (node == nil) {
            return;
        }
        printTree(node.right, level + 1);
        for (int i = 0; i < level; i++) {
            System.out.print("    ");
        }
        System.out.println(node.value + " (" + (node.color ? "RED" : "BLACK") + ")");
        printTree(node.left, level + 1);
    }

    // Classe Node
    private class Node {
        int value;
        Node left, right, father;
        boolean color; // true para vermelho, false para preto

        public Node(int value) {
            this.value = value;
            this.color = true; // Novos nós são sempre vermelhos
        }
    }
}

