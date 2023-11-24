package Other.Data_Structure.MyLinkedList;


//public class MyLinkedList<T> implements MyList<T> {
//
//    private Node<T> first;
//    private Node<T> last;
//    private int size;
//
//    @Override
//    public void add(T obj) {
//        if(first == null){
//            first = new Node<>(obj);
//            last = first;
//            size++;
//            return;
//        }
//        last.next = new Node<>(obj);
//        last = last.next;
//        size++;
//    }
//
//    @Override
//    public T get(int index) {
//        checkIndex(index);
//        Node<T> temp = first;
//        for(int i = 0; i < index; i++){
//            temp = temp.next;
//        }
//        return temp.value;
//    }
//
//    @Override
//    public void remove(int index) {
//        checkIndex(index);
//        if(index == 0){
//            first = first.next;
//            size--;
//            return;
//        }
//        Node<T> temp = first;
//        for(int i = 0; i < index - 1; i++){
//            temp = temp.next;
//        }
//        temp.next = temp.next.next;
//        size--;
//    }
//
//    @Override
//    public boolean remove(T obj) {
//        if(first.value.equals(obj)){
//            first = first.next;
//            size--;
//            return true;
//        }
//        if(find(obj) != null) {
//            Node<T> node = find(obj);
//            node.next = node.next.next;
//            size--;
//            return true;
//        }
//        return false;
//    }
//
//    private Node<T> find(T obj){
//        Node<T> temp = first;
//        for(int i = 0; i < size; i++){
//            if(temp.next.value.equals(obj)){
//                return temp;
//            }
//            temp = temp.next;
//        }
//        return temp;
//    }
//
//    @Override
//    public int size() {
//        return size;
//    }
//
//    private void checkIndex(int index){
//        if(index < 0){
//            throw new IndexOutOfBoundsException("Index must be >= 0");
//        }
//        if(index >= size){
//            throw new IndexOutOfBoundsException();
//        }
//    }
//
//    @Override
//    public String toString() {
//        Node<T> temp = first;
//        StringBuilder result = new StringBuilder("[");
//        for(int i = 0; i < size; i++){
//            if(temp.next == null){
//                result.append(temp.value);
//                break;
//            }
//            result.append(temp.value);
//            result.append(",");
//            temp = temp.next;
//
//        }
//        result.append("]");
//        return result.toString();
//    }
//}


import Other.Data_Structure.MyList.MyList;

public class MyLinkedList<T> implements MyList<T> {

    private Node<T> first;
    private Node<T> last;
    private int size;


    @Override
    public void add(T obj) {
        if (first == null) {
            first = new Node<>(obj);
            last = first;
            size++;
            return;
        }
        last.next = new Node<>(obj);
        last = last.next;
        size++;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> temp = first;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp.value;
    }

    public void set(int index, T obj) {
        checkIndex(index);
        Node<T> temp = first;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        temp.value = obj;
    }

    @Override
    public void remove(int index) {
        checkIndex(index);
        if (index == 0) {
            first = first.next;
            size--;
            return;
        }
        Node<T> temp = first;
        for (int i = 0; i < index - 1; i++) {
            temp = temp.next;
        }
        if (index == size - 1) {
            temp.next = temp.next.next;
            last = temp;
            size--;
        } else {
            temp.next = temp.next.next;
            size--;
        }
    }

    @Override
    public boolean remove(T obj) {
        Node<T> temp = first;
        for (int i = 0; i < size; i++) {
            if (temp.value.equals(obj)) {
                remove(i);
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    private void checkIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    public boolean contains(T obj) {
        Node<T> temp = first;
        for (int i = 0; i < size; i++) {
            if (temp.value.equals(obj)) {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        Node<T> temp = first;
        for (int i = 0; i < size; i++) {
            if (temp.next == null) {
                result.append(temp.value);
                result.append("]");
                break;
            }
            result.append(temp.value);
            result.append(", ");
            temp = temp.next;
        }
        return result.toString();
    }
}