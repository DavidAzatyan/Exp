package DataStructure.StackAndQueue;

import DataStructure.StackAndQueue.Circular_Queue.CircularQueue;
import Other.A;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Main {
    public static void main(String[] args) {
        CircularQueue queue = new CircularQueue(3);

        System.out.println(queue.enQueue(1));
        System.out.println(queue.enQueue(2));
        System.out.println(queue.enQueue(5));

//        System.out.println(queue.deQueue());



//        System.out.println(queue.deQueue());
        System.out.println(1%3);
        System.out.println(1%9);
        System.out.println(queue);
    }
}
