package com.daisx.heaven.algorithm;


import java.util.*;

/**
 * @Author: dai.s.x
 * @Date: 2022/3/1 10:35
 */
public class LeetCodeArray {

    /**
     * 以字符串形式打印数组
     * @param array
     * @return
     */
    private static String printArray(int[] array){
        if (array==null||array.length==0){
            return "[]";
        }
        StringBuilder s=new StringBuilder("[");
        for (int i=0;i<array.length;i++){
            s.append(array[i]);
            if (i!=array.length-1){
                s.append(",");
            }
        }
        s.append("]");
        return s.toString();
    }

    /**
     * 以字符串形式打印矩阵
     * @param matrix
     * @return
     */
    private static String printMatrix(int[][] matrix){
        StringBuilder s=new StringBuilder();
        int length = matrix.length;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                s.append(matrix[i][j]).append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }

    /**
     *把数组中从[start，end]之间的元素两两交换,也就是反转
     * @param nums
     * @param start
     * @param end
     */
    public static void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start++] = nums[end];
            nums[end--] = temp;
        }
    }

    /**
     * 【删除排序数组中的重复项】
     * 一个 升序排列 的数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，返回删除后数组的新长度。元素的 相对顺序 应该保持 一致 。
     * @param nums
     */
    private static int removeDuplicates1(int[] nums){
        if (nums==null||nums.length==0){
            return 0;
        }
        int left=0;
        for (int right=1;right<nums.length;right++){
            if (nums[left]!=nums[right]){
                nums[++left]=nums[right];
            }
        }

        return ++left;
    }

    private static int removeDuplicates2(int[] nums){
        if (nums==null||nums.length==0){
            return 0;
        }
        int left=0;
        int right=1;
        while (right<nums.length){
            if (nums[left]!=nums[right]){
                if (right-left>1){
                    nums[++left]=nums[right];
                }else {
                    left++;
                }
            }
            right++;
        }
        return ++left;
    }

    /**
     * 买卖股票的最佳时机 II(动态规划)
     * 给定一个数组 prices ，其中prices[i] 表示股票第 i 天的价格。在每一天，你可能会决定购买和/或出售股票。你在任何时候最多只能持有 一股 股票。
     * 你也可以购买它，然后在 同一天 出售。返回 你能获得的 最大 利润
     * @param prices
     * @return
     */
    private static int maxProfit1(int[] prices) {
        if (prices==null || prices.length<2){
            return 0;
        }
        int hold= -prices[0];
        int noHold=0;
        for (int i=1;i<prices.length;i++){
            hold=Math.max(noHold-prices[i],hold);
            noHold=Math.max(hold+prices[i],noHold);
        }
        return noHold;
    }

    /**
     * 买卖股票的最佳时机 II(贪心算法)
     * 给定一个数组 prices ，其中prices[i] 表示股票第 i 天的价格。在每一天，你可能会决定购买和/或出售股票。你在任何时候最多只能持有 一股 股票。
     * 你也可以购买它，然后在 同一天 出售。返回 你能获得的 最大 利润
     * @param prices
     * @return
     */
    private static int maxProfit2(int[] prices) {
        if (prices==null || prices.length<2){
            return 0;
        }
        int total= 0;
        int index=1;
        while (index < prices.length){
            total+=Math.max(prices[index]-prices[index-1],0);
            index++;
        }
        return total;
    }

    /**
     * 旋转数组
     * 给你一个数组，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。
     * @param nums
     * @param k
     */
    public static void rotate1(int[] nums, int k){
        if (nums==null||nums.length<2||k==0){
            return;
        }
        int length = nums.length;
        int temp[] = new int[length];
        for (int i = 0; i < length; i++) {
            temp[i] = nums[i];
        }
        for (int i = 0; i < length; i++) {
            nums[(i + k) % length] = temp[i];
        }
    }

    public static void rotate2(int[] nums, int k) {
        if (nums==null||nums.length<2||k==0){
            return;
        }
        int length = nums.length;
        k %= length;
        //先反转全部的元素
        reverse(nums, 0, length - 1);
        //在反转前k个元素
        reverse(nums, 0, k - 1);
        //接着反转剩余的
        reverse(nums, k, length - 1);
    }

    /**
     * 存在重复元素
     * 给你一个整数数组 nums 。如果任一值在数组中出现 至少两次 ，返回 true ；如果数组中每个元素互不相同，返回 false 。
     * @param nums
     * @return
     */
    public static boolean containsDuplicate(int[] nums) {
        if (nums==null||nums.length<2){
            return false;
        }
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (!set.add(num)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 只出现一次的数字(异或)
     * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
     * @param nums
     * @return
     */
    public static int singleNumber1(int[] nums) {
        int result = 0;
        for (int i = 0; i < nums.length; i++) {
            result ^= nums[i];
        }
        return result;
    }

    public static int[] singleNumber2(int[] nums) {

        Set<Integer> set=new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (!set.add(nums[i])){
                set.remove(nums[i]);
            }
        }
        int[] res=new int[set.size()];
        Iterator<Integer> iterator = set.iterator();
        for (int i=0;i<set.size();i++){
            res[i]=iterator.next();
        }
        return res;
    }

    /**
     *两个数组的交集 II
     * 给你两个整数数组nums1 和 nums2 ，请你以数组形式返回两数组的交集。返回结果中每个元素出现的次数，
     * 应与元素在两个数组中都出现的次数一致（如果出现次数不一致，则考虑取较小值）。可以不考虑输出结果的顺序。
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] intersect1(int[] nums1, int[] nums2) {
        HashMap<Integer, Integer> map = new HashMap<>();
        ArrayList<Integer> list = new ArrayList<>();

        //先把数组nums1的所有元素都存放到map中，其中key是数组中的元素，value是这个元素出现在数组中的次数
        for (int i = 0; i < nums1.length; i++) {
            map.put(nums1[i], map.getOrDefault(nums1[i], 0) + 1);
        }

        //然后再遍历nums2数组，查看map中是否包含nums2的元素，如果包含，
        //就把当前值加入到集合list中，然后再把对应的value值减1。
        for (int i = 0; i < nums2.length; i++) {
            if (map.getOrDefault(nums2[i], 0) > 0) {
                list.add(nums2[i]);
                map.put(nums2[i], map.get(nums2[i]) - 1);
            }
        }

        //把集合list转化为数组
        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }
        return res;
    }

    public static int[] intersect2(int[] nums1, int[] nums2) {
        // 先对两个数组进行排序
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int i = 0;
        int j = 0;
        ArrayList<Integer> list = new ArrayList<>();
        while (i<nums1.length&&j<nums2.length){
            if (nums1[i]==nums2[j]){
                list.add(nums1[i]);
                i++;
                j++;
            }else if (nums1[i]<nums2[j]){
                i++;
            }else {
                j++;
            }
        }
        int[] res=new int[list.size()];
        for (int i1 = 0; i1 < list.size(); i1++) {
            res[i1]=list.get(i1);
        }

        return res;
    }


    /**
     * 给定一个由 整数 组成的 非空 数组所表示的非负整数，在该数的基础上加一。
     * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
     * 你可以假设除了整数 0 之外，这个整数不会以零开头。
     * @param digits
     * @return
     */
    public static int[] plusOne(int[] digits) {
        int length = digits.length;
        for (int i = length - 1; i >= 0; i--) {
            if (digits[i] != 9) {
                //如果数组当前元素不等于9，直接加1
                //然后直接返回
                digits[i]++;
                return digits;
            } else {
                //如果数组当前元素等于9，那么加1之后
                //肯定会变为0，我们先让他变为0
                digits[i] = 0;
            }
        }
        //除非数组中的元素都是9，否则不会走到这一步，
        //如果数组的元素都是9，我们只需要把数组的长度
        //增加1，并且把数组的第一个元素置为1即可
        int temp[] = new int[length + 1];
        temp[0] = 1;
        return temp;
    }


    /**
     * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序
     * @param nums
     */
    public static void moveZeroes1(int[] nums) {
        int i = 0;
        for (int j = 0; j < nums.length; j++) {
            if (nums[j] != 0) {
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
                i++;
            }
        }

    }

    public static void moveZeroes2(int[] nums) {
       int i=0;
       int j=0;
       while (j<nums.length){
           if (nums[j]!=0){
               int temp=nums[j];
               nums[j]=nums[j-i];
               nums[j-i]=temp;
           } else {
               i++;
           }
           j++;
       }

    }

    /**
     * 给定一个整数数组 nums和一个整数目标值 target，请你在该数组中找出 和为目标值 target 的那两个整数，并返回它们的数组下标。
     * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
     * 你可以按任意顺序返回答案
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum1(int[] nums, int target) {
        int i=0;
        int j=1;
        while (nums[i]+nums[j]!=target){
            if (j==nums.length-1){
                i++;
                j=i;
            }
            j++;
        }
        return new int[]{i,j};
    }
    public static int[] twoSum2(int[] nums, int target) {
        HashMap<Integer, Integer> m = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (m.get(target - nums[i]) != null) {
                return new int[]{m.get(target - nums[i]), i};
            }
            m.put(nums[i], i);
        }
        return new int[]{0,0};
    }


    /**
     * 请你判断一个9 x 9 的数独是否有效。只需要 根据以下规则 ，验证已经填入的数字是否有效即可。
     * 数字1-9在每一行只能出现一次。
     * 数字1-9在每一列只能出现一次。
     * 数字1-9在每一个以粗实线分隔的3x3宫内只能出现一次。
     * @param board
     */
    public static boolean isValidSudoku(char[][] board) {
        int[] line = new int[9];
        int[] column = new int[9];
        int[] cell = new int[9];
        int shift = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                //如果还没有填数字，直接跳过
                if (board[i][j] == '.') {
                    continue;
                }
                shift = 1 << (board[i][j] - '0');
                int k = (i / 3) * 3 + j / 3;
                //如果对应的位置只要有一个大于0，说明有冲突，直接返回false
                if ((column[i] & shift) > 0 || (line[j] & shift) > 0 || (cell[k] & shift) > 0) {
                    return false;
                }
                column[i] |= shift;
                line[j] |= shift;
                cell[k] |= shift;
            }
        }
        return true;
    }

    /**
     *给定一个 n×n 的二维矩阵matrix 表示一个图像。请你将图像顺时针旋转 90 度。
     * 你必须在 原地 旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要 使用另一个矩阵来旋转图像。
     * @param matrix  先上下交换，在对角线交换
     */
    public static void rotate1(int[][] matrix) {
        int length = matrix.length;
        //先上下交换
        for (int i = 0; i < length / 2; i++) {
            int temp[] = matrix[i];
            matrix[i] = matrix[length - i - 1];
            matrix[length - i - 1] = temp;
        }
        //在按照对角线交换
        for (int i = 0; i < length; ++i) {
            for (int j = i + 1; j < length; ++j) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
    }

    public static void rotate2(int[][] matrix) {
        int length = matrix.length;
        //因为是对称的，只需要计算循环前半行即可
        for (int i = 0; i < length / 2; i++) {
            for (int j = i; j < length - i - 1; j++) {
                int temp = matrix[i][j];
                int m = length - j - 1;
                int n = length - i - 1;
                matrix[i][j] = matrix[m][i];
                matrix[m][i] = matrix[n][m];
                matrix[n][m] = matrix[j][n];
                matrix[j][n] = temp;
            }
        }
    }

    /**
     * 给你一个 无重复元素 的整数数组candidates 和一个目标整数target，找出candidates中可以使数字和为目标数target 的 所有不同组合 ，并以列表形式返回。你可以按 任意顺序 返回这些组合。
     * candidates 中的 同一个 数字可以 无限制重复被选取 。如果至少一个数字的被选数量不同，则两种组合是不同的。
     * 对于给定的输入，保证和为target 的不同组合数少于 150 个。
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> combine = new ArrayList<>();
        dfs(candidates, target, ans, combine, 0);
        return ans;
    }

    public void dfs(int[] candidates, int target, List<List<Integer>> ans, List<Integer> combine, int idx) {
        if (idx == candidates.length) {
            return;
        }
        if (target == 0) {
            ans.add(new ArrayList<>(combine));
            return;
        }
        // 直接跳过
        dfs(candidates, target, ans, combine, idx + 1);
        // 选择当前数
        if (target - candidates[idx] >= 0) {
            combine.add(candidates[idx]);
            dfs(candidates, target - candidates[idx], ans, combine, idx);
            combine.remove(combine.size() - 1);
        }
    }

    /**
     *
     * @param nums
     * @return
     */
    public int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int mid = (left + right) / 2;
            if (nums[mid] > nums[right]){
                left = mid + 1;
            }else if (nums[mid] < nums[right]){
                right = mid;
            } else{
                right = right - 1;
            }
        }
        return nums[left];
    }

    public static void main(String[] args) {

    }
}
