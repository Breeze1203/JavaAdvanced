package org.pt.algo.demo;
/*
给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
子数组 是数组中的一个连续部分
输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
输出：6
解释：连续子数组 [4,-1,2,1] 的和最大，为 6
 */

public class SubArraySum_53 {
    public static int maxSubArray(int[] nums) {
        int pre = 0;
        int max = nums[0];
        for (int i = 0; i < nums.length; i++) {
            // 如果累加的值是小于0，下一个是正数，直接舍弃掉前面的值
            pre = Math.max(pre, pre + nums[i]);
            max = Math.max(max, pre);
        }
        return max;
    }
}
