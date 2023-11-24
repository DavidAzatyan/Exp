package DataStructure.List;

public interface MyList<T> {
    void add(T obj);

    T get(int index);

    void remove(int index);

    void remove(T obj);

    int size();
}
