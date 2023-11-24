package DataStructure.StackAndQueue.ArrayDeque;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ArrayDequeCompareToLinkedList {
    public static void main(String[] args) {
        int size = 50_000_000;
        Queue<Integer> arrayDeque = new ArrayDeque<>();
        long startAdd = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            arrayDeque.add(i);
        }
        long endAdd = System.currentTimeMillis();
        System.out.println("Add el to arraydeque " + (endAdd - startAdd));

        long startRemove = System.currentTimeMillis();
        for (int i = 0; i < 10_000_000; i++) {
            arrayDeque.poll();
        }
        long endRemove = System.currentTimeMillis();
        System.out.println("Remove el from arraydeque " + (endRemove - startRemove));


        ArrayList<Integer> linkedList = new ArrayList<>();
        long startAdd1 = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            linkedList.add(i);
        }
        long endAdd1 = System.currentTimeMillis();
        System.out.println("Add el to linkedList " + (endAdd1 - startAdd1));

        long startRemove1 = System.currentTimeMillis();
        for (int i = 0; i < 10_000_000; i++) {
            linkedList.remove(linkedList.size() - 1);
        }
        long endRemove1 = System.currentTimeMillis();
        System.out.println("Remove el from linkedList " + (endRemove1 - startRemove1));
    }
}
