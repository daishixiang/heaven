package com.daisx.heaven.algorithm;

import org.apache.poi.ss.formula.functions.Roman;
import org.apache.pulsar.shade.org.checkerframework.checker.units.qual.A;

import java.util.*;

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

    /**
     * 公司有编号为 1到 n的 n个工程师，给你两个数组 speed和 efficiency，其中 speed[i]和 efficiency[i]分别代表第 i位工程师的速度和效率。请你返回由最多k个工程师组成的最大团队表现值，
     * 由于答案可能很大，请你返回结果对 10^9 + 7 取余后的结果。
     * 团队表现值的定义为：一个团队中「所有工程师速度的和」乘以他们「效率值中的最小值」
     * @param n
     * @param speed
     * @param efficiency
     * @param k
     * @return
     */
    public static int maxPerformance(int n, int[] speed, int[] efficiency, int k) {
        int[][] items = new int[n][2];
        for(int i = 0 ; i < n ; i++){
            items[i][0] = speed[i];
            items[i][1] = efficiency[i];
        }
        Arrays.sort(items, (a, b) -> b[1] - a[1]);
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        long res = 0, sum = 0;
        for(int i = 0 ; i < n ; i++){
            if(queue.size() > k - 1){
                sum -= queue.poll();
            }
            res = Math.max(res, (sum + items[i][0])* items[i][1]);
            queue.add(items[i][0]);
            sum += items[i][0];
        }
        return (int)(res % ((int)1e9 + 7));
    }


    //定义三个指针，保证遍历数组中的每一个结果
    public static List<List<Integer>> threeSum(int[] nums) {
        //定义一个结果集
        List<List<Integer>> res = new ArrayList<>();
        //数组的长度
        int len = nums.length;
        //当前数组的长度为空，或者长度小于3时，直接退出
        if(nums == null || len <3){
            return res;
        }
        //将数组进行排序
        Arrays.sort(nums);
        //遍历数组中的每一个元素
        for(int i = 0; i<len;i++){
            //如果遍历的起始元素大于0，就直接退出
            //原因，此时数组为有序的数组，最小的数都大于0了，三数之和肯定大于0
            if(nums[i]>0){
                break;
            }
            //去重，当起始的值等于前一个元素，那么得到的结果将会和前一次相同
            if(i > 0 && nums[i] == nums[i-1]) {
                continue;
            }
            int l = i +1;
            int r = len-1;
            //当 l 不等于 r时就继续遍历
            while(l<r){
                //将三数进行相加
                int sum = nums[i] + nums[l] + nums[r];
                //如果等于0，将结果对应的索引位置的值加入结果集中
                if(sum==0){
                    // 将三数的结果集加入到结果集中
                    res.add(Arrays.asList(nums[i], nums[l], nums[r]));
                    //在将左指针和右指针移动的时候，先对左右指针的值，进行判断
                    //如果重复，直接跳过。
                    //去重，因为 i 不变，当此时 l取的数的值与前一个数相同，所以不用在计算，直接跳
                    while(l < r && nums[l] == nums[l+1]) {
                        l++;
                    }
                    //去重，因为 i不变，当此时 r 取的数的值与前一个相同，所以不用在计算
                    while(l< r && nums[r] == nums[r-1]){
                        r--;
                    }
                    //将 左指针右移，将右指针左移。
                    l++;
                    r--;
                    //如果结果小于0，将左指针右移
                }else if(sum < 0){
                    l++;
                    //如果结果大于0，将右指针左移
                }else if(sum > 0){
                    r--;
                }
            }
        }
        return res;
    }

    /**
     *请实现一个函数用来匹配包含'. '和'*'的正则表达式。模式中的字符'.'表示任意一个字符，而'*'表示它前面的字符可以出现任意次（含0次）
     * 在本题中，匹配是指字符串的所有字符匹配整个模式。例如，字符串"aaa"与模式"a.a"
     * @param s
     * @param p
     * @return
     */
    public static boolean isMatch(String s, String p) {
        int n = s.length();
        int m = p.length();
        boolean[][] f = new boolean[n + 1][m + 1];

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                //分成空正则和非空正则两种
                if (j == 0) {
                    f[i][j] = i == 0;
                } else {
                    //非空正则分为两种情况 * 和 非*
                    if (p.charAt(j - 1) != '*') {
                        if (i > 0 && (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '.')) {
                            f[i][j] = f[i - 1][j - 1];
                        }
                    } else {
                        //碰到 * 了，分为看和不看两种情况
                        //不看
                        if (j >= 2) {
                            f[i][j] |= f[i][j - 2];
                        }
                        //看
                        if (i >= 1 && j >= 2 && (s.charAt(i - 1) == p.charAt(j - 2) || p.charAt(j - 2) == '.')) {
                            f[i][j] |= f[i - 1][j];
                        }
                    }
                }
            }
        }
        return f[n][m];
    }

    /**
     * 给你一个数组 nums和一个值 val，你需要 原地 移除所有数值等于val的元素，并返回移除后数组的新长度。
     * 不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。
     * 元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
     * @param nums
     * @param val
     * @return
     */
    public static int removeElement(int[] nums, int val) {
        int n = nums.length;
        int left = 0;
        for (int right = 0; right < n; right++) {
            if (nums[right] != val) {
                nums[left] = nums[right];
                left++;
            }
        }
        return left;
    }

    /**
     * 实现strStr()函数。
     *
     * 给你两个字符串haystack 和 needle ，请你在 haystack 字符串中找出 needle 字符串出现的第一个位置（下标从 0 开始）。如果不存在，则返回 -1 。
     *
     * @param haystack
     * @param needle
     * @return
     */
    public int strStr(String haystack, String needle) {
        int n = haystack.length();
        int m = needle.length();
        if (m == 0) {
            return 0;
        }
        int[] pi = new int[m];
        for (int i = 1, j = 0; i < m; i++) {
            while (j > 0 && needle.charAt(i) != needle.charAt(j)) {
                j = pi[j - 1];
            }
            if (needle.charAt(i) == needle.charAt(j)) {
                j++;
            }
            pi[i] = j;
        }
        for (int i = 0, j = 0; i < n; i++) {
            while (j > 0 && haystack.charAt(i) != needle.charAt(j)) {
                j = pi[j - 1];
            }
            if (haystack.charAt(i) == needle.charAt(j)) {
                j++;
            }
            if (j == m) {
                return i - m + 1;
            }
        }
        return -1;
    }


    public static int largestRectangleArea(int[] heights) {
        int res = 0;
        Stack<Integer> stack = new Stack<>();

        int[] newHeights = new int[heights.length + 2];
        newHeights[0] = 0;
        newHeights[newHeights.length-1] = 0;
        for (int i = 1; i < heights.length + 1; i++) {
            newHeights[i] = heights[i - 1];
        }

        for (int i = 0; i < newHeights.length; i++) {
            while (!stack.isEmpty() && newHeights[i] < newHeights[stack.peek()]) {
                int cur = stack.pop();
                int curHeight = newHeights[cur];
                int leftIndex = stack.peek();
                int rightIndex = i;
                int curWidth = rightIndex - leftIndex - 1;
                res = Math.max(res, curWidth * curHeight);
            }

            stack.push(i);
        }

        return res;
    }

    /**
     * 给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使每个元素 最多出现两次 ，返回删除后数组的新长度。
     * 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
     * @param nums
     * @return
     */
    public static int removeDuplicates(int[] nums) {
        int n=nums.length;
        if (n<3){
            return n;
        }
        int left=2;
        int right=2;
        while (right<n){
            if (nums[left-2]!=nums[right]){
                nums[left]=nums[right];
                left++;
            }
            right++;
        }
        return left;
    }

    /**
     * 给定一个非负整数 numRows，生成「杨辉三角」的前 numRows 行。
     * 在「杨辉三角」中，每个数是它左上方和右上方的数的和。
     * @param numRows
     * @return
     */
    public static List<List<Integer>> generate(int numRows) {
        List<List<Integer>> res=new ArrayList<>();
        List<Integer> l1=new ArrayList<>(1);
        l1.add(1);
        res.add(l1);
        for (int i = 1; i < numRows; i++) {
            List<Integer> li=new ArrayList<>(i+1);
            List<Integer> ri = res.get(i-1);
            li.add(1);
            for (int j=1;j<i;j++){
                li.add(ri.get(j-1)+ri.get(j));
            }
            li.add(1);
            res.add(li);
        }
        return res;
    }

    /**
     * 输入整数数组 arr ，找出其中最小的 k 个数。例如，输入4、5、1、6、2、7、3、8这8个数字，则最小的4个数字是1、2、3、4。
     * @param arr
     * @param k
     * @return
     */
    public  static int[] getLeastNumbers(int[] arr, int k) {
        int[] res = new int[k];
        if (k == 0) {
            return res;
        }
        PriorityQueue<Integer> queue = new PriorityQueue<>((num1, num2) -> num2 - num1);
        for (int i = 0; i < k; ++i) {
            queue.offer(arr[i]);
        }
        for (int i = k; i < arr.length; ++i) {
            if (queue.peek() > arr[i]) {
                queue.poll();
                queue.offer(arr[i]);
            }
        }
        for (int i = 0; i < k; ++i) {
            res[i] = queue.poll();
        }
        return res;
    }


    /**
     * 给定一个字符串 s 和一个整数 k。你可以从 s 的前 k 个字母中选择一个，并把它加到字符串的末尾。
     * 返回 在应用上述步骤的任意数量的移动后，字典上最小的字符串。
     * @param s
     * @param k
     * @return
     */
    public static String orderlyQueue(String s, int k) {
        if (k==1){
            String res=s;
            for (int i = 0; i < s.length(); i++) {
                String s1 = s.substring(i) + s.substring(0, i);
                if (s1.compareTo(res)<0){
                    res=s1;
                }
            }
            return res;
        }else {
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            return new String(chars);
        }
    }

    /**
     *
     * @param grid
     * @return
     */
    public static int surfaceArea(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        int sum = 0;
        int verticalOverlap = 0;
        int rowOverlap = 0;
        int colOverlap = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sum += grid[i][j];

                if (grid[i][j] > 1) {
                    verticalOverlap += (grid[i][j] - 1);
                }

                if (j > 0) {
                    rowOverlap += Math.min(grid[i][j - 1], grid[i][j]);
                }

                if (i > 0) {
                    colOverlap += Math.min(grid[i - 1][j], grid[i][j]);
                }
            }
        }
        return sum * 6 - (verticalOverlap + rowOverlap + colOverlap) * 2;
    }


    public static void main(String[] args) {

        List<List<Integer>> generate = generate(5);
        for (int i = 0; i < generate.size(); i++) {
            List<Integer> integers = generate.get(i);
            for (int i1 = 0; i1 < integers.size(); i1++) {
                System.out.print(integers.get(i1));
            }
            System.out.println();
        }

    }

}
