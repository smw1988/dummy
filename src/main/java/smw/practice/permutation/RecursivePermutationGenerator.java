package smw.practice.permutation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import smw.practice.util.ArrayUtils;

public class RecursivePermutationGenerator implements PermutationGenerator {

    @Override
    public List<int[]> generate(int[] array) {
        List<int[]> result = new ArrayList<int[]>();
        this.permuteImpl(array, p -> {
            int[] permutationCopy = Arrays.copyOf(p, p.length);
            result.add(permutationCopy);
        }, 0);
        return result;
    }

    private void permuteImpl(int[] array, Consumer<int[]> action, int depth) {
        if (depth == array.length) {
            action.accept(array);
        } else {
            for (int i = depth; i < array.length; ++i) {
                ArrayUtils.swap(array, depth, i);
                permuteImpl(array, action, depth + 1);
                ArrayUtils.swap(array, depth, i);
            }
        }
    }

}
