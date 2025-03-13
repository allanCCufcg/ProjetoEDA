class Node:
    def __init__(self, key):
        self.key = key
        self.left = None
        self.right = None

class SplayTree:
    def __init__(self):
        self.root = None

    def right_rotate(self, x):
        y = x.left
        x.left = y.right
        y.right = x
        return y

    def left_rotate(self, x):
        y = x.right
        x.right = y.left
        y.left = x
        return y

    def splay(self, root, key):
        if root is None or root.key == key:
            return root
        if key < root.key:
            if root.left is None:
                return root
            if key < root.left.key:
                root.left.left = self.splay(root.left.left, key)
                root = self.right_rotate(root)
            elif key > root.left.key:
                root.left.right = self.splay(root.left.right, key)
                if root.left.right is not None:
                    root.left = self.left_rotate(root.left)
            return root if root.left is None else self.right_rotate(root)
        else:
            if root.right is None:
                return root
            if key > root.right.key:
                root.right.right = self.splay(root.right.right, key)
                root = self.left_rotate(root)
            elif key < root.right.key:
                root.right.left = self.splay(root.right.left, key)
                if root.right.left is not None:
                    root.right = self.right_rotate(root.right)
            return root if root.right is None else self.left_rotate(root)

    def insert(self, key):
        if self.root is None:
            self.root = Node(key)
            return
        self.root = self.splay(self.root, key)
        if self.root.key == key:
            return
        new_node = Node(key)
        if key < self.root.key:
            new_node.right = self.root
            new_node.left = self.root.left
            self.root.left = None
        else:
            new_node.left = self.root
            new_node.right = self.root.right
            self.root.right = None
        self.root = new_node

    def search(self, key):
        self.root = self.splay(self.root, key)
        return self.root is not None and self.root.key == key

    def delete(self, key):
        if self.root is None:
            return
        self.root = self.splay(self.root, key)
        if self.root.key != key:
            return
        if self.root.left is None:
            self.root = self.root.right
        else:
            temp = self.root.right
            self.root = self.splay(self.root.left, key)
            self.root.right = temp
