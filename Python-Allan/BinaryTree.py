class Node:
    def __init__(self, value):
        self.value = value
        self.left = None
        self.right = None

class BinaryTree:
    def __init__(self):
        self.root = None

    def insert(self, value):
        if self.root is None:
            self.root = Node(value)
        else:
            self.insert_recursive(self.root, value)

    def insert_recursive(self, node, value):
        if value < node.value:
            if node.left is None:
                node.left = Node(value)
            else:
                self.insert_recursive(node.left, value)
        else:
            if node.right is None:
                node.right = Node(value)
            else:
                self.insert_recursive(node.right, value)

    def search(self, value):
        return self._search_recursive(self.root, value)

    def _search_recursive(self, node, value):
        if node is None:
            return False
        if node.value == value:
            return True
        
        if value < node.value:
            return self._search_recursive(node.left, value)
        else:
            return self._search_recursive(node.right, value)

    def remove(self, value):
        self.root = self._remove_recusrive(self.root, value)

    def _remove_recusrive(self, node, value):
        if node is None:
            return None
        if value < node.value:
            node.left = self._remove_recusrive(node.left, value)
        elif value > node.value:
            node.right = self._remove_recusrive(node.right, value)
        else:
            if node.left is None:
                return node.right
            if node.right is None:
                return node.left
            
            successor = self._find_min(node.right)
            node.value = successor.value
            node.right = self._remove_recusrive(node.right, successor.value)

        return node
    
    def _find_min(self, node):
        while node.left is not None:
            node = node.left
        return node
    
    def inorder_traversal(self):
        self._inorder_recursive(self.root)
        print()

    def _inorder_recursive(self, node):
        if node:
            self._inorder_recursive(node.left)
            print(node.value, end='' )
            self._inorder_recursive(node.right)