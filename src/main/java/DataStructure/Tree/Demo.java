package DataStructure.Tree;

import DataStructure.Tree.MyBinarySearchTree.BinarySearchTree;

public class Demo {
    public static void main(String[] args) {
        BinarySearchTree binarySearchTree = new BinarySearchTree();
        int[] arr = {4,2,6,1,3,5,7};
        for (int i = 0; i < arr.length; i++) {
            binarySearchTree.add(arr[i]);
        }
        binarySearchTree.decreaseSort();
        binarySearchTree.increaseSort();
        binarySearchTree.pre_Order();
        System.out.println();
        binarySearchTree.preOrder();
        binarySearchTree.levelOrder();
    }
}
