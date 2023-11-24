package Exesercises.Exalt.ex3;

import java.util.Comparator;
import java.util.Map;

final class ValueComparator<K, V extends Comparable<V>> implements Comparator<K> {

    private Map<K, V> map;

    ValueComparator(Map<K, V> base) {
        this.map = base;
    }

    @Override
    public int compare(K o1, K o2) {
        return map.get(o2).compareTo(map.get(o1));
    }

}
