package Exesercises.Exalt.ex2;

import java.util.*;

//In a warehouse, the manager would like to sort the items in the stock.Given an array of n item values,
// sort the array in ascending order,first by the number of certain value,then by the values themselves

//На складе менеджер хочет отсортировать товары на складе.
// Имея массив из n значений товаров, отсортируйте массив в порядке возрастания,
// сначала по количеству определенного значения, затем по самим значениям

public class Ex2 {

    static List<Integer> itemsSort(List<Integer> items) {

        List<Integer> list = new ArrayList<>();

        Map<Integer,Integer> numbers = new HashMap<>();

        for(int number : items) {
            if(numbers.containsKey(number)) {
                Integer  count = numbers.get(number);
                numbers.put(number, ++count);
            } else {
                numbers.put(number,1);
            }
        }
        FrequencyComparator comp = new FrequencyComparator(numbers);
        TreeMap<Integer,Integer> sortedMap = new TreeMap<>(comp);
        sortedMap.putAll(numbers);
        System.out.println(sortedMap);
        for(Integer i : sortedMap.keySet()) {
            int frequencey = sortedMap.get(i);
            for(int count  = 1 ; count <= frequencey ; count++) {
                list.add(i);
            }
        }

        return list;
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(4,2,3,5,3,3,5));
        System.out.println(itemsSort(list));
    }

}
