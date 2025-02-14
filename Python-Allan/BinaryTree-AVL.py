class Node:
    def __init__(self, value):
        self.value = value
        self.left = None
        self.right = None
        self.height = 1

class AVLTree:
    def __init__(self):
        self.root = None

    def insert(self, value):
        self.root = self._insert_recursive(self.root, value)

    def _insert_recursive(self, node, value):
        if not node:
            return Node(value)
        
        if value < node.value:
            node.left = self._insert_recursive(node.left, value)
        else:
            node.right = self._insert_recursive(node.right, value)

        return self._balance(node)

    def search(self, value):
        return self._search_recursive(self.root, value)

    def _search_recursive(self, node, value):
        if not node:
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
        if not node:
            return None
        if value < node.value:
            node.left = self._remove_recusrive(node.left, value)
        elif value > node.value:
            node.right = self._remove_recusrive(node.right, value)
        else:
            if not node.left:
                return node.right
            if not node.right:
                return node.left
            
            temp = self._find_min(node.right)
            node.value = temp.value
            node.right = self._remove_recusrive(node.right, temp.value)

        return self._balance(node)
    
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
            print(node.value, end=" ")
            self._inorder_recursive(node.right)
    
    def _balance(self, node):
        if not node:
            return None
        
        node.height = 1 + max(self._get_height(node.left), self._get_height(node.right))
        balance_factor = self._get_balance(node)

        if balance_factor > 1 and self._get_balance(node.left) >= 0:
            return self._rotate_right(node)
        
        if balance_factor < -1 and self._get_balance(node.right) <= 0:
            return self._rotate_left(node)
        
        if balance_factor > 1 and self._get_balance(node.left) < 0:
            node.left = self._rotate_left(node.left)
            return self._rotate_right(node)
        
        if balance_factor < -1 and self._get_balance(node.right) > 0:
            node.right = self._rotate_right(node.right)
            return self._rotate_left(node)
        
        return node
    
    def _rotate_left(self, z):
        y = z.right
        T2 = y.left
        y.left = z
        z.right = T2
        z.height = 1 + max(self._get_height(z.left), self._get_height(z.right))
        y.height = 1 + max(self._get_height(y.left), self._get_height(y.right))
        return y
    
    def _rotate_right(self, z):
        y = z.left
        T3 = y.right
        y.right = z
        z.left = T3
        z.height = 1 + max(self._get_height(z.left), self._get_height(z.right))
        y.height = 1 + max(self._get_height(y.left), self._get_height(y.right))
        return y

    def _get_height(self, node):
        return node.height if node else 0

    def _get_balance(self, node):
        return self._get_height(node.left) - self._get_height(node.right) if node else 0