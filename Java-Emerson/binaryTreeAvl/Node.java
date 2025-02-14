package binaryTreeAvl;

public class Node {
    private Node left;
    private Node right;
    private int height;
    private int value;

    public Node(int value) {
        this.value = value;
        this.left = null;
        this.right = null;
        this.height = 1;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
