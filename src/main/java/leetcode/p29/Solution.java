package leetcode.p29;

public class Solution {

    public int divide(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }
        if (dividend == Integer.MIN_VALUE && divisor == Integer.MIN_VALUE) {
            return 1;
        }
        if (dividend == 0) {
            return 0;
        }

        boolean negative = (dividend < 0) ^ (divisor < 0);
        int multiple = 0;
        int result = 0;

        dividend = dividend < 0 ? dividend : -dividend;
        divisor = divisor < 0 ? divisor : -divisor;

        int temp = divisor;
        while (temp >= dividend && temp < 0) {
            multiple = multiple == 0 ? 1 : multiple << 1;
            divisor = temp;
            temp = divisor << 1;
        }
        result = result - multiple;
        
        temp = divisor;
        while (multiple > 1) {
            temp = temp >> 1;
            multiple = multiple >> 1;

            if ((divisor + temp) >= dividend && (divisor + temp) < 0) {
                result = result - multiple;
                divisor = divisor + temp;
            }
        }

        return negative ? result : -result;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int result = s.divide(15, 5);
        System.out.println(result);
    }
}
