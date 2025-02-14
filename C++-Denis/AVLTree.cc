#include <iostream>
using namespace std;

/*
    Aluno: Denis Almeida Ferreira
    Email: denis.almeida.ferreira@ccc.ufcg.edu.break
    Matrícula: 123210975
    Usuário no GitHub: DenisAlmeidaFerreira

    Implementação de uma Árvore AVL
*/

// Estrutura para representar um nó da árvore AVL
struct Node {
    int data;
    Node* left;
    Node* right;
    int height;

    // Construtor para inicializar um nó com um valor
    Node(int value) {
        data = value;
        left = right = nullptr;
        height = 1;
    }
};

// Classe para a Árvore AVL
class AVLTree {
public:
    Node* root; // Raiz da árvore

    AVLTree() {
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

    // Retorna a altura de um nó específico
    int getHeight(Node* node) {
        return node ? node->height : 0;
    }

    // Retorna o fator de balanço da árvore
    int getBalanceFactor() {
        return getBalanceFactorRec(root);
    }

    // Função auxiliar para calcular o fator de balanço
    int getBalanceFactorRec(Node* node) {
        return node ? getHeight(node->left) - getHeight(node->right) : 0;
    }

    // Rotaciona um nó para a direita
    Node* rotateRight(Node* y) {
        Node* x = y->left;
        Node* T2 = x->right;
        x->right = y;
        y->left = T2;
        y->height = max(getHeight(y->left), getHeight(y->right)) + 1;
        x->height = max(getHeight(x->left), getHeight(x->right)) + 1;
        return x;
    }

    // Rotaciona um nó para a esquerda
    Node* rotateLeft(Node* x) {
        Node* y = x->right;
        Node* T2 = y->left;
        y->left = x;
        x->right = T2;
        x->height = max(getHeight(x->left), getHeight(x->right)) + 1;
        y->height = max(getHeight(y->left), getHeight(y->right)) + 1;
        return y;
    }

    // Inserção de um valor na árvore
    Node* insert(int value) {
        return insertRec(root, value);
    }

    // Função recursiva para inserir um nó na árvore
    Node* insertRec(Node* node, int value) {
        if (node == nullptr) return new Node(value);
        if (value < node->data) node->left = insertRec(node->left, value);
        else node->right = insertRec(node->right, value);

        node->height = 1 + max(getHeight(node->left), getHeight(node->right));
        int balance = getBalanceFactorRec(node);
        
        if (balance > 1 && value < node->left->data) return rotateRight(node);
        if (balance < -1 && value > node->right->data) return rotateLeft(node);
        if (balance > 1 && value > node->left->data) {
            node->left = rotateLeft(node->left);
            return rotateRight(node);
        }
        if (balance < -1 && value < node->right->data) {
            node->right = rotateRight(node->right);
            return rotateLeft(node);
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
        while (current->left != nullptr) current = current->left;
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
            if (!node->left || !node->right) {
                Node* temp = node->left ? node->left : node->right;
                delete node;
                return temp;
            }
            Node* temp = minValueNodeRec(node->right);
            node->data = temp->data;
            node->right = removeRec(node->right, temp->data);
        }

        node->height = 1 + max(getHeight(node->left), getHeight(node->right));
        int balance = getBalanceFactorRec(node);
        
        if (balance > 1 && getBalanceFactorRec(node->left) >= 0) return rotateRight(node);
        if (balance > 1 && getBalanceFactorRec(node->left) < 0) {
            node->left = rotateLeft(node->left);
            return rotateRight(node);
        }
        if (balance < -1 && getBalanceFactorRec(node->right) <= 0) return rotateLeft(node);
        if (balance < -1 && getBalanceFactorRec(node->right) > 0) {
            node->right = rotateRight(node->right);
            return rotateLeft(node);
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
        return getBalanceFactor() <= 1 && isBalancedRec(node->left) && isBalancedRec(node->right);
    }
};

int main() {
    AVLTree tree;
    tree.root = tree.insert(50);
    tree.root = tree.insert(30);
    tree.root = tree.insert(70);
    tree.root = tree.insert(20);
    tree.root = tree.insert(40);
    tree.root = tree.insert(60);
    tree.root = tree.insert(80);
    tree.root = tree.insert(90);
    tree.root = tree.insert(100);
    tree.root = tree.insert(110);
    tree.root = tree.insert(105);
    tree.root = tree.insert(120);

    cout << "Altura da Arvore: " << tree.height() << endl;
    cout << "Numero de Nos: " << tree.countNodes() << endl;
    cout << "Arvore balanceada? " << (tree.isBalanced() ? "Sim" : "Nao") << endl;

    cout << "Percurso Em-Ordem: ";
    tree.inOrder();
    cout << endl;

    tree.root = tree.remove(80);
    cout << "Percurso Em-Ordem apos remocao de 80: ";
    tree.inOrder();
    cout << endl;

    tree.root = tree.remove(50);
    cout << "Percurso Em-Ordem apos remocao de 50: ";
    tree.inOrder();
    cout << endl;
    
    return 0;
}