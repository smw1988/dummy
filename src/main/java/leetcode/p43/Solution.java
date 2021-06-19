package leetcode.p43;

import java.util.ArrayList;

class BigInt {
    private ArrayList<Integer> digits = new ArrayList<>();
    private static final int C = 1000000000;
    public static final BigInt ZERO = new BigInt("0");

    public BigInt(String str) {
        for (int index = str.length(); index > 0; index -= 9) {
            String part = str.substring(Math.max(0, index - 9), index);
            int digit = Integer.parseInt(part);
            digits.add(digit);
        }
    }

    private BigInt(ArrayList<Integer> digits) {
        this.digits = digits;
    }

    public BigInt add(BigInt other) {
        return new BigInt(fullAdd(this.digits, other.digits));
    }

    public BigInt multiply(BigInt other) {
        if (this.digits.get(this.digits.size() - 1) == 0) {
            return ZERO;
        }
        if (other.digits.get(other.digits.size() - 1) == 0) {
            return ZERO;
        }

        ArrayList<Integer> newDigits = ZERO.digits;
        int shift = 0;

        for (int t : other.digits) {
            ArrayList<Integer> tempProduct = halfMultiply(digits, t, shift);
            newDigits = fullAdd(newDigits, tempProduct);
            ++shift;
        }

        return new BigInt(newDigits);
    }

    private static ArrayList<Integer> halfMultiply(ArrayList<Integer> digits, int t, int shift) {
        if (t == 0) {
            return ZERO.digits;
        }

        ArrayList<Integer> newDigits = new ArrayList<>();
        while (shift-- > 0) {
            newDigits.add(0);
        }

        int carry = 0;
        for (Integer d : digits) {
            long product = d.longValue() * t + carry;
            if (product >= C) {
                carry = (int)(product / C);
            } else {
                carry = 0;
            }

            int p = (int)(product % C);
            newDigits.add(p);
        }

        if (carry > 0) {
            newDigits.add(carry);
        }

        return newDigits;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = digits.size() - 1; i >= 0; --i) {
            if (i == digits.size() - 1) {
                sb.append(String.valueOf(digits.get(i)));
            } else {
                sb.append(String.format("%09d", digits.get(i)));
            }
        }
        return sb.toString();
    }

    private static ArrayList<Integer> fullAdd(ArrayList<Integer> digits1, ArrayList<Integer> digits2) {
        int index1 = 0, index2 = 0;
        int carry = 0;

        ArrayList<Integer> newDigits = new ArrayList<>();
        while (index1 < digits1.size() && index2 < digits2.size()) {
            SumResult sumResult = halfAdd(digits1.get(index1), digits2.get(index2), carry);
            carry = sumResult.carry;
            newDigits.add(sumResult.sum);
            ++index1;
            ++index2;
        }

        while (index1 < digits1.size()) {
            SumResult sumResult = halfAdd(digits1.get(index1), 0, carry);
            carry = sumResult.carry;
            newDigits.add(sumResult.sum);
            ++index1;
        }

        while (index2 < digits2.size()) {
            SumResult sumResult = halfAdd(digits2.get(index2), 0, carry);
            carry = sumResult.carry;
            newDigits.add(sumResult.sum);
            ++index2;
        }

        if (carry == 1) {
            newDigits.add(1);
        }

        return newDigits;
    }

    private static SumResult halfAdd(int d1, int d2, int c) {
        int s = d1 + d2 + c;
        if (s >= C) {
            s -= C;
            c = 1;
        } else {
            c = 0;
        }
        return new SumResult(s, c);
    }

    static class SumResult {
        int sum;
        int carry;

        SumResult(int sum, int carry) {
            this.sum = sum;
            this.carry = carry;
        }
    }
}

public class Solution {

    public String multiply(String num1, String num2) {
        BigInt i1 = new BigInt(num1);
        BigInt i2 = new BigInt(num2);
        BigInt s = i1.multiply(i2);
        return s.toString();
    }
    
    public static void main(String[] args) {
        BigInt i1 = new BigInt("725071900");
        BigInt i2 = new BigInt("6478");
        BigInt s = i1.multiply(i2);
        System.out.println(s);
    }

}
