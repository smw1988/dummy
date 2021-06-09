package leetcode.p5;

import java.util.List;
import java.util.ArrayList;

public class Solution {

    public static void main(String[] args) {
        Solution s = new Solution();
        
        String p = s.longestPalindrome("cbbd");
        System.out.println(p);
    }

    public String longestPalindrome(String s) {
        int stringLength = s.length();
        if (stringLength == 1) {
            return s;
        }
        
        char[] chars = s.toCharArray();
        
        List<Integer> oddList = new ArrayList<>();
        List<Integer> evenList = new ArrayList<>();
        
        for (int i = 0; i < stringLength; ++i) {
            oddList.add(i);
            if (i < stringLength - 1 && chars[i] == chars[i + 1]) {
                evenList.add(i);
            }
        }
        
        Pair oddLongest = extend(chars, oddList, 1);
        Pair evenLongest = evenList.size() == 0 ? new Pair(0, null) : extend(chars, evenList, 2);
        
        if (oddLongest.longest >= evenLongest.longest) {
            return s.substring(oddLongest.list.get(0), oddLongest.longest);
        } else {
            return s.substring(evenLongest.list.get(0), evenLongest.longest);
        }
        
    }
    
    private Pair extend(char[] chars, List<Integer> longestPos, int longest) {
        List<Integer> extendedLongestPos = new ArrayList<>();
        while (true) {
            for (int candidateLongestStart : longestPos) {
                int leftPos = candidateLongestStart - 1;
                int rightPos = candidateLongestStart + longest;
                
                if (leftPos >= 0 && rightPos < chars.length && chars[leftPos] == chars[rightPos]) {
                    extendedLongestPos.add(leftPos);
                }
            }
            
            if (extendedLongestPos.size() > 0) {
                List<Integer> t = longestPos;
                longestPos = extendedLongestPos;
                extendedLongestPos = t;
                extendedLongestPos.clear();
                longest += 2;
            } else {
                return new Pair(longest, longestPos);
            }
        }
    }
    
}

class Pair {
    public int longest;
    public List<Integer> list;
    
    public Pair(int longest, List<Integer> list) {
        this.longest = longest;
        this.list = list;
    }
}
