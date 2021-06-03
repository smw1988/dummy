package smw.practice.permutation;

import java.util.Arrays;
import java.util.List;

public class PermutationTest {
    public static void main(String[] args) {
        int[] array = { 1, 2, 3, 4, };
        
        // PermutationGenerator pg1 = new RecursivePermutationGenerator();
        // testPermutationGenerator(pg1, array);

        System.out.println("test pg2");
        PermutationGenerator pg2 = new RecursivePermutationGenerator();
        testPermutationGenerator(pg2, array);

    }

    private static void testPermutationGenerator(PermutationGenerator g, int[] array) {
        List<int[]> permutationList = g.generate(array);
        printPermutationList(permutationList);
    }

    private static void printPermutationList(List<int[]> permutationList) {
        for (int[] array : permutationList) {
            Arrays.stream(array).forEach(e -> System.out.print(e + ", "));
            System.out.println();
        }
    }
}
