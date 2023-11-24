package Exesercises.Exalt.ex3;

import java.util.*;

// An e-commerce site tracks the purchase made each day.
// The product that is purchased the most one day is the featured product for the following day.
// If there is a tie for the product purchased more frequantly,
// those product names are ordered alphabetically ascending and the last element  in the list is choosen.


// Сайт электронной коммерции отслеживает совершенные покупки каждый день.
// Продукт, который покупается чаще всего на один день, является рекомендуемым продуктом на следующий день.
// Если товар покупается чаще,
// эти наименования продуктов упорядочиваются в алфавитном порядке по возрастанию, и выбирается последний элемент в списке.

public class Ex3 {

    static String featuredProduct(List<String> products) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (String s : products) {
            if (map.containsKey(s)) {
                map.put(s, map.get(s) + 1);
            } else {
                map.put(s, 1);
            }
        }
        ValueComparator<String, Integer> comparator = new ValueComparator<>(map);
        Map<String, Integer> sortedMap = new TreeMap<>(comparator);
        sortedMap.putAll(map);

        List<String> sortedList = new ArrayList<>(sortedMap.keySet());


        System.out.println(sortedList);

        return sortedList.get(0);
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>(Arrays.asList("redShirt", "greenPants", "redShirt",
                                                    "orangeShoes", "blackPants", "blackPants"));
        System.out.println(featuredProduct(list));
    }
}
