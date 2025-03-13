class Node:
    def __init__(self, data, color='R'):
        self.data = data
        self.color = color
        self.left = None
        self.right = None
        self.parent = None

class RedBlackTree:
    def __init__(self):
        self.NIL = Node(0, 'B')
        self.root = self.NIL

    def insert(self, data):
        new_node = Node(data)
        new_node.left = self.NIL
        new_node.right = self.NIL
        self.root = self._insert_recursive(self.root, new_node)
        self.fix_insert(new_node)

    def _insert_recursive(self, current, new_node):
        if current == self.NIL:
            return new_node
        if new_node.data < current.data:
            current.left = self._insert_recursive(current.left, new_node)
            current.left.parent = current
        else:
            current.right = self._insert_recursive(current.right, new_node)
            current.right.parent = current
        return current

    def fix_insert(self, node):
        while node.parent and node.parent.color == 'R':
            if node.parent == node.parent.parent.left:
                uncle = node.parent.parent.right
                if uncle.color == 'R':
                    node.parent.color = 'B'
                    uncle.color = 'B'
                    node.parent.parent.color = 'R'
                    node = node.parent.parent
                else:
                    if node == node.parent.right:
                        node = node.parent
                        self.left_rotate(node)
                    node.parent.color = 'B'
                    node.parent.parent.color = 'R'
                    self.right_rotate(node.parent.parent)
            else:
                uncle = node.parent.parent.left
                if uncle.color == 'R':
                    node.parent.color = 'B'
                    uncle.color = 'B'
                    node.parent.parent.color = 'R'
                    node = node.parent.parent
                else:
                    if node == node.parent.left:
                        node = node.parent
                        self.right_rotate(node)
                    node.parent.color = 'B'
                    node.parent.parent.color = 'R'
                    self.left_rotate(node.parent.parent)
        self.root.color = 'B'

    def left_rotate(self, node):
        temp = node.right
        node.right = temp.left
        if temp.left != self.NIL:
            temp.left.parent = node
        temp.parent = node.parent
        if not node.parent:
            self.root = temp
        elif node == node.parent.left:
            node.parent.left = temp
        else:
            node.parent.right = temp
        temp.left = node
        node.parent = temp

    def right_rotate(self, node):
        temp = node.left
        node.left = temp.right
        if temp.right != self.NIL:
            temp.right.parent = node
        temp.parent = node.parent
        if not node.parent:
            self.root = temp
        elif node == node.parent.right:
            node.parent.right = temp
        else:
            node.parent.left = temp
        temp.right = node
        node.parent = temp

    def inorder(self):
        self._inorder_recursive(self.root)

    def _inorder_recursive(self, node):
        if node != self.NIL:
            self._inorder_recursive(node.left)
            print(node.data, node.color, end=' ')
            self._inorder_recursive(node.right)
