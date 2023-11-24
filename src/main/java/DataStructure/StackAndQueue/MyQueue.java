package DataStructure.StackAndQueue;

import DataStructure.List.MyLinkedList.MyLinkedList;

import java.util.EmptyStackException;

public class MyQueue<T> {
    private MyLinkedList<T> data = new MyLinkedList<>();

    public void push(T obj) {
        data.add(obj);
    }

    public T pop() {
        if(isEmpty()){
            throw new RuntimeException("MyQueue is empty");
        }
        T t = data.get(0);
        data.remove(0);
        return t;
    }

    public int size() {
        return data.size();
    }

    public boolean isEmpty(){
        return data.size() == 0;
    }

    @Override
    public String toString(){
        return data.toString();
    }
}
