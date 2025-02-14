package binaryTreeAvl;

/*
    Aluno: Emerson Henrique Sulpino de Araújo
    Email: emerson.henrique.sulpino.araujo@ccc.ufcg.edu.br
    Matrícula: 123210141
    User do GitHub: Emerson349
*/

public class BinaryTreeAVL {
    private Node root; // Raiz da árvore AVL

    public BinaryTreeAVL() {
        this.root = null;
    }

    // Retorna a altura de um nó
    private int getHeight(Node node) {
        return (node == null) ? 0 : node.getHeight();
    }

    // Insere um valor na árvore
    public void insert(int value) {
        root = insertRec(value, root);
    }

    // Remove um valor da árvore
    public void remove(int value) {
        root = removeRec(value, root);
    }

    // Atualiza a altura de um nó
    private void updateHeight(Node node) {
        node.setHeight(1 + Math.max(getHeight(node.getLeft()), getHeight(node.getRight())));
    }

    // Calcula o fator de balanceamento de um nó
    private int getBalanceFactor(Node node) {
        return (node == null) ? 0 : (getHeight(node.getLeft()) - getHeight(node.getRight()));
    }

    // Método recursivo para inserir um nó sempre balanceando a árvore
    private Node insertRec(int value, Node root) {
        if (root == null) return new Node(value);
        if (value < root.getValue())
            root.setLeft(insertRec(value, root.getLeft()));
        else if (value > root.getValue())
            root.setRight(insertRec(value, root.getRight()));
        else
            return root;

        // Verifica o Balanceamento atual após uma inserção
        updateHeight(root);
        int balanceFactor = getBalanceFactor(root);

        // Realiza rotações para manter a árvore balanceada
        if (balanceFactor > 1) {
            // Caso 1 Rotação simples a direita
            if (value < root.getLeft().getValue()) {
                return rotateRight(root);
            }
            // Caso 2 Rotação Dupla a Direita
            else if (value > root.getLeft().getValue()) {
                root.setLeft(rotateLeft(root.getLeft()));
                return rotateRight(root);
            }
        }
        else if (balanceFactor < -1) {
            // Caso 1 Rotação simples a direita
            if (value > root.getRight().getValue()) {
                return rotateLeft(root);
            }
            // Caso 2 Rotação dupla a direita
            else if (value < root.getRight().getValue()) {
                root.setRight(rotateRight(root.getRight()));
                return rotateLeft(root);
            }
        }

        return root;
    }

    // Método recursivo para remover um nó
    private Node removeRec(int value, Node root) {
        if (root == null) return null;
        if (value < root.getValue()) {
            root.setLeft(removeRec(value, root.getLeft()));
        } else if (value > root.getValue()) {
            root.setRight(removeRec(value, root.getRight()));
        }
        else {
            // Caso 1: Nó sem filhos
            if (root.getLeft() == null && root.getRight() == null) {
                return null;
            }
            // Caso 2: Nó com apenas um filho
            if (root.getLeft() == null && root.getRight() != null) {
                return root.getRight();
            } else if (root.getRight() == null && root.getLeft() != null) {
                return root.getLeft();
            }
            // Caso 3: Nó com dois filhos (pega o sucessor)
            Node temp = root.getRight();
            while (temp.getLeft() != null) {
                temp = temp.getLeft();
            }
            root.setValue(temp.getValue());
            root.setRight(removeRec(temp.getValue(), root.getRight()));
        }

        updateHeight(root);
        int balanceFactor = getBalanceFactor(root);

        // Realiza rotações para manter o balanceamento
        if (balanceFactor > 1) {
            // Rotação simples a direita
            if (getBalanceFactor(root.getLeft()) >= 0) {
                return rotateRight(root);
            } else {
                // Rotação dupla a direita
                root.setLeft(rotateLeft(root.getLeft()));
                return rotateRight(root);
            }
        }
        else if (balanceFactor < -1) {
            // Rotação simples a esquerda
            if (getBalanceFactor(root.getRight()) >= 0) {
                return rotateLeft(root);
            }
            // Rotação dupla a esquerda
            else {
                root.setRight(rotateRight(root.getRight()));
                return rotateLeft(root);
            }
        }

        return root;
    }

    // Método de rotação a esquerda
    private Node rotateLeft(Node root) {
        Node newRoot = root.getRight();
        Node subTree = newRoot.getLeft();

        newRoot.setLeft(root);
        root.setRight(subTree);

        updateHeight(root);
        updateHeight(newRoot);

        return newRoot;
    }

    // Método de rotação a direita
    private Node rotateRight(Node root) {
        Node newRoot = root.getLeft();
        Node subTree = newRoot.getRight();

        newRoot.setRight(root);
        root.setLeft(subTree);

        updateHeight(root);
        updateHeight(newRoot);

        return newRoot;
    }

    // Percorre a árvore em ordem
    public void inOrder() {
        recInOrder(root);
    }

    private void recInOrder(Node root) {
        if (root != null) {
            recInOrder(root.getLeft());
            System.out.println(root.getValue());
            recInOrder(root.getRight());
        }
    }
}

