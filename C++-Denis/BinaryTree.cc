#include <iostream>

using namespace std;

/*a
    Aluno: Denis Almeida Ferreira
    Email: denis.almeida.ferreira@ccc.ufcg.edu.br
    Matrícula: 123210975
    Usuário no GitHub: DenisAlmeidaFerreira

    Implementação de uma Árvore Binária em C++
*/

// Estrutura para representar um nó da árvore binária
struct Node {
    int data;
    Node* left;
    Node* right;

    // Construtor para inicializar um nó com um valor
    Node(int value) {
        data = value;
        left = right = nullptr;
    }
};

// Classe para a Árvore Binária
class BinaryTree {
public:
    Node* root; // Raiz da árvore

    BinaryTree() {
        root = nullptr;
    }

    // Retorna a altura da árvore
    int height() {
        return heightRec(root);
    }

    // Função recursiva para calcular a altura de um nó
    int heightRec(Node* node) {
        if (node == nullptr) return 0;
        return 1 + max(heightRec(node->left), heightRec(node->right));
    }

    // Retorna o número total de nós na árvore
    int countNodes() {
        return countNodesRec(root);
    }

    // Função recursiva para contar os nós
    int countNodesRec(Node* node) {
        if (node == nullptr) return 0;
        return 1 + countNodesRec(node->left) + countNodesRec(node->right);
    }

    // Inserção de um valor na árvore
    Node* insert(int value) {
        return insertRec(root, value);
    }

    // Função recursiva para inserir um nó na árvore
    Node* insertRec(Node* node, int value) {
        if (node == nullptr) {
            return new Node(value);
        }
        if (value < node->data) {
            node->left = insertRec(node->left, value);
        } else {
            node->right = insertRec(node->right, value);
        }
        return node;
    }

    // Retorna o nó com o menor valor na árvore
    Node* minValueNode() {
        return minValueNodeRec(root);
    }

    // Função recursiva para encontrar o menor valor
    Node* minValueNodeRec(Node* node) {
        Node* current = node;
        while (current && current->left != nullptr)
            current = current->left;
        return current;
    }

    // Retorna o nó com o maior valor na árvore
    Node* maxValueNode() {
        return maxValueNodeRec(root);
    }

    // Função recursiva para encontrar o maior valor
    Node* maxValueNodeRec(Node* node) {
        Node* current = node;
        while (current && current->right != nullptr)
            current = current->right;
        return current;
    }

    // Remove um nó da árvore
    Node* remove(int value) {
        return removeRec(root, value);
    }

    // Função recursiva para remover um nó
    Node* removeRec(Node* node, int value) {
        if (node == nullptr) return node;
    
        if (value < node->data) node->left = removeRec(node->left, value);
        else if (value > node->data) node->right = removeRec(node->right, value);
        else {
            if (node->left == nullptr) {
                Node* temp = node->right;
                delete node;
                return temp;
            }
            if (node->right == nullptr) {
                Node* temp = node->left;
                delete node;
                return temp;
            }
            Node* temp = minValueNodeRec(node->right);
            node->data = temp->data;
            node->right = removeRec(node->right, temp->data);
        }
    return node;
    }

    // Busca um valor na árvore
    bool search(int value) {
        return searchRec(root, value);
    }

    // Função recursiva para buscar um valor
    bool searchRec(Node* node, int value) {
        if (node == nullptr) {
            return false;
        }
        if (node->data == value) {
            return true;
        }
        if (value < node->data) {
            return searchRec(node->left, value);
        }
        return searchRec(node->right, value);
    }

    void preOrder() {
        return preOrderRec(root);
    }

    void preOrderRec(Node* node) {
        if (node != nullptr) {
            cout << node->data << " ";
            preOrderRec(node->left);
            preOrderRec(node->right);
        }
    }

    // Percurso Em-Ordem (esquerda, raiz, direita)
    void inOrder() {
        return inOrderRec(root);
    }

    // Função Recursiva para percurso Em-Ordem
    void inOrderRec(Node* node) {
        if (node != nullptr) {
            inOrderRec(node->left);
            cout << node->data << " ";
            inOrderRec(node->right);
        }
    }

    void postOrder() {
        return postOrderRec(root);
    }

    void postOrderRec(Node* node) {
        if (node != nullptr) {
            postOrderRec(node->left);
            postOrderRec(node->right);
            cout << node->data << " ";
        }
    }

    // Verifica se a árvore está balanceada
    bool isBalanced() {
        return isBalancedRec(root);
    }

    // Função recursiva para verificar se a árvore está balanceada
    bool isBalancedRec(Node* node) {
        if (node == nullptr) return true;
        int lh = heightRec(node->left);
        int rh = heightRec(node->right);
        return abs(lh - rh) <= 1 && isBalancedRec(node->left) && isBalancedRec(node->right);
    }
};
