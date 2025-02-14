package rbTree; // Incompleta

/*
    Aluno: Emerson Henrique Sulpino de Araújo
    Email: emerson.henrique.sulpino.araujo@ccc.ufcg.edu.br
    Matrícula: 123210141
    User do GitHub: Emerson349
*/

public class RbTree {
    private Node root; // Nó raiz da árvore
    private Node nil; // Nó NIL (representa folhas)

    public RbTree(int value) {
        // Inicializa o nó NIL para garantir estrutura válida da árvore
        nil = new Node(0);
        nil.setColor(false); // NIL sempre preto
        root = nil; // Inicialmente, a árvore está vazia
    }

    public void insert(int value) {
        Node newNode = new Node(value);
        newNode.setLeft(nil); // Novos nós apontam para NIL
        newNode.setRight(nil);
        newNode.setFather(null);

        root = insertRec(newNode, root); // Insere recursivamente
        balanceTree(newNode); // Balanceia a árvore após inserção
    }

    private Node insertRec(Node newNode, Node root) {
        if (root == nil) return newNode; // Caso base: inserção na folha

        if (newNode.getValue() < root.getValue()) {
            root.setLeft(insertRec(newNode, root.getLeft()));
            root.getLeft().setFather(root);
        } else if (newNode.getValue() > root.getValue()) {
            root.setRight(insertRec(newNode, root.getRight()));
            root.getRight().setFather(root);
        }
        return root;
    }

    private void balanceTree(Node newNode) {
        Node father;
        Node parent;

        while (newNode.getFather() != null && newNode.getFather().getColor()) {
            father = newNode.getFather();
            parent = father.getFather();

            if (father.equals(parent.getRight())) { // Caso o pai seja filho direito
                Node uncle = parent.getLeft();

                if (uncle.getColor()) { // Caso 1: tio vermelho
                    uncle.setColor(false);
                    father.setColor(false);
                    parent.setColor(true);
                    newNode = parent;
                } else {
                    if (newNode.equals(parent.getLeft())) { // Caso 2: rotação à direita
                        newNode = father;
                        rotateRight(parent);
                    }
                    father.setColor(false); // Caso 3: rotação à esquerda
                    parent.setColor(true);
                    rotateLeft(parent);
                }
            } else { // Caso simétrico (pai é filho esquerdo)
                Node uncle = parent.getRight();
                if (uncle.getColor()) {
                    uncle.setColor(false);
                    father.setColor(false);
                    parent.setColor(true);
                    newNode = parent;
                } else {
                    if (newNode.equals(parent.getRight())) {
                        newNode = father;
                        rotateLeft(parent);
                    }
                    father.setColor(false);
                    parent.setColor(true);
                    rotateRight(parent);
                }
            }
        }
        root.setColor(false); // Raiz sempre preta
    }

    private Node searchNode(int value, Node node) {
        if (node == nil) return nil; // Caso base: não encontrado
        if (value == node.getValue()) return node;
        return value < node.getValue() ? searchNode(value, node.getLeft()) : searchNode(value, node.getRight());
    }

    public void remove(int value) {
        Node nodeToRemove = searchNode(value, root);
        if (nodeToRemove == nil) return; // Se não encontrado, não faz nada
        deleteNode(nodeToRemove);
    }

    private void deleteNode(Node node) {
        Node x, temp = node;
        boolean tempColor = temp.getColor();

        if (node.getLeft() == nil) { // Caso 1: sem filho esquerdo
            x = node.getRight();
            transplant(node, node.getRight());
        } else if (node.getRight() == nil) { // Caso 2: sem filho direito
            x = node.getLeft();
            transplant(node, node.getLeft());
        } else { // Caso 3: nó com dois filhos
            temp = successor(node.getRight());
            tempColor = temp.getColor();
            x = temp.getRight();
        }
    }

    private Node successor(Node node) {
        while (node.getLeft() != nil) { // Encontra o menor nó na subárvore direita
            node = node.getLeft();
        }
        return node;
    }

    private void transplant(Node x, Node y) {
        if (x.getFather() == null) {
            root = y;
        } else if (x == x.getFather().getLeft()) {
            x.getFather().setLeft(y);
        } else {
            x.getFather().setRight(y);
        }
        y.setFather(x.getFather());
    }

    // Rotação a esquerda
    private void rotateLeft(Node node) {
        Node right = node.getRight();
        node.setRight(right.getLeft());
        if (right.getLeft() != null) {
            right.getLeft().setFather(node);
        }
        right.setFather(node.getFather());
        if (node.getFather() == null) {
            root = right;
        } else if (node.equals(node.getFather().getLeft())) {
            node.getFather().setLeft(right);
        } else {
            node.getFather().setRight(right);
        }
        right.setLeft(node);
        node.setFather(right);
    }

    // Rotação a direita
    private void rotateRight(Node node) {
        Node left = node.getLeft();
        node.setLeft(left.getRight());
        if (left.getRight() != null) {
            left.getRight().setFather(node);
        }
        left.setFather(node.getFather());
        if (node.getFather() == null) {
            root = left;
        } else if (node.equals(node.getFather().getRight())) {
            node.getFather().setRight(left);
        } else {
            node.getFather().setLeft(left);
        }
        left.setRight(node);
        node.setFather(left);
    }
}

