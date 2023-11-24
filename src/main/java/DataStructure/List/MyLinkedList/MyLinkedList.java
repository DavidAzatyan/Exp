package DataStructure.List.MyLinkedList;

import DataStructure.List.MyList;

public class MyLinkedList<T> implements MyList<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T obj) {
        if (head == null) {
            head = tail = new Node<>(obj);
            size++;
            return;
        }
        tail.next = new Node<>(obj);
        tail = tail.next;
        size++;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp.value;
    }

    @Override
    public void remove(int index) {
        checkIndex(index);
        if (index == 0) {
            head = head.next;
        } else {
            Node<T> node = head;
            int i;
            for (i = 0; i < index - 1; i++) {
                node = node.next;
            }
            node.next = node.next.next;
            if (i == size - 2) {
                tail = node;
            }
        }
        size--;
    }

    @Override
    public void remove(T obj) {
        int index = indexOf(obj);
        remove(index);
    }

    public int indexOf(T obj) {
        Node<T> temp = head;
        for (int i = 0; i < size; i++) {
            if (temp.value.equals(obj)) {
                return i;
            }
            temp = temp.next;
        }
        return -1;
    }

    public boolean contains(T obj) {
        return indexOf(obj) != -1;
    }

    @Override
    public int size() {
        return size;
    }

    private void checkIndex(int index) {
        if (index < 0) {
            throw new ArrayIndexOutOfBoundsException();
        } else if (index >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                result.append(node.value);
                break;
            }
            result.append(node.value);
            result.append(", ");
            node = node.next;
        }
        result.append("]");
        return result.toString();
    }
}
