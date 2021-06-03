package smw.practice.permutation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import smw.practice.util.ArrayUtils;

public class IterativePermutationGenerator implements PermutationGenerator {

    @Override
    public List<int[]> generate(int[] array) {
        List<int[]> result = new ArrayList<int[]>();
        this.permuteImpl(array, p -> {
            int[] permutationCopy = Arrays.copyOf(p, p.length);
            result.add(permutationCopy);
        });
        
        return result;
    }

    private void permuteImpl(int[] array, Consumer<int[]> action) {
        LinkedList<Integer> stack = new LinkedList<>();
        stack.add(0);

        while (!stack.isEmpty()) {
            int depth = stack.pop();

            if (depth == array.length) {
                action.accept(array);
            } else {
                for (int i = array.length - 1; i >= depth; --i) {
                    ArrayUtils.swap(array, depth, i);
                    stack.push(depth + 1);
                    ArrayUtils.swap(array, depth, i);
                }
            }

        }
    }
    
}
