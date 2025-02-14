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
        if (value < root.getValue()) {
            root.setLeft(insertRec(value, root.getLeft())); // Insere na subárvore esquerda
        }
        if (value > root.getValue()) {
            root.setRight(insertRec(value, root.getRight())); // Insere na subárvore direita
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
        if (value < root.getValue()) {
            return searchRec(value, root.getLeft()); // Busca na subárvore esquerda
        }
        if (value > root.getValue()) {
            return searchRec(value, root.getRight()); // Busca na subárvore direita
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
        if (value < node.getValue()) {
            node.setLeft(removeRec(value, node.getLeft())); // Procura na subárvore esquerda
        } else if (value > node.getValue()) {
            node.setRight(removeRec(value, node.getRight())); // Procura na subárvore direita
        }
        else {
            // Caso 1: Nó folha (sem filhos)
            if (node.getLeft() == null && node.getRight() == null) {
                return null;
            }
            // Caso 2: Nó com apenas um filho
            if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            }
            // Caso 3: Nó com dois filhos
            Node temp = node.getRight();
            while (temp.getLeft() != null) {
                temp = temp.getLeft(); // Encontra o sucessor (menor valor da subárvore direita)
            }
            node.setValue(temp.getValue()); // Substitui o valor pelo sucessor
            node.setRight(removeRec(temp.getValue(), node.getRight())); // Remove o nó sucessor
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
            recInOrder(root.getLeft()); // Visita a subárvore esquerda
            System.out.println(root.getValue()); // Imprime o valor do nó atual
            recInOrder(root.getRight()); // Visita a subárvore direita
        }
    }
}

