package com.daisx.heaven.algorithm;

import java.util.*;

/**
 * @Author: dai.s.x
 * @Date: 2022/3/24 14:08
 */
public class LeetCodeTree {
    /**
     * 二叉树结构
     */
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 给你二叉树的根节点 root ，返回其节点值的 层序遍历 。 （即逐层地，从左到右访问所有节点）
     * @param root
     * @return
     */
    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res=new ArrayList<>();
        if (root==null){
            return res;
        }
        Queue<TreeNode> current = new LinkedList<>();
        current.add(root);
        while (!current.isEmpty()){
            List<Integer> child=new ArrayList<>();
            for (int i = 0; i < current.size(); i++) {
                TreeNode node = current.poll();
                child.add(node.val);
                if (node.left!=null){
                    current.add(node.left);
                }
                if (node.right!=null){
                    current.add(node.right);
                }
            }
            res.add(child);
        }
        return res;
    }
}
