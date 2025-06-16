package org.pt.algo.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j],
nums[k]] 满足 i != j、i != k 且 j != k ，同时还满足 nums[i] + nums[j] + nums[k] == 0 。请
你返回所有和为 0 且不重复的三元组。
注意：答案中不可以包含重复的三元组
[-4,-1,-1,0,1,2]

i=1 num[i]=-1 l=2 r=5 l+r=1 sum=0;[-1,-1,2]
 */
public class threeSum_11 {

    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; i++) {
            if (nums[i] > 0) break;
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            int left = i + 1;
            int right = nums.length - 1;
            int sum;
            while (left < right) {
                sum = nums[i] + nums[left] + nums[right];
                if (sum > 0) {
                    right=right-1; //移动指针
                    while (left < right && nums[right] == nums[right + 1]) right--;
                } else if (sum < 0) {
                    left=left+1; //移动指针
                    while (left < right && nums[left] == nums[left - 1]) left++;
                } else {
                    res.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    right--; // 移动指针
                    left++; // 移动指针
                    while (left < right && nums[right] == nums[right + 1]) right--;
                    while (left < right && nums[left] == nums[left - 1]) left++;
                }
            }
        }
        return res;
    }

}
