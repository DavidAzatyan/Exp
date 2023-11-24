package Other.Data_Structure.MyArrayList;

import Other.Data_Structure.MyList.MyList;

public class MyArrayList<T> implements MyList<T> {

    private Object[] data;
    private int cursor;
    private final int FACTOR = 5;
    private int capacity;

    public MyArrayList() {
        capacity = FACTOR;
        data = new Object[capacity];
        cursor = 0;
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
    public void remove(int index) {
        checkIndex(index);
        for (int i = index; i < cursor - 1; i++) {
            data[i] = data[i + 1];
        }
        cursor--;
    }

    @Override
    public boolean remove(T obj) {


        if(indexOf(obj) >= 0){
            remove(indexOf(obj));
            return true;
        }

        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndex(index);
        return (T) data[index];
    }

    public boolean contains(T obj) {
        return indexOf(obj) >= 0;
    }


    private int indexOf(T obj){
        if(obj == null){
            for (int i = 0; i < cursor; i++) {
                if (data[i] == obj) {
                    return i;
                }
            }
        }
        else{
            for (int i = 0; i < cursor; i++) {
                if(data[i] != null) {
                    if (data[i].equals(obj)) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    @Override
    public int size() {
        return cursor;
    }

    public int capacity() {
        return capacity;
    }

    private void checkIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index must be >= 0");
        }
        if (index >= cursor) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < cursor; i++) {
            result.append(data[i]);

            if (i < cursor - 1) {
                result.append(", ");
            }
        }
        result.append("]");
        return result.toString();
    }

}
