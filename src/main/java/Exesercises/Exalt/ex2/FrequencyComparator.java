package Exesercises.Exalt.ex2;

import java.util.Comparator;
import java.util.Map;

final class FrequencyComparator implements Comparator<Integer> {
    private Map<Integer, Integer> refMap;

    FrequencyComparator(Map<Integer, Integer> base) {
        this.refMap = base;
    }

    @Override
    public int compare(Integer k1, Integer k2) {
        Integer val1 = refMap.get(k1);
        Integer val2 = refMap.get(k2);

        int num = val1.compareTo(val2);
        return num == 0 ? k1.compareTo(k2) : num;
    }
}
