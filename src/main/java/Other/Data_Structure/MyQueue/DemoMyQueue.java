package Other.Data_Structure.MyQueue;

public class DemoMyQueue {
    public static void main(String[] args) {
        MyQueue<Integer> myQueue = new MyQueue<>();
        System.out.println(myQueue.isEmpty());
        myQueue.push(1);
        myQueue.push(2);
        myQueue.push(3);
        myQueue.push(4);
        System.out.println(myQueue.isEmpty());
        System.out.println(myQueue.pop());
        System.out.println(myQueue);
    }
}
