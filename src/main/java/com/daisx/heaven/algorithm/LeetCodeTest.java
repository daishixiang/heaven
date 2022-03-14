package com.daisx.heaven.algorithm;

import org.apache.poi.ss.formula.functions.Roman;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: dai.s.x
 * @Date: 2022/3/14 9:43
 */
public class LeetCodeTest {

    public static int lengthOfLongestSubstring(String s) {
        if (s==null||s.length()==0){
            return 0;
        }
        if (s.length()==1){
            return 1;
        }
        char[] chars = s.toCharArray();
        int left=0;
        int right=1;
        int ans=0;
        while (right<s.length()){
            for (int i = left; i < right; i++) {
                if (chars[i]==chars[right]){
                    left=i+1;
                }
            }
            ans = Math.max(ans,right-left+1);
            right++;
        }
        return ans;
    }

    /**
     * 给定两个大小分别为 m 和 n 的正序（从小到大）数组nums1 和nums2。请你找出并返回这两个正序数组的 中位数 。算法的时间复杂度应该为 O(log (m+n))
     * @param nums1
     * @param nums2
     * @return
     */
    public static double findMedianSortedArrays1(int[] nums1, int[] nums2) {
        if(nums1.length > nums2.length) {
            return findMedianSortedArrays1(nums2, nums1);
        }
        int m = nums1.length;
        int n = nums2.length;
        int begin = 0;
        int end = m;
        //左半部分的最大值
        double left_max = Double.MIN_VALUE;
        //右半部分的最小值
        double right_min = Double.MAX_VALUE;
        while(begin <= end) {
            //基于二分的方式求 i
            int i = begin + (end - begin) / 2;
            //数组A长度为m，数组B长度n，总长度为偶数时，左半部分右半部分相等：
            //m - i + n - j = i + j
            //总长度为奇数时，左半部分比右半部分多1个：
            //m - i + n - j + 1 = i + j
            //统一奇数、偶数情况，得到j为：(m + n + 1) / 2 - i
            int j = (m + n + 1) / 2 - i;
            //如果nums1[i - 1] > nums2[j]说明 i 取大了
            if(i > 0 && j < n && nums1[i - 1] > nums2[j]) {
                end = i - 1;
            }
            //nums2[j - 1] > nums1[i]，i 取小了
            else if(j > 0 && i < m && nums2[j - 1] > nums1[i]) {
                begin = i + 1;
            }
            //满足条件：nums1[i - 1] < nums2[j]，nums2[j - 1] < nums1[i]
            else {
                //边界情况，数组A切分后左半部分为空 i == 0
                //数组B 切分后左半部分为空 j == 0
                if(i == 0) {
                    left_max = nums2[j - 1];
                }
                else if(j == 0) {
                    left_max = nums1[i - 1];
                }
                //求左半部分的最大值
                else {
                    left_max = Math.max(nums1[i - 1], nums2[j - 1]);
                }
                //总长度为奇数时，直接返回左半部分最大值即可
                if((m + n) % 2 == 1) {
                    return left_max / 1.0;
                }
                //边界情况，数组A 切分后，右半部分为空 i == m
                //数组B 切分后，右半部分为空 j == n
                if(i == m) {
                    right_min = nums2[j];
                }
                else if(j == n) {
                    right_min = nums1[i];
                }
                //求右半部分的最小值
                else {
                    right_min = Math.min(nums1[i], nums2[j]);
                }
                //总长度为偶数时
                return (left_max + right_min) / 2.0;
            }
        }
        return 0.0D;
    }

    public static double findMedianSortedArrays2(int[] nums1, int[] nums2) {
        if (nums1.length>nums2.length){
            return findMedianSortedArrays2(nums2,nums1);
        }
        //m < n
        int m = nums1.length;
        int n = nums2.length;
        double leftMaxValue ;
        double rightMinValue ;
        int begin=0;
        int end=m;
        while (begin<end){
            int i = begin + (end - begin) / 2;
            int j=(m+n+1)/2-i;
            if(i > 0 && j < n && nums1[i - 1] > nums2[j]) {
                end = i - 1;
            }
            else if(j > 0 && i < m && nums2[j - 1] > nums1[i]) {
                begin = i + 1;
            }else {
                if(i == 0) {
                    leftMaxValue = nums2[j - 1];
                }else if(j == 0) {
                    leftMaxValue = nums1[i - 1];
                }else {
                    leftMaxValue = Math.max(nums1[i - 1], nums2[j - 1]);
                }
                if ((m+n)%2!=0){
                    return leftMaxValue / 1.0;
                }

                if (i==m){
                    rightMinValue=nums2[j];
                }else if(j==n){
                    rightMinValue=nums1[i];
                }else {
                    rightMinValue=Math.max(nums1[i],nums2[j]);
                }
                return (rightMinValue+leftMaxValue)/2.0;
            }
        }
        return 0.0D;
    }

    /**
     *给你一个整数 x ，如果 x 是一个回文整数，返回 true ；否则，返回 false 。
     * 回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
     * @param x
     * @return
     */
    public static boolean isPalindrome(int x) {
        if (x<0||(x % 10==0&&x!=0)){
            return false;
        }
        int cur=0;
        while (x > cur) {
            cur = cur * 10 + x % 10;
            x /= 10;
        }
        return cur==x||x==cur/10;
    }


    /**
     * 罗马数字包含以下七种字符:I，V，X，L，C，D和M。
     * 字符          数值
     * I             1
     * V             5
     * X             10
     * L             50
     * C             100
     * D             500
     * M             1000
     * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做IIII，而是IV。
     * 数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。
     * 同样地，数字 9 表示为IX。这个特殊的规则只适用于以下六种情况：
     *
     * I可以放在V(5) 和X(10) 的左边，来表示 4 和 9。
     * X可以放在L(50) 和C(100) 的左边，来表示 40 和90。
     * C可以放在D(500) 和M(1000) 的左边，来表示400 和900。
     * 给定一个罗马数字，将其转换成整数
     **/

     public static Map<Character,Integer> romanMap=new HashMap<>();

     public static int romanToInt(String s) {
         if (romanMap.size()<1){
             romanMap.put('I',1);
             romanMap.put('V',5);
             romanMap.put('X',10);
             romanMap.put('L',50);
             romanMap.put('C',100);
             romanMap.put('D',500);
             romanMap.put('M',1000);
         }
         char[] chars = s.toCharArray();
         int i=0;
         int num=0;
         while (i<chars.length-1){
             char a = chars[i];
             char b = chars[i+1];
             if (romanMap.get(a)<romanMap.get(b)){
                 num-=romanMap.get(a);
             }else {
                 num+=romanMap.get(a);
             }
             i++;
         }
         num+=romanMap.get(chars[chars.length-1]);
         return num;
     }


    /**
     * 编写一个函数来查找字符串数组中的最长公共前缀。
     * 如果不存在公共前缀，返回空字符串 ""。
     * @param strs
     * @return
     */
    public static String longestCommonPrefix(String[] strs) {
        if (strs==null||strs.length==0){
            return "";
        }
        String ans=strs[0];
        for (int i = 1; i < strs.length; i++) {
            String str = strs[i];
            int j = 0 ;
            for (; j < ans.length() && j<str.length(); j++) {
                if (str.charAt(j)!=ans.charAt(j)){
                   break;
                }
            }
            ans=ans.substring(0,j);
            if ("".equals(ans)){
                return "";
            }
        }
        return ans;
    }


    public static void main(String[] args) {
        int[] num1={1,3};
        int[] num2={2,7};



        String s="aaaaa";
        System.out.println(findMedianSortedArrays2(num1,num2));
    }
}
