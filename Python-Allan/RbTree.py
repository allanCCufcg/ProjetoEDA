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

    def transplant(self, u, v):
        if not u.parent:
            self.root = v
        elif u == u.parent.left:
            u.parent.left = v
        else:
            u.parent.right = v
        v.parent = u.parent

    def minimum(self, node):
        while node.left != self.NIL:
            node = node.left
        return node

    def remove(self, data):
        node = self.root
        z = self.NIL
        while node != self.NIL:
            if node.data == data:
                z = node
                break
            elif data < node.data:
                node = node.left
            else:
                node = node.right
        if z == self.NIL:
            print("Valor não encontrado na árvore.")
            return

        y = z
        y_original_color = y.color
        if z.left == self.NIL:
            x = z.right
            self.transplant(z, z.right)
        elif z.right == self.NIL:
            x = z.left
            self.transplant(z, z.left)
        else:
            y = self.minimum(z.right)
            y_original_color = y.color
            x = y.right
            if y.parent == z:
                x.parent = y
            else:
                self.transplant(y, y.right)
                y.right = z.right
                y.right.parent = y
            self.transplant(z, y)
            y.left = z.left
            y.left.parent = y
            y.color = z.color
        if y_original_color == 'B':
            self.fix_delete(x)

    def fix_delete(self, x):
        while x != self.root and x.color == 'B':
            if x == x.parent.left:
                sibling = x.parent.right
                if sibling.color == 'R':
                    sibling.color = 'B'
                    x.parent.color = 'R'
                    self.left_rotate(x.parent)
                    sibling = x.parent.right
                if sibling.left.color == 'B' and sibling.right.color == 'B':
                    sibling.color = 'R'
                    x = x.parent
                else:
                    if sibling.right.color == 'B':
                        sibling.left.color = 'B'
                        sibling.color = 'R'
                        self.right_rotate(sibling)
                        sibling = x.parent.right
                    sibling.color = x.parent.color
                    x.parent.color = 'B'
                    sibling.right.color = 'B'
                    self.left_rotate(x.parent)
                    x = self.root
            else:
                sibling = x.parent.left
                if sibling.color == 'R':
                    sibling.color = 'B'
                    x.parent.color = 'R'
                    self.right_rotate(x.parent)
                    sibling = x.parent.left
                if sibling.right.color == 'B' and sibling.left.color == 'B':
                    sibling.color = 'R'
                    x = x.parent
                else:
                    if sibling.left.color == 'B':
                        sibling.right.color = 'B'
                        sibling.color = 'R'
                        self.left_rotate(sibling)
                        sibling = x.parent.left
                    sibling.color = x.parent.color
                    x.parent.color = 'B'
                    sibling.left.color = 'B'
                    self.right_rotate(x.parent)
                    x = self.root
        x.color = 'B'

