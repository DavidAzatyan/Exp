package Other.Data_Structure.MyLinkedList;

public class DemoMyLinkedList {
    public static void main(String[] args) {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        list.add(4);
        list.add(5);
        list.add(7);
//        System.out.println(list.size());
        for(int i = 0; i < list.size(); i++){
            System.out.println(list.get(i));
        }
//        list.remove(0);
        list.remove(Integer.valueOf(7));

        System.out.println(list);
    }
}
