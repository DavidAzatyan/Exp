package DataStructure.Tree.MyBinarySearchTree;

public class Node {
    Integer value;
    Node left;
    Node right;

    Node(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node(" + value + ")";
    }
}
