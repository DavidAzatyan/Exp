package Other.Data_Structure.MyBinarySearchTree;


import Other.Data_Structure.MyQueue.MyQueue;
import Other.Data_Structure.MyStack.MyStack;

public class MyBinarySearchTree {
    private Node root;

    public void add(Integer val) {
        root = addRecursion(root, val);
    }

    private Node addRecursion(Node current, Integer val) {
        if (current == null) {
            return new Node(val);
        }
        if (current.value < val) {
            current.right = addRecursion(current.right, val);
        }
        if (current.value > val) {
            current.left = addRecursion(current.left, val);
        }
        return current;
    }

    public void remove(Integer val) {
        root = removeRec(root, val);
    }

    private Node removeRec(Node current, Integer val) {
        if (current == null) {
            return null;
        }
        if (current.value < val) {
            current.right = removeRec(current.right, val);
        } else if (current.value > val) {
            current.left = removeRec(current.left, val);
        } else {
            if (current.left == null) {
                return current.right;
            } else if (current.right == null) {
                return current.left;
            }
            current.value = minValue(current.right);
            current.right = removeRec(current.right, current.value);
        }
        return current;
    }

    private Integer minValue(Node root) {
        Node temp = root;
        while (temp.left != null) {
            temp = temp.left;
        }
        return temp.value;
    }

    public boolean contains(Integer val) {
        return find(root, val) != null;
    }

    public Integer find(Integer val) {

        if (find(root, val) == null)
            return null;
        else
            return find(root, val).value;
//        Node current = root;
//
//        while (!current.value.equals(val)){
//
//            if(current.value > val){
//                current = current.left;
//            }
//            if(current.value < val){
//                current = current.right;
//            }
//
//            if(current == null){
//                return null;
//            }
//        }
//
//        return current.value;
    }

    private Node find(Node root, Integer val) {
        if (root == null) {
            return null;
        }
        if (root.value.equals(val)) {
            return root;
        }
        if (root.value > val) {
            return find(root.left, val);
        }
        return find(root.right, val);
    }

    public int min() {
        Node temp = root;
        while (temp.left != null) {
            temp = temp.left;
        }
        return temp.value;
    }

    public int max() {
        Node temp = root;
        while (temp.right != null) {
            temp = temp.right;
        }
        return temp.value;
    }

    public void inorder() {
        inorder(root);
    }

    private void inorder(Node root) {
        if (root != null) {
            inorder(root.left);
            System.out.print(root.value + " ");
            inorder(root.right);
        }
    }

    public void levelOrder() {
        BFS(root);
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

    public void postOrder(){
        postOrder(root);
    }

    private void postOrder(Node root){
        if(root != null){
            postOrder(root.left);
            postOrder(root.right);
            System.out.print(root.value + " ");
        }
    }

    public int height() {
        return maxDepth(root);
    }

    private int maxDepth(Node root) {
        if (root == null) {
            return -1;
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

    public void invert() {
        root = invert(root);
    }

    private Node invert(Node root) {
        if (root == null) {
            return null;
        }
        if (root.left != null) {
            root.left = invert(root.left);
        }
        if (root.right != null) {
            root.right = invert(root.right);
        }

        Node temp = root.right;
        root.right = root.left;
        root.left = temp;
        return root;

    }

}
