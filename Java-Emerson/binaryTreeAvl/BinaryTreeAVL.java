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
        return (node == null) ? 0 : node.height;
    }

    // Atualiza a altura de um nó
    private void updateHeight(Node node) {
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    // Calcula o fator de balanceamento de um nó
    private int getBalanceFactor(Node node) {
        return (node == null) ? 0 : (getHeight(node.left) - getHeight(node.right));
    }

    // Insere um valor na árvore
    public void insert(int value) {
        root = insertRec(value, root);
    }

    // Método recursivo para inserir um nó sempre balanceando a árvore
    private Node insertRec(int value, Node root) {
        if (root == null) return new Node(value);
        if (value < root.value)
            root.left = insertRec(value, root.left);
        else if (value > root.value)
            root.right = insertRec(value, root.right);
        else
            return root;

        // Verifica o Balanceamento atual após uma inserção
        updateHeight(root);
        int balanceFactor = getBalanceFactor(root);

        // Realiza rotações para manter a árvore balanceada
        if (balanceFactor > 1) {
            // Caso 1 Rotação simples a direita
            if (value < root.left.value) {
                return rotateRight(root);
            }
            // Caso 2 Rotação Dupla a Direita
            else if (value > root.left.value) {
                root.left = rotateLeft(root.left);
                return rotateRight(root);
            }
        }
        else if (balanceFactor < -1) {
            // Caso 1 Rotação simples a esquerda
            if (value > root.right.value) {
                return rotateLeft(root);
            }
            // Caso 2 Rotação dupla a esquerda
            else if (value < root.right.value) {
                root.right = rotateRight(root.right);
                return rotateLeft(root);
            }
        }

        return root;
    }

    // Remove um valor da árvore
    public void remove(int value) {
        root = removeRec(value, root);
    }

    // Método recursivo para remover um nó
    private Node removeRec(int value, Node root) {
        if (root == null) return null;
        if (value < root.value) {
            root.left = removeRec(value, root.left);
        } else if (value > root.value) {
            root.right = removeRec(value, root.right);
        }
        else {
            // Caso 1: Nó sem filhos
            if (root.left == null && root.right == null) {
                return null;
            }
            // Caso 2: Nó com apenas um filho
            if (root.left == null && root.right != null) {
                return root.right;
            } else if (root.right == null && root.left != null) {
                return root.left;
            }
            // Caso 3: Nó com dois filhos (pega o sucessor)
            Node temp = root.right;
            while (temp.left != null) {
                temp = temp.left;
            }
            root.value = temp.value;
            root.right = removeRec(temp.value, root.right);
        }

        updateHeight(root);
        int balanceFactor = getBalanceFactor(root);

        // Realiza rotações para manter o balanceamento
        if (balanceFactor > 1) {
            // Rotação simples a direita
            if (getBalanceFactor(root.left) >= 0) {
                return rotateRight(root);
            } else {
                // Rotação dupla a direita
                root.left = rotateLeft(root.left);
                return rotateRight(root);
            }
        }
        else if (balanceFactor < -1) {
            // Rotação simples a esquerda
            if (getBalanceFactor(root.right) >= 0) {
                return rotateLeft(root);
            }
            // Rotação dupla a esquerda
            else {
                root.right = rotateRight(root.right);
                return rotateLeft(root);
            }
        }

        return root;
    }

    // Procura e retorna um determinado valor na árvore
    public Node search(int value) {
        return searchRec(root, value);
    }

    private Node searchRec(Node node, int value) {
        if (node == null) return null;
        if (node.value == value) return node;
        else if (value < node.value) return searchRec(node.left, value);
        else return searchRec(node.right, value);
    }

    // Método de rotação a esquerda
    private Node rotateLeft(Node root) {
        Node newRoot = root.right;
        Node subTree = newRoot.left;

        newRoot.left = root;
        root.right = subTree;

        updateHeight(root);
        updateHeight(newRoot);

        return newRoot;
    }

    // Método de rotação a direita
    private Node rotateRight(Node root) {
        Node newRoot = root.left;
        Node subTree = newRoot.right;

        newRoot.right = root;
        root.left = subTree;

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
            recInOrder(root.left);
            System.out.println(root.value);
            recInOrder(root.right);
        }
    }

    public class Node {
        public Node left;
        public Node right;
        public int height;
        public int value;

        public Node(int value) {
            this.value = value;
            this.left = null;
            this.right = null;
            this.height = 1;
        }
    }
}
