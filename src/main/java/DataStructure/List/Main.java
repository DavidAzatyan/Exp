package DataStructure.List;

import java.util.Arrays;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        Vector<Integer> vector = new Vector<>(Arrays.asList(1,2,3,4,5,2,0,3,6,9,4,0,1,2,12));
        System.out.println(vector.capacity());
        vector.remove(2);
        System.out.println(vector.capacity());
    }
}
