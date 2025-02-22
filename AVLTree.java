
/*
 * *** Zaki Khan / 400 001 ***
 *
 * This java file is a Java object implementing simple AVL Tree.
 * You are to complete the deleteElement method.
 *
 */

import java.lang.Math;

class Node {
    int value;
    int height;
    Node leftChild, rightChild;

    public Node(int data) {
        value = data;
        height = 0;
        leftChild = rightChild = null;
    }
}

class LUC_AVLTree {
    private Node rootNode;

    public LUC_AVLTree() { rootNode = null; }
    public void removeAll() { rootNode = null; }
    public boolean checkEmpty() { return rootNode == null; }
    public void insert(int value) { rootNode = insertElement(value, rootNode); }
    public void delete(int value) { rootNode = deleteElement(value, rootNode); }
    public String preorderTraversal() { return preorderTraversal(rootNode); }

    private boolean isTreeBalanced() { return isTreeBalanced(rootNode); }
    private boolean isBST() { return isBST(rootNode); }
    private int getHeight(Node node) { return node == null ? -1 : node.height; }
    private int getMaxHeight(int leftNodeHeight, int rightNodeHeight) {
        return Math.max(leftNodeHeight, rightNodeHeight);
    }

    private boolean isTreeBalanced(Node node) {
        if (node == null) return true;

        int leftSubTree = getHeight(node.leftChild) + 1;
        int rightSubTree = getHeight(node.rightChild) + 1;

        if (Math.abs(leftSubTree - rightSubTree) > 1) return false;

        return isTreeBalanced(node.leftChild) && isTreeBalanced(node.rightChild);
    }

    private boolean isBST(Node node) {
        if (node == null) return true;

        if ((node.leftChild != null && maxValue(node.leftChild) > node.value) ||
                (node.rightChild != null && minValue(node.rightChild) < node.value) ||
                !isBST(node.leftChild) || !isBST(node.rightChild)) {
            return false;
        }

        return true;
    }

    private int maxValue(Node node) {
        if (node == null) return Integer.MIN_VALUE;

        int value = node.value;
        int leftMax = maxValue(node.leftChild);
        int rightMax = maxValue(node.rightChild);

        return Math.max(value, Math.max(leftMax, rightMax));
    }

    private int minValue(Node node) {
        if (node == null) return Integer.MAX_VALUE;

        int value = node.value;
        int leftMin = minValue(node.leftChild);
        int rightMin = minValue(node.rightChild);

        return Math.min(value, Math.min(leftMin, rightMin));
    }

    private String preorderTraversal(Node node) {
        if (node == null) return "";

        return node.value + " " + preorderTraversal(node.leftChild) + preorderTraversal(node.rightChild);
    }

    private Node insertElement(int value, Node node) {
        if (node == null) {
            node = new Node(value);
            return node;
        }

        if (value < node.value) {
            node.leftChild = insertElement(value, node.leftChild);
            int bf = getBalanceFactor(node);

            if (Math.abs(bf) > 1) {
                if (value < node.leftChild.value) {
                    node = LLRotation(node);
                } else {
                    node = LRRotation(node);
                }
            }
        } else if (value > node.value) {
            node.rightChild = insertElement(value, node.rightChild);
            int bf = getBalanceFactor(node);

            if (Math.abs(bf) > 1) {
                if (value > node.rightChild.value) {
                    node = RRRotation(node);
                } else {
                    node = RLRotation(node);
                }
            }
        }

        node.height = getMaxHeight(getHeight(node.leftChild), getHeight(node.rightChild)) + 1;
        return node;
    }

    private Node deleteElement(int value, Node node) {
        if (node == null) {
            return node;
        }

        if (value < node.value) {
            node.leftChild = deleteElement(value, node.leftChild);
        } else if (value > node.value) {
            node.rightChild = deleteElement(value, node.rightChild);
        } else {
            if (node.leftChild == null || node.rightChild == null) {
                node = (node.leftChild == null) ? node.rightChild : node.leftChild;
            } else {
                Node temp = minValueNode(node.rightChild);
                node.value = temp.value;
                node.rightChild = deleteElement(temp.value, node.rightChild);
            }
        }

        if (node == null) {
            return node;
        }

        node.height = getMaxHeight(getHeight(node.leftChild), getHeight(node.rightChild)) + 1;
        int balance = getBalanceFactor(node);

        if (balance > 1) {
            if (getBalanceFactor(node.leftChild) >= 0) {
                return LLRotation(node);
            } else {
                return LRRotation(node);
            }
        }

        if (balance < -1) {
            if (getBalanceFactor(node.rightChild) <= 0) {
                return RRRotation(node);
            } else {
                return RLRotation(node);
            }
        }

        return node;
    }

    private int getBalanceFactor(Node node) {
        if (node == null) return 0;

        int leftSubTreeHeight = getHeight(node.leftChild) + 1;
        int rightSubTreeHeight = getHeight(node.rightChild) + 1;

        return leftSubTreeHeight - rightSubTreeHeight;
    }

    private Node minValueNode(Node node) {
        Node current = node;
        while (current.leftChild != null) {
            current = current.leftChild;
        }
        return current;
    }

    private Node LLRotation(Node x) {
        Node y = x.leftChild;
        x.leftChild = y.rightChild;
        y.rightChild = x;

        x.height = getMaxHeight(getHeight(x.leftChild), getHeight(x.rightChild)) + 1;
        y.height = getMaxHeight(getHeight(y.leftChild), getHeight(y.rightChild)) + 1;

        return y;
    }

    private Node LRRotation(Node x) {
        Node y = x.leftChild;
        Node z = x.leftChild.rightChild;
        y.rightChild = z.leftChild;
        x.leftChild = z.rightChild;
        z.leftChild = y;
        z.rightChild = x;

        x.height = getMaxHeight(getHeight(x.leftChild), getHeight(x.rightChild)) + 1;
        y.height = getMaxHeight(getHeight(y.leftChild), getHeight(y.rightChild)) + 1;
        z.height = getMaxHeight(getHeight(z.leftChild), getHeight(z.rightChild)) + 1;

        return z;
    }

    private Node RRRotation(Node x) {
        Node y = x.rightChild;
        x.rightChild = y.leftChild;
        y.leftChild = x;

        x.height = getMaxHeight(getHeight(x.leftChild), getHeight(x.rightChild)) + 1;
        y.height = getMaxHeight(getHeight(y.leftChild), getHeight(y.rightChild)) + 1;

        return y;
    }

    private Node RLRotation(Node x) {
        Node y = x.rightChild;
        Node z = x.rightChild.leftChild;
        y.leftChild = z.rightChild;
        x.rightChild = z.leftChild;
        z.rightChild = y;
        z.leftChild = x;

        x.height = getMaxHeight(getHeight(x.leftChild), getHeight(x.rightChild)) + 1;
        y.height = getMaxHeight(getHeight(y.leftChild), getHeight(y.rightChild)) + 1;
        z.height = getMaxHeight(getHeight(z.leftChild), getHeight(z.rightChild)) + 1;

        return z;
    }
}
