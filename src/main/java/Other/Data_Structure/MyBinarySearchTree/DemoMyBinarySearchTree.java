package Other.Data_Structure.MyBinarySearchTree;


public class DemoMyBinarySearchTree {
    public static void main(String[] args) {
        MyBinarySearchTree tree = new MyBinarySearchTree();
        tree.add(8);
        tree.add(5);
        tree.add(6);
        tree.add(7);
        tree.add(15);
        tree.add(9);
        tree.add(16);
        tree.add(1);
        tree.add(0);
        tree.add(-9);

        tree.postOrder();
        System.out.println();
        tree.preOrder();


    }
}
