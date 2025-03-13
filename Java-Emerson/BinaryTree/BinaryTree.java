package BinaryTree;

/*
    Aluno: Emerson Henrique Sulpino de Araújo
    Email: emerson.henrique.sulpino.araujo@ccc.ufcg.edu.br
    Matrícula: 123210141
    User do GitHub: Emerson349
*/

public class BinaryTree {
    private Node root; // Nó raiz da árvore

    // Método para inserir um valor na árvore
    public void insert(int value) {
        root = insertRec(value, root);
    }

    // Método recursivo para inserir um novo nó na posição correta
    private Node insertRec(int value, Node root) {
        if (root == null) {
            return new Node(value); // Cria um novo nó se a posição estiver vazia
        }
        if (value < root.value) {
            root.left = insertRec(value, root.left); // Insere na subárvore esquerda
        }
        if (value > root.value) {
            root.right = insertRec(value, root.right); // Insere na subárvore direita
        }
        return root;
    }

    // Método para buscar um valor na árvore
    public Node search(int value) {
        return searchRec(value, root);
    }

    // Método recursivo para buscar um nó na árvore
    private Node searchRec(int value, Node root) {
        if (root == null) {
            return null; // Retorna null se o nó não for encontrado
        }
        if (value < root.value) {
            return searchRec(value, root.left); // Busca na subárvore esquerda
        }
        if (value > root.value) {
            return searchRec(value, root.right); // Busca na subárvore direita
        }
        return root; // Retorna o nó encontrado
    }

    // Método para remover um valor da árvore
    public void remove(int value) {
        root = removeRec(value, root);
    }

    // Método recursivo para remover um nó da árvore
    private Node removeRec(int value, Node node) {
        if (node == null) {
            return null; // Retorna null se o nó não for encontrado
        }
        if (value < node.value) {
            node.left = removeRec(value, node.left); // Procura na subárvore esquerda
        } else if (value > node.value) {
            node.right = removeRec(value, node.right); // Procura na subárvore direita
        }
        else {
            // Caso 1: Nó folha (sem filhos)
            if (node.left == null && node.right == null) {
                return null;
            }
            // Caso 2: Nó com apenas um filho
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }
            // Caso 3: Nó com dois filhos
            Node temp = node.right;
            while (temp.left != null) {
                temp = temp.left; // Encontra o sucessor (menor valor da subárvore direita)
            }
            node.value = temp.value; // Substitui o valor pelo sucessor
            node.right = removeRec(temp.value, node.right); // Remove o nó sucessor
        }
        return node;
    }

    // Método para percorrer a árvore em ordem
    public void inOrder() {
        recInOrder(root);
    }

    // Método recursivo para percorrer a árvore em ordem
    private void recInOrder(Node root) {
        if (root != null) {
            recInOrder(root.left); // Visita a subárvore esquerda
            System.out.println(root.value); // Imprime o valor do nó atual
            recInOrder(root.right); // Visita a subárvore direita
        }
    }

    public class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }
}

