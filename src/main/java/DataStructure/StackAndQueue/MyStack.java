package DataStructure.StackAndQueue;

import DataStructure.List.MyArrayList.MyArrayList;

import java.util.EmptyStackException;

public class MyStack<T> {
    private MyArrayList<T> data = new MyArrayList<>();

    public void push(T obj) {
        data.add(obj);
    }

    public T pop() {
        if(isEmpty()){
            throw new EmptyStackException();
        }
        T t = data.get(data.size() - 1);
        data.remove(data.size() - 1);
        return t;
    }

    public boolean isEmpty(){
        return data.size() == 0;
    }

    public int size(){
        return data.size();
    }

    @Override
    public String toString(){
        return data.toString();
    }
}
