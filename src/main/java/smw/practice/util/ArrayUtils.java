package smw.practice.util;

public class ArrayUtils {
    
    public static void swap(int[] array, int i, int j) {
        int t = array[i];
        array[i] = array[j];
        array[j] = t;
    }

    public static void reverse(int[] array, int start) {
        for (int end = array.length - 1; start < end; ++start, --end) {
            swap(array, start, end);
        }
    }

}
