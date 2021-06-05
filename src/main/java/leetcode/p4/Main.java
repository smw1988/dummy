package leetcode.p4;

public class Main {

    public static void main(String[] args) {
        Main m = new Main();
        
        int[] a1 = { 3, };
        int[] a2 = { 1, 2, 4, 5, 6, };

        double d = m.findMedianSortedArrays(a1, a2);
        System.out.println(d);
    }
    
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len1 = nums1.length;
        int len2 = nums2.length;
        int totalLen = len1 + len2;
        
        if (totalLen % 2 == 0) {
            int leftMed = findLeftMed(nums1, nums2);
            int rightMed = findRightMed(nums1, nums2);
            return ((double)(leftMed + rightMed)) / 2;
        } else {
            return findNth(nums1, 0, len1, nums2, 0, len2, totalLen / 2);
        }
    }
    
    private int findLeftMed(int[] nums1, int[] nums2) {
        int len1 = nums1.length;
        int len2 = nums2.length;
        int totalLen = len1 + len2;
        
        if (len1 == 0) {
            return findNth(nums2, 0, len2 - 1, len2 / 2 - 1);
        } else if (nums2.length == 0) {
            return findNth(nums1, 0, len1 - 1, len1 / 2 - 1);
        } else {
            if (nums1[len1 - 1] >= nums2[len2 - 1]) {
                return findNth(nums1, 0, len1 - 1, nums2, 0, len2, (totalLen - 1) / 2);
            } else {
                return findNth(nums1, 0, len1, nums2, 0, len2 - 1, (totalLen - 1) / 2);
            }
        }
    }
    
    private int findRightMed(int[] nums1, int[] nums2) {
        int len1 = nums1.length;
        int len2 = nums2.length;
        int totalLen = len1 + len2;
        
        if (len1 == 0) {
            return findNth(nums2, 1, len2, len2 / 2 - 1);
        } else if (nums2.length == 0) {
            return findNth(nums1, 1, len1, len1 / 2 - 1);
        } else {
            if (nums1[0] <= nums2[0]) {
                return findNth(nums1, 1, len1, nums2, 0, len2, (totalLen - 1) / 2);
            } else {
                return findNth(nums1, 0, len1, nums2, 1, len2, (totalLen - 1) / 2);
            }
        }
    }
    
    private int findNth(int[] a1, int s1, int e1, int[] a2, int s2, int e2, int nth) {
        if (e1 <= s1) {
            return findNth(a2, s2, e2, nth);
        }
        if (e2 <= s2) {
            return findNth(a1, s1, e1, nth);
        }
        
        if (a1[e1 - 1] <= a2[s2]) {
            return findNthInAscArray(a1, s1, e1, a2, s2, e2, nth);
        }
        if (a2[e2 - 1] <= a1[s1]) {
            return findNthInAscArray(a2, s2, e2, a1, s1, e1, nth);
        }
        
        if (e1 - s1 == 1) {
            return findNthWithOneArraySingleElement(a1, s1, a2, s2, e2, nth);
        }
        if (e2 - s2 == 1) {
            return findNthWithOneArraySingleElement(a2, s2, a1, s1, e1, nth);
        }
        
        int m1 = (s1 + e1 + 1) / 2;
        int m2 = (s2 + e2 + 1) / 2;
        int totalLeftHalfLen = (m1 - s1) + (m2 - s2);
        
        if (totalLeftHalfLen > nth) {
            if (a1[m1 - 1] < a2[m2 - 1]) {
                return findNth(a1, s1, e1, a2, s2, m2, nth);
            } else if (a1[m1 - 1] > a2[m2 - 1]) {
                return findNth(a1, s1, m1, a2, s2, e2, nth);
            } else {
                return findNth(a1, s1, m1, a2, s2, m2, nth);
            }
        } else {
            if (a1[m1 - 1] < a2[m2 - 1]) {
                return findNth(a1, m1, e1, a2, s2, e2, nth - (m1 - s1));
            } else if (a1[m1 - 1] > a2[m2 - 1]) {
                return findNth(a1, s1, e1, a2, m2, e2, nth - (m2 - s2));
            } else {
                return findNth(a1, m1, e1, a2, m2, e2, nth - totalLeftHalfLen);
            }
        }
        
    }
    
    private int findNthInAscArray(int[] a1, int s1, int e1, int[] a2, int s2, int e2, int nth) {
        if (nth < (e1 - s1)) {
            return findNth(a1, s1, e1, nth);
        } else {
            return findNth(a2, s2, e2, nth - (e1 - s1));
        }
    }
    
    private int findNth(int[] a, int s, int e, int nth) {
        return a[s + nth];
    }
    
    private int findNthWithOneArraySingleElement(int[] a1, int s1, int[] a2, int s2, int e2, int nth) {
        int order = findOrder(a2, s2, e2, a1[s1]);
        if (order == nth) {
            return a1[s1];
        } else if (order > nth) {
            return a2[s2 + nth];
        } else {
            return a2[s2 + nth - 1];
        }
    }
    
    private int findOrder(int[] a, int s, int e, int element) {
        int start = s, end = e;
        while (start < end) {
            int m = (start + end + 1) / 2;
            
            if (element == a[m - 1]) {
                return m - 1 - s;
            } else if (element < a[m - 1]) {
                end = m - 1;
            } else {
                start = m;
            }
        }
        
        if (element <= a[start]) {
            return start - s;
        } else {
            return start - s + 1;
        }
    }

}
