package DataStructure.Tree.MyBinarySearchTree;


import DataStructure.StackAndQueue.MyQueue;
import DataStructure.StackAndQueue.MyStack;

import java.util.*;

public class BinarySearchTree {

    private Node root;

    public void add(Integer value) {
        root = addRecursion(root, value);
    }

    private Node addRecursion(Node current, Integer value) {
        if (current == null) {
            return new Node(value);
        }
        if (current.value < value) {
            current.right = addRecursion(current.right, value);
        }
        if (current.value > value) {
            current.left = addRecursion(current.left, value);
        }
        return current;
    }

    public boolean contains(Integer value) {
        return find(root, value) != null;
    }

    private Node find(Node current, Integer value) {
        if (current == null) {
            return null;
        }
        if (current.value.equals(value)) {
            return current;
        }
        if (current.value < value) {
            return find(current.right, value);
        }
        return find(current.left, value);
    }

    public void remove(Integer value) {
        root = removeRecursion(root, value);
    }

    private Node removeRecursion(Node current, Integer value) {
        if (current == null) {
            return null;
        }
        if (value > current.value) {
            current.right = removeRecursion(current.right, value);
        } else if (value < current.value) {
            current.left = removeRecursion(current.left, value);
        } else {
            if (current.left == null) {
                return current.right;
            } else if (current.right == null) {
                return current.left;
            }
            current.value = minValue(current.right, value);
            current.right = removeRecursion(current.right, current.value);
        }
        return current;
    }

    private Integer minValue(Node current, Integer value) {
        Node node = current;
        while (node.left != null) {
            node = node.left;
        }
        return node.value;
    }

    public Integer min() {
        Node current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    public Integer max() {
        Node current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }

    public int height() {
        return maxDepth(root);
    }

    private int maxDepth(Node root) {
        if (root == null) {
            return 0;
        } else {
            int lD = maxDepth(root.left);
            int rD = maxDepth(root.right);

            if (lD > rD) {
                return lD + 1;
            } else {
                return rD + 1;
            }
        }
    }


    public void increaseSort() {
        increaseSort(root);
        System.out.println();
    }

    private void increaseSort(Node root) {
        if (root != null) {
            increaseSort(root.left);
            System.out.print(root.value + " ");
            increaseSort(root.right);
        }
    }

    public void decreaseSort() {
        decreaseSort(root);
        System.out.println();
    }

    private void decreaseSort(Node root) {
        if (root != null) {
            decreaseSort(root.right);
            System.out.print(root.value + " ");
            decreaseSort(root.left);
        }
    }

    public void levelOrder() {
        BFS(root);
        System.out.println();
    }

    private void BFS(Node root) {
        MyQueue<Node> myQueue = new MyQueue<>();
        myQueue.push(root);
        while (!myQueue.isEmpty()) {
            Node node = myQueue.pop();
            System.out.print(node.value + " ");
            if (node.left != null) {
                myQueue.push(node.left);
            }
            if (node.right != null) {
                myQueue.push(node.right);
            }
        }
    }

    public void preOrder() {
        DFS(root);
        System.out.println();
    }

    private void DFS(Node root) {
        MyStack<Node> myStack = new MyStack<>();
        myStack.push(root);
        while (!myStack.isEmpty()) {
            Node node = myStack.pop();
            System.out.print(node.value + " ");
            if (node.right != null) {
                myStack.push(node.right);
            }
            if (node.left != null) {
                myStack.push(node.left);
            }
        }
    }

    public void postOrder() {
        postOrder(root);
        System.out.println();
    }

    private void postOrder(Node root) {
        if (root != null) {
            postOrder(root.left);
            postOrder(root.right);
            System.out.print(root.value + " ");
        }
    }

    public void pre_Order(){
        preOrder(root);
    }

    private void preOrder(Node root){
        if(root != null){
            System.out.print(root.value + " ");
            preOrder(root.left);
            preOrder(root.right);
        }
    }
}
