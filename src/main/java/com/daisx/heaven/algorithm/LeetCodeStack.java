package com.daisx.heaven.algorithm;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @Author: dai.s.x
 * @Date: 2022/3/25 10:00
 */
public class LeetCodeStack {
    /**
     * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
     * @param height
     * @return
     */
    public static int trap(int[] height) {
        int ans = 0;
        Deque<Integer> stack = new LinkedList<>();
        int n = height.length;
        for (int i = 0; i < n; i++) {
           while (!stack.isEmpty()&&height[i]>height[stack.peek()]){
               int top = stack.pop();
               if (stack.isEmpty()){
                   break;
               }
               int left=stack.peek();
               int curWidth=i-left-1;
               int curHeight=Math.min(height[left],height[i])-height[top];
               ans+=curHeight*curWidth;
           }
           stack.push(i);
        }
        return ans;
    }


    public static void main(String[] args) {
        int [] h={0,1,0,2,1,0,1,3,2,1,2,1};
        int trap = trap(h);
        System.out.println(trap);

    }
}
