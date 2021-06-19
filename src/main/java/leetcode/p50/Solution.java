package leetcode.p50;

public class Solution {
    public double myPow(double x, int n) {
        if (n == 0) {
            return 1;
        }
        if (n == 1) {
            return x;
        }
        if (x == 0) {
            return 0;
        }

        if (n > 0) {
            return pow(x, n);
        } else {
            boolean oneMore = false;
            if (n == Integer.MIN_VALUE) {
                n = -(n + 1);
                oneMore = true;
            } else {
                n = -n;
            }

            double result = pow(x, n);
            if (oneMore) {
                result = result * x;
            }

            return 1 / result;
        }
    }

    private double pow(double x, int n) {
        double result = 1;
        for (int k = 30; k >= 0; --k) {
            if (((n >> k) & 0x1) == 0) {
                result = result * result;
            } else {
                result = result * result * x;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.myPow(0.00001, 2147483647));
    }
}
