package splayTree;

/*
    Aluno: Emerson Henrique Sulpino de Araújo
    Email: emerson.henrique.sulpino.araujo@ccc.ufcg.edu.br
    Matrícula: 123210141
    User do GitHub: Emerson349
    */

public class SplayTree {
    private Node root;

    public SplayTree() {
        this.root = null;
    }

    // Método coração de uma splayTree. Move um nó para a raiz
    public void splay(Node node) {
        while (node.father != null) {
            Node father = node.father;
            Node grandpa = father.father;
            if (grandpa == null) { // Caso 1 "Zig": nó é filho da raiz
                if (node == father.left) rotateRight(father);
                else rotateLeft(father);
            }
            else if (node == father.left && father == grandpa.left) { // Caso 2 "Zig-Zig": ambos são filhos esquerdos
                rotateRight(grandpa); // Rotação à direita no avô
                rotateRight(father); // Rotação à direita no pai
            } else if (node == father.right && father == grandpa.right) { // Caso 2 "Zig-Zig": ambos são filhos direitos
                rotateLeft(grandpa); // Rotação à esquerda no avô
                rotateLeft(father); // Rotação à esquerda no pai
            } else { // Caso 3 "Zig-Zag": rotação em zigue-zague
                if (node == father.left) {
                    rotateRight(father); // Rotação à direita no pai
                    rotateLeft(grandpa); // Rotação à esquerda no avô
                } else {
                    rotateLeft(father); // Rotação à esquerda no pai
                    rotateRight(grandpa); // Rotação à direita no avô
                }
            }
        }
        root = node; // o nó se torna raiz
    }

    public void insert(int value) {
        Node newNode = insertRec(root, value); // Insere o nó e retorna o nó inserido
        if (newNode != null) {
            splay(newNode); // Realiza o splay no nó recém-inserido
        }
    }

    private Node insertRec(Node node, int value) {
        if (node == null) {
            Node newNode = new Node(value); // Cria o novo nó
            if (root == null) root = newNode; // Se a árvore estiver vazia, o novo nó é a raiz
            return newNode; // Retorna o nó recém-inserido
        }
        // Insere na subárvore esquerda ou direita
        if (value > node.value) {
            if (node.right == null) {
                node.right = new Node(value); // Insere o novo nó à direita
                node.right.father = node; // Define o pai do novo nó
                return node.right; // Retorna o nó recém-inserido
            } else
                return insertRec(node.right, value); // Continua a busca na subárvore direita
        } else if (value < node.value) {
            if (node.left == null) {
                node.left = new Node(value); // Insere o novo nó à esquerda
                node.left.father = node; // Define o pai do novo nó
                return node.left; // Retorna o nó recém-inserido
            } else
                return insertRec(node.left, value); // Continua a busca na subárvore esquerda
        } else {
            // Valor já existe na árvore, não faz nada
            return node; // Retorna o nó atual
        }
    }

    public void remove(int valor) {
        Node nodeToRemove = search(valor);
        if (nodeToRemove == null) return;

        // Separa a árvore em duas subárvores
        if (nodeToRemove.left == null) // Caso 1: Nó não tem filho esquerdo
            trasnplant(nodeToRemove, nodeToRemove.right);
         else if (nodeToRemove.right == null) // Caso 2: Nó não tem filho direito
            trasnplant(nodeToRemove, nodeToRemove.left);
         else { // Caso 3: Nó tem dois filhos
            Node sucessor = findMin(nodeToRemove.right); // Encontra o sucessor
            if (sucessor.father != nodeToRemove) { // Se o sucessor não for filho direto do nó a ser removido
                trasnplant(sucessor, sucessor.right);
                sucessor.right = nodeToRemove.right;
                sucessor.right.father = sucessor;
            }
            // Substitui o nó a ser removido pelo sucessor
            trasnplant(nodeToRemove, sucessor);
            sucessor.left = nodeToRemove.left;
            sucessor.left.father = sucessor;
        }

        // Realiza o splay no pai do nó removido (ou no sucessor, se houver)
        if (nodeToRemove.father != null) {
            splay(nodeToRemove.father);
        }
    }

    private void trasnplant(Node u, Node v) {
        if (u.father == null)
            root = v;
        else if (u == u.father.left)
            u.father.left = v;
        else
            u.father.right = v;
        if (v != null) v.father = u.father; // Atualiza o pai de v
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // pecorre àrvore em busca do nó referente ao ao valor
    public Node search(int value) {
        Node node = searchRec(root, value);
        if (node != null)
            splay(node); // coloca o nó recentemente acessado na raiz
        return node;
    }

    private Node searchRec(Node node, int value) {
        if (node == null) return null;
        if (node.value == value) return node;
        else if (value < node.value) return searchRec(node.left, value);
        else return searchRec(node.right, value);
    }

    // Rotação à esquerda
    private void rotateLeft(Node node) {
        Node right = node.right;
        node.right = right.left;
        if (right.left != null) {
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
        if (left.right != null) { // Atualiza o pai do filho direito do filho esquerdo
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

    // método para testes visuais
    public void imprimirArvore() {
        imprimirArvoreRecursivo(root, 0);
    }

    private void imprimirArvoreRecursivo(Node node, int nivel) {
        if (node == null)
            return;
        imprimirArvoreRecursivo(node.right, nivel + 1);

        for (int i = 0; i < nivel; i++) {
            System.out.print("   ");
        }
        System.out.println(node.value);
        imprimirArvoreRecursivo(node.left, nivel + 1);
    }


    class Node {
        int value;
        Node left;
        Node right;
        Node father;

        public Node(int value) {
            this.value = value;
            this.left = null;
            this.right = null;
            this.father = null;
        }
    }
}
