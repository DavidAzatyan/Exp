package Other.Data_Structure.MyStack;

public class DemoMyStack {
    public static void main(String[] args) {
        MyStack<Integer> myStack = new MyStack<>();
        System.out.println(myStack.isEmpty());
        myStack.push(1);
        myStack.push(2);
        myStack.push(3);
        myStack.push(4);
        System.out.println(myStack.isEmpty());
        System.out.println(myStack.pop());
        System.out.println(myStack);
    }
}
