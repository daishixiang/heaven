package com.daisx.heaven.algorithm;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

/**
 * @Author: dai.s.x
 * @Date: 2022/3/3 13:46
 */
public class LeetCodeString {

    /**
     * 打印
     * @param s
     */
    public static String print(char[] s){
        StringBuilder sb=new StringBuilder("[");
        for (int i = 0; i < s.length; i++) {
            sb.append(s[i]);
            if (i!=s.length-1){
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * 编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 s 的形式给出。
     * 不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
     * @param s
     */
    public static void reverseString1(char[] s) {
        if (s==null||s.length<2){
            return;
        }
        int length = s.length;
        for (int i = 0; i < length/2; i++) {
            s[i]^=s[length-1-i];
            s[length-1-i]^=s[i];
            s[i]^=s[length-1-i];
        }
    }
    public static void reverseString2(char[] s) {
        if (s==null||s.length<2){
            return;
        }
        int length = s.length;
        int left=0;
        int right=length-1;
        while (left<right){
            s[left]^=s[right];
            s[right]^=s[left];
            s[left]^=s[right];
            left++;
            right--;
        }
    }



    /**
     *给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。
     * 如果反转后整数超过 32 位的有符号整数的范围[−2^31, 2^31− 1] ，就返回 0。
     * @param x
     * @return
     */
    public static int reverse1(int x) {
        long res = 0;
        while (x != 0) {
            res = res * 10 + x % 10;
            x /= 10;
        }
        return (int) res == res ? (int) res : 0;
    }

    public static int reverse2(int x) {
        int res = 0;
        while (x != 0) {
            int t = x % 10;
            int newRes = res * 10 + t;
            if ((newRes - t) / 10 != res){
                return 0;
            }
            res=newRes;
            x /= 10;
        }
        return res;
    }


    /**
     * 给定一个字符串 s ，找到 它的第一个不重复的字符，并返回它的索引 。如果不存在，则返回 -1
     * @param s
     * @return
     */
    public static int firstUniqChar1(String s) {
        int count[] = new int[26];
        char[] chars = s.toCharArray();
        //先统计每个字符出现的次数
        for (int i = 0; i < s.length(); i++){
            count[chars[i] - 'a']++;
        }
        //然后在遍历字符串s中的字符，如果出现次数是1就直接返回
        for (int i = 0; i < s.length(); i++) {
            if (count[chars[i] - 'a'] == 1) {
                return i;
            }
        }
        return -1;
    }

    public static int firstUniqChar2(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.indexOf(s.charAt(i)) == s.lastIndexOf(s.charAt(i))) {
                return i;
            }
        }
        return -1;
    }


    /**
     * 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词
     * 注意：若s 和 t中每个字符出现的次数都相同，则称s 和 t互为字母异位词
     * @param s
     * @param t
     * @return
     */
    public boolean isAnagram(String s, String t) {
        if (s.length()!=t.length()){
            return false;
        }
        char[] cs = s.toCharArray();
        char[] ct = t.toCharArray();

        int[] map = new int[26];
        int count = 0;
        for (int i = 0; i < cs.length; i++) {
            if (++map[cs[i] - 'a'] == 1) {
                count++;
            }
            if (--map[ct[i] - 'a'] == 0) {
                count--;
            }
        }
        return count == 0;

    }


    /**
     * 给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
     * 0~9数字对应十进制48－57
     * a~z字母对应的十进制97－122
     * A~Z字母对应的十进制65－90
     * @param s
     * @return
     */
    public static boolean isPalindrome1(String s) {
        String s1 = s.toLowerCase();
        char[] chars = s1.toCharArray();
        int left=0;
        int right=chars.length-1;
        while (left<right){
            int leftChar = Integer.valueOf(chars[left]);
            int rightChar = Integer.valueOf(chars[right]);
            if (!(47<leftChar&&leftChar<58 ||96<leftChar&&leftChar<123)){
                left++;
            }else if (!(47<rightChar&&rightChar<58 ||96<rightChar&&rightChar<123)){
                right--;
            }else {
                int i = chars[left] ^ chars[right];
                if (i!=0){
                    return false;
                }
                left++;
                right--;
            }
        }
        return true;
    }

    public static boolean isPalindrome2(String s) {
        String actual = s.replaceAll("[^A-Za-z0-9]", "").toLowerCase();
        String rev = new StringBuffer(actual).reverse().toString();
        return actual.equals(rev);
    }

    /**
     * 请你来实现一个myAtoi(string s)函数，使其能将字符串转换成一个 32 位有符号整数（类似 C/C++ 中的 atoi 函数）。
     * 函数myAtoi(string s) 的算法如下：
     * 读入字符串并丢弃无用的前导空格
     * 检查下一个字符（假设还未到字符末尾）为正还是负号，读取该字符（如果有）。 确定最终结果是负数还是正数。 如果两者都不存在，则假定结果为正。
     * 读入下一个字符，直到到达下一个非数字字符或到达输入的结尾。字符串的其余部分将被忽略。
     * 将前面步骤读入的这些数字转换为整数（即，"123" -> 123， "0032" -> 32）。如果没有读入数字，则整数为 0 。必要时更改符号（从步骤 2 开始）。
     * 如果整数数超过 32 位有符号整数范围 [−2^31, 2^31− 1] ，需要截断这个整数，使其保持在这个范围内。具体来说，小于 −2^31 的整数应该被固定为 −2^31 ，大于 2^31− 1 的整数应该被固定为 2^31− 1 。
     * 返回整数作为最终结果。
     * 注意：
     * 本题中的空白字符只包括空格字符 ' ' 。
     * 除前导空格或数字后的其余字符串外，请勿忽略 任何其他字符。
     * @param str
     * @return
     */
    public static int myAtoi(String str) {
        str = str.trim();
        if (str.length() == 0) {
            return 0;
        }
        int index = 0;
        int res = 0;
        int sign = 1;
        int length = str.length();
        if (str.charAt(index) == '-' || str.charAt(index) == '+'){
            sign = str.charAt(index++) == '+' ? 1 : -1;
        }
        for (; index < length; ++index) {
            int digit = str.charAt(index) - '0';
            if (digit < 0 || digit > 9) {
                break;
            }
            if (res > Integer.MAX_VALUE / 10 || (res == Integer.MAX_VALUE / 10 && digit > Integer.MAX_VALUE % 10)) {
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }else {
                res = res * 10 + digit;
            }
        }
        return sign * res;
    }


    /**
     * 给你两个字符串haystack 和 needle ，请你在 haystack 字符串中找出 needle 字符串出现的第一个位置（下标从 0 开始）。如果不存在，则返回 -1 。
     * KMP算法的本质就是减少重复计算，我们举个例子，比如下面的图中，在最后一步比较失败的时候，然后子串又要从头开始，主串要从上一次比较的下一个字符开始，
     * 实际上前面的一些比较成功的数据我们是可以利用的。如下图所示，如果匹配失败的话，下一步主串还从失败的地方比较就可以了。
     * @param haystack
     * @param needle
     * @return
     */
    public static int strStr(String haystack, String needle) {
        haystack.indexOf(needle);
        return 0;
    }

    public static int[] getNext(String ps) {
        char[] p = ps.toCharArray();
        int[] next = new int[p.length];
        next[0] = -1;
        int j = 0;
        int k = -1;
        while (j < p.length - 1) {
            if (k == -1 || p[j] == p[k]) {
                // 当两个字符相等时要跳过
                if (p[++j] == p[++k]) {
                    next[j] = next[k];
                } else {
                    next[j] = k;
                }
            } else {
                k = next[k];
            }
        }
        return next;
    }

    public static void main(String[] args) {

        new HashMap<>();
        new Stack<>();
        new LinkedList<>();
        System.out.println("");

    }
}
