package DataStructure.List.MyArrayList;

import DataStructure.List.MyList;

import java.util.ArrayList;

public class MyArrayList<T> implements MyList<T> {

    private int cursor;
    private static final int FACTOR = 5;
    public int capacity;
    private Object[] data;

    public MyArrayList() {
        capacity = FACTOR;
        data = new Object[capacity];
    }

    @Override
    public void add(T obj) {
        if (cursor == capacity - 1) {
            growArray();
        }
        data[cursor++] = obj;
    }

    private void growArray() {
        capacity += FACTOR;
        Object[] newData = new Object[capacity];
        System.arraycopy(data, 0, newData, 0, cursor);
        data = newData;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndex(index);
        return (T) data[index];
    }

    @Override
    public void remove(int index) {
        checkIndex(index);
        for (int i = index; i < cursor - 1; i++) {
            data[i] = data[i + 1];
        }
        cursor--;
    }

    @Override
    public void remove(T obj) {
        int index = indexOf(obj);
        remove(index);
    }

    private int indexOf(T obj) {
        for (int i = 0; i < cursor; i++) {
            if (data[i].equals(obj)) {
                return i;
            }
        }
        return -1;
    }

    public boolean contains(T obj) {
        return indexOf(obj) != -1;
    }

    @Override
    public int size() {
        return cursor;
    }

    private void checkIndex(int index) {
        if (index < 0) {
            throw new ArrayIndexOutOfBoundsException("index must be great or equal 0");
        } else if (index > cursor - 1) {
            throw new ArrayIndexOutOfBoundsException("index must be small than size");
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < cursor; i++) {
            if (i == cursor - 1) {
                result.append(data[i]);
                break;
            }
            result.append(data[i]);
            result.append(", ");
        }
        result.append("]");
        return result.toString();
    }
}
