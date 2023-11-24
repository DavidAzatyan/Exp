package Other.Data_Structure.MyQueue;

import Other.Data_Structure.MyArrayList.MyArrayList;

public class MyQueue<T> {

    private MyArrayList<T> data = new MyArrayList<>();

    public void push(T obj) {
        data.add(obj);
    }

    public T pop() {
        T temp = data.get(0);
        data.remove(0);
        return temp;
    }

    public boolean isEmpty() {
        return data.size() == 0;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < data.size(); i++) {
            result.append(data.get(i));

            if (i < data.size() - 1) {
                result.append(", ");
            }
        }
        result.append("]");
        return result.toString();
    }
}
