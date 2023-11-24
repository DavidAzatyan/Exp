package Other.Data_Structure.MyLinkedList;

public class Main {
    public static void main(String[] args) {
        MyLinkedList<Integer> myLinkedList = new MyLinkedList<>();
        myLinkedList.add(1);
        myLinkedList.add(2);
        myLinkedList.add(3);
        myLinkedList.add(4);
//        myLinkedList.remove(Integer.valueOf(4));
        myLinkedList.add(5);
        System.out.println(myLinkedList);
    }
}
