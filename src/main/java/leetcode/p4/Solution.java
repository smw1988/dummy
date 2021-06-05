package leetcode.p4;

public class Solution {
    public static void main(String[] args) {
        Solution s = new Solution();
        
        int[] a1 = { 1, 3, };
        int[] a2 = { 2, };

        double d = s.findMedianSortedArrays(a1, a2);
        System.out.println(d);
    }

    static class SubArray {
        private int[] array;
        private int start;
        private int end;

        public SubArray(int[] array) {
            this(array, 0, array.length);
        }

        public SubArray(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        public int get(int index) {
            return array[start + index];
        }

        public int length() {
            return end - start;
        }

        public boolean empty() {
            return end <= start;
        }

        public int first() {
            return array[start];
        }

        public int last() {
            return array[end - 1];
        }

        public SubArray dropFirst() {
            return new SubArray(this.array, this.start + 1, this.end);
        }

        public SubArray dropLast() {
            return new SubArray(this.array, this.start, this.end - 1);
        }

        public SubArray[] splitAtMiddle() {
            int middle = (start + end + 1) / 2;
            SubArray lefArray = new SubArray(this.array, start, middle);
            SubArray righArray = new SubArray(this.array, middle, end);
            return new SubArray[] { lefArray, righArray, };
        }

    }
    
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        SubArray a1 = new SubArray(nums1);
        SubArray a2 = new SubArray(nums2);

        if ((a1.length() + a2.length()) % 2 == 0) {
            int leftMed = findLeftMed(a1, a2);
            int rightMed = findRightMed(a1, a2);
            return ((double)(leftMed + rightMed)) / 2;
        } else {
            return findNth(a1, a2, (a1.length() + a2.length()) / 2);
        }
    }
    
    private int findLeftMed(SubArray a1, SubArray a2) {
        if (a1.length() == 0) {
            return findNth(a2.dropLast(), a2.length() / 2 - 1);
        } else if (a2.length() == 0) {
            return findNth(a1.dropLast(), a1.length() / 2 - 1);
        } else {
            int medIndex = (a1.length() + a2.length() - 1) / 2;
            if (a1.last() >= a2.last()) {
                return findNth(a1.dropLast(), a2, medIndex);
            } else {
                return findNth(a1, a2.dropLast(), medIndex);
            }
        }
    }
    
    private int findRightMed(SubArray a1, SubArray a2) {
        if (a1.length() == 0) {
            return findNth(a2.dropFirst(), a2.length() / 2 - 1);
        } else if (a2.length() == 0) {
            return findNth(a1.dropFirst(), a1.length() / 2 - 1);
        } else {
            int medIndex = (a1.length() + a2.length() - 1) / 2;
            if (a1.first() <= a2.first()) {
                return findNth(a1.dropFirst(), a2, medIndex);
            } else {
                return findNth(a1, a2.dropFirst(), medIndex);
            }
        }
    }
    
    private int findNth(SubArray a1, SubArray a2, int nth) {
        if (a1.empty()) {
            return findNth(a2, nth);
        }
        if (a2.empty()) {
            return findNth(a1, nth);
        }
        
        if (a1.last() <= a2.first()) {
            return findNthInAscArray(a1, a2, nth);
        }
        if (a2.last() <= a1.first()) {
            return findNthInAscArray(a2, a1, nth);
        }
        
        if (a1.length() == 1) {
            return findNthWithOneArraySingleElement(a1, a2, nth);
        }
        if (a2.length() == 1) {
            return findNthWithOneArraySingleElement(a2, a1, nth);
        }

        SubArray[] a1Splits = a1.splitAtMiddle();
        SubArray[] a2Splits = a2.splitAtMiddle();

        SubArray a1Left = a1Splits[0], a1Right = a1Splits[1];
        SubArray a2Left = a2Splits[0], a2Right = a2Splits[1];

        int totalLeftHalfLen = a1Left.length() + a2Left.length();
        
        if (totalLeftHalfLen > nth) {
            if (a1Left.last() < a2Left.last()) {
                return findNth(a1, a2Left, nth);
            } else if (a1Left.last() > a2Left.last()) {
                return findNth(a1Left, a2, nth);
            } else {
                return findNth(a1Left, a2Left, nth);
            }
        } else {
            if (a1Left.last() < a2Left.last()) {
                return findNth(a1Right, a2, nth - a1Left.length());
            } else if (a1Left.last() > a2Left.last()) {
                return findNth(a1, a2Right, nth - a2Left.length());
            } else {
                return findNth(a1Right, a2Right, nth - totalLeftHalfLen);
            }
        }
        
    }
    
    private int findNthInAscArray(SubArray a1, SubArray a2, int nth) {
        if (nth < a1.length()) {
            return findNth(a1, nth);
        } else {
            return findNth(a2, nth - a1.length());
        }
    }
    
    private int findNth(SubArray a, int nth) {
        return a.get(nth);
    }
    
    private int findNthWithOneArraySingleElement(SubArray a1, SubArray a2, int nth) {
        int order = findOrder(a2, a1.first());
        if (order == nth) {
            return a1.first();
        } else if (order > nth) {
            return a2.get(nth);
        } else {
            return a2.get(nth - 1);
        }
    }
    
    private int findOrder(SubArray a, int element) {
        int start = 0, end = a.length();
        while (start < end) {
            int middle = (start + end + 1) / 2;
            
            if (element == a.get(middle - 1)) {
                return middle - 1;
            } else if (element < a.get(middle - 1)) {
                end = middle - 1;
            } else {
                start = middle;
            }
        }
        
        if (element <= a.get(start)) {
            return start;
        } else {
            return start + 1;
        }
    }
}
