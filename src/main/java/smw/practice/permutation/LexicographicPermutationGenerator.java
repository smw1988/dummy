package smw.practice.permutation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import smw.practice.util.ArrayUtils;

public class LexicographicPermutationGenerator implements PermutationGenerator {

    @Override
    public List<int[]> generate(int[] array) {
        List<int[]> permutationList = new ArrayList<>();

        do {
            permutationList.add(Arrays.copyOf(array, array.length));
        } while (this.permute(array));

        return permutationList;
    }

    private boolean permute(int[] array) {
        if (array.length <= 1) {
            return false;
        }

        int peak = array.length - 1;
        for (int i = peak - 1; array[i] >= array[peak]; --i, --peak) {
            if (i == 0) {
                return false;
            }
        }

        int newStart = peak - 1;
        int toExchange = array.length - 1;
        for ( ; toExchange >= peak && array[newStart] >= array[toExchange]; --toExchange)
            ;

        ArrayUtils.swap(array, newStart, toExchange);
        ArrayUtils.reverse(array, peak);
        return true;
    }
    
}
