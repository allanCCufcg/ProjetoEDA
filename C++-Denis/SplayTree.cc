#include <iostream>
using namespace std;

/*
    Aluno: Denis Almeida Ferreira
    Email: denis.almeida.ferreira@ccc.ufcg.edu.br
    Matrícula: 123210975
    Usuário no GitHub: DenisAlmeidaFerreira

    Implementação de uma Árvore Splay
*/

class SplayTree {
private:
    struct Node {
        int value;
        Node *left, *right, *father;
        Node(int val) : value(val), left(nullptr), right(nullptr), father(nullptr) {}
    };
    
    Node* root;
    
    void rotateLeft(Node* node) {
        Node* right = node->right;
        if (!right) return;
        node->right = right->left;
        if (right->left) right->left->father = node;
        right->father = node->father;
        if (!node->father) root = right;
        else if (node == node->father->left) node->father->left = right;
        else node->father->right = right;
        right->left = node;
        node->father = right;
    }
    
    void rotateRight(Node* node) {
        Node* left = node->left;
        if (!left) return;
        node->left = left->right;
        if (left->right) left->right->father = node;
        left->father = node->father;
        if (!node->father) root = left;
        else if (node == node->father->right) node->father->right = left;
        else node->father->left = left;
        left->right = node;
        node->father = left;
    }
    
    void splay(Node* node) {
        while (node->father) {
            Node* father = node->father;
            Node* grandpa = father->father;
            if (!grandpa) {
                if (node == father->left) rotateRight(father);
                else rotateLeft(father);
            } else if ((node == father->left) == (father == grandpa->left)) {
                if (node == father->left) {
                    rotateRight(grandpa);
                    rotateRight(father);
                } else {
                    rotateLeft(grandpa);
                    rotateLeft(father);
                }
            } else {
                if (node == father->left) {
                    rotateRight(father);
                    rotateLeft(grandpa);
                } else {
                    rotateLeft(father);
                    rotateRight(grandpa);
                }
            }
        }
        root = node;
    }
    
    Node* insertRec(Node* node, int value) {
        if (!node) return new Node(value);
        if (value > node->value) {
            if (!node->right) {
                node->right = new Node(value);
                node->right->father = node;
                return node->right;
            }
            return insertRec(node->right, value);
        } else if (value < node->value) {
            if (!node->left) {
                node->left = new Node(value);
                node->left->father = node;
                return node->left;
            }
            return insertRec(node->left, value);
        }
        return node;
    }
    
    Node* searchRec(Node* node, int value) {
        if (!node || node->value == value) return node;
        if (value < node->value) return searchRec(node->left, value);
        return searchRec(node->right, value);
    }
    
    void transplant(Node* u, Node* v) {
        if (!u->father) root = v;
        else if (u == u->father->left) u->father->left = v;
        else u->father->right = v;
        if (v) v->father = u->father;
    }
    
    Node* findMin(Node* node) {
        while (node->left) node = node->left;
        return node;
    }
    
    void printTreeRec(Node* node, int level) {
        if (!node) return;
        printTreeRec(node->right, level + 1);
        for (int i = 0; i < level; i++) cout << "   ";
        cout << node->value << endl;
        printTreeRec(node->left, level + 1);
    }
    
public:
    SplayTree() : root(nullptr) {}
    
    void insert(int value) {
        Node* newNode = insertRec(root, value);
        if (newNode) splay(newNode);
    }
    
    void remove(int value) {
        Node* nodeToRemove = search(value);
        if (!nodeToRemove) return;
        if (!nodeToRemove->left) transplant(nodeToRemove, nodeToRemove->right);
        else if (!nodeToRemove->right) transplant(nodeToRemove, nodeToRemove->left);
        else {
            Node* successor = findMin(nodeToRemove->right);
            if (successor->father != nodeToRemove) {
                transplant(successor, successor->right);
                successor->right = nodeToRemove->right;
                successor->right->father = successor;
            }
            transplant(nodeToRemove, successor);
            successor->left = nodeToRemove->left;
            successor->left->father = successor;
        }
        if (nodeToRemove->father) splay(nodeToRemove->father);
        delete nodeToRemove;
    }
    
    Node* search(int value) {
        Node* node = searchRec(root, value);
        if (node) splay(node);
        return node;
    }
    
    void printTree() {
        printTreeRec(root, 0);
    }
};
