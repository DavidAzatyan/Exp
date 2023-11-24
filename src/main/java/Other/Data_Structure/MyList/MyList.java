package Other.Data_Structure.MyList;

public interface MyList<T> {
    void add(T obj);
    T get(int index);
    void remove(int index);
    boolean remove(T obj);
    int size();

}
