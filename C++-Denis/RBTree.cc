#include <iostream>
#include <iomanip>

using namespace std;

/*
    Aluno: Denis Almeida Ferreira
    Email: denis.almeida.ferreira@ccc.ufcg.edu.br
    Matrícula: 123210975
    Usuário no GitHub: DenisAlmeidaFerreira

    Implementação de uma Árvore Rubro Negro
*/

enum Color { RED, BLACK };

class RBTree {
private:
    struct Node {
        int value;
        Node *left, *right, *father;
        Color color;

        Node(int val) : value(val), left(nullptr), right(nullptr), father(nullptr), color(RED) {}
    };

    Node *root, *nil;

    void rotateLeft(Node *node) {
        Node *right = node->right;
        node->right = right->left;
        if (right->left != nil) right->left->father = node;
        right->father = node->father;
        if (node->father == nullptr) root = right;
        else if (node == node->father->left) node->father->left = right;
        else node->father->right = right;
        right->left = node;
        node->father = right;
    }

    void rotateRight(Node *node) {
        Node *left = node->left;
        node->left = left->right;
        if (left->right != nil) left->right->father = node;
        left->father = node->father;
        if (node->father == nullptr) root = left;
        else if (node == node->father->right) node->father->right = left;
        else node->father->left = left;
        left->right = node;
        node->father = left;
    }

    void balanceTree(Node *newNode) {
        while (newNode->father && newNode->father->color == RED) {
            Node *father = newNode->father;
            Node *parent = father->father;
            if (father == parent->right) {
                Node *uncle = parent->left;
                if (uncle->color == RED) {
                    uncle->color = BLACK;
                    father->color = BLACK;
                    parent->color = RED;
                    newNode = parent;
                } else {
                    if (newNode == father->left) {
                        newNode = father;
                        rotateRight(father);
                    }
                    father->color = BLACK;
                    parent->color = RED;
                    rotateLeft(parent);
                }
            } else {
                Node *uncle = parent->right;
                if (uncle->color == RED) {
                    uncle->color = BLACK;
                    father->color = BLACK;
                    parent->color = RED;
                    newNode = parent;
                } else {
                    if (newNode == father->right) {
                        newNode = father;
                        rotateLeft(father);
                    }
                    father->color = BLACK;
                    parent->color = RED;
                    rotateRight(parent);
                }
            }
        }
        root->color = BLACK;
    }

    Node* insertRec(Node *newNode, Node *current) {
        if (current == nil) return newNode;
        if (newNode->value < current->value) {
            current->left = insertRec(newNode, current->left);
            current->left->father = current;
        } else if (newNode->value > current->value) {
            current->right = insertRec(newNode, current->right);
            current->right->father = current;
        }
        return current;
    }

    void printTree(Node* root, int indent = 0) {
        if (root == nullptr || root == nil) return;

        printTree(root->right, indent + 4);

        cout << setw(indent) << " " << root->value << " (" << (root->color == RED ? "RED" : "BLACK") << ")\n";

        printTree(root->left, indent + 4);
    }

    void transplant(Node *target, Node *replacement) {
        if (target->father == nullptr) root = replacement;
        else if (target == target->father->left) target->father->left = replacement;
        else target->father->right = replacement;
        replacement->father = target->father;
    }

    Node* findMinimum(Node *node) {
        while (node->left != nil) node = node->left;
        return node;
    }

    void fixDeletion(Node *node) {
        while (node != root && node->color == BLACK) {
            if (node == node->father->left) {
                Node *sibling = node->father->right;
                if (sibling->color == RED) {
                    sibling->color = BLACK;
                    node->father->color = RED;
                    rotateLeft(node->father);
                    sibling = node->father->right;
                }
                if (sibling->left->color == BLACK && sibling->right->color == BLACK) {
                    sibling->color = RED;
                    node = node->father;
                } else {
                    if (sibling->right->color == BLACK) {
                        sibling->left->color = BLACK;
                        sibling->color = RED;
                        rotateRight(sibling);
                        sibling = node->father->right;
                    }
                    sibling->color = node->father->color;
                    node->father->color = BLACK;
                    sibling->right->color = BLACK;
                    rotateLeft(node->father);
                    node = root;
                }
            } else {
                Node *sibling = node->father->left;
                if (sibling->color == RED) {
                    sibling->color = BLACK;
                    node->father->color = RED;
                    rotateRight(node->father);
                    sibling = node->father->left;
                }
                if (sibling->right->color == BLACK && sibling->left->color == BLACK) {
                    sibling->color = RED;
                    node = node->father;
                } else {
                    if (sibling->left->color == BLACK) {
                        sibling->right->color = BLACK;
                        sibling->color = RED;
                        rotateLeft(sibling);
                        sibling = node->father->left;
                    }
                    sibling->color = node->father->color;
                    node->father->color = BLACK;
                    sibling->left->color = BLACK;
                    rotateRight(node->father);
                    node = root;
                }
            }
        }
        node->color = BLACK;
    }

public:
    RBTree() {
        nil = new Node(0);
        nil->color = BLACK;
        root = nil;
    }

    void insert(int value) {
        Node *newNode = new Node(value);
        newNode->left = nil;
        newNode->right = nil;
        root = insertRec(newNode, root);
        balanceTree(newNode);
    }

    void printTree() {
        printTree(root, 0);
    }

    void remove(int value) {
        Node *current = root, *successor, *fixNode;
        while (current != nil && current->value != value) {
            if (value < current->value) current = current->left;
            else current = current->right;
        }
        if (current == nil) return;

        successor = current;
        Color originalColor = successor->color;
        if (current->left == nil) {
            fixNode = current->right;
            transplant(current, current->right);
        } else if (current->right == nil) {
            fixNode = current->left;
            transplant(current, current->left);
        } else {
            successor = findMinimum(current->right);
            originalColor = successor->color;
            fixNode = successor->right;
            if (successor->father == current) fixNode->father = successor;
            else {
                transplant(successor, successor->right);
                successor->right = current->right;
                successor->right->father = successor;
            }
            transplant(current, successor);
            successor->left = current->left;
            successor->left->father = successor;
            successor->color = current->color;
        }
        delete current;
        if (originalColor == BLACK) fixDeletion(fixNode);
    }
};

int main() {
    RBTree rbTree;

    cout << "Inserindo valores..." << endl;
    rbTree.insert(10);
    rbTree.insert(20);
    rbTree.insert(30);
    rbTree.insert(40);
    rbTree.insert(50);
    rbTree.insert(25);

    cout << "\nÁrvore após inserções:" << endl;
    rbTree.printTree();

    cout << "\nRemovendo valores..." << endl;
    rbTree.remove(30);
    rbTree.remove(10);

    cout << "\nÁrvore após remoções:" << endl;
    rbTree.printTree();

    return 0;
}
