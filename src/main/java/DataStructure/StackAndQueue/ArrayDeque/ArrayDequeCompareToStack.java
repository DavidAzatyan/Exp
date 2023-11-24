package DataStructure.StackAndQueue.ArrayDeque;

import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Stack;

public class ArrayDequeCompareToStack {
    public static void main(String[] args) {
//        int size = 200_000_000;
//
//        ArrayDeque<Integer> arrayDeque = new ArrayDeque<>();
//        long startAdd = System.currentTimeMillis();
//        for (int i = 0; i < size; i++) {
//            arrayDeque.add(i);
//        }
//        long endAdd = System.currentTimeMillis();
//        System.out.println("Add el to arraydeque " + (endAdd - startAdd));
//
//        long startRemove = System.currentTimeMillis();
//        for (int i = 0; i < 150_000_000; i++) {
//            arrayDeque.pollLast();
//        }
//        long endRemove = System.currentTimeMillis();
//        System.out.println("Remove el from arraydeque " + (endRemove - startRemove));
//
//        Stack<Integer> stack = new Stack<>();
//        long startAdd1 = System.currentTimeMillis();
//        for (int i = 0; i < size; i++) {
//            stack.add(i);
//        }
//        long endAdd1 = System.currentTimeMillis();
//        System.out.println("Add el to stack " + (endAdd1 - startAdd1));
//
//        long startRemove1 = System.currentTimeMillis();
//        for (int i = 0; i < 150_000_000; i++) {
//            stack.pop();
//        }
//        long endRemove1 = System.currentTimeMillis();
//        System.out.println("Remove el from stack " + (endRemove1 - startRemove1));

        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(10);
        priorityQueue.add(1);
        priorityQueue.add(25);
        priorityQueue.add(0);
        priorityQueue.add(15);
        System.out.println(priorityQueue);



    }
}
