package org.pt.algo.demo;
/*
给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
请注意 ，必须在不复制数组的情况下原地对数组进行操作。
示例 1:
输入: nums = [0,1,0,3,12]
输出: [1,3,12,0,0]
示例 2:
输入: nums = [0]
输出: [0]
提示:
1 <= nums.length <= 104
-231 <= nums[i] <= 231 - 1
 */
public class moveZeroes_283 {
    public void moveZeroes(int[] nums) {
        // 指向第一个数
        int j=0;
        for (int i = 0; i < nums.length; i++) {
            // 如果此时指向的数不等于0，
            if(nums[i]!=0){
                // 位置i的数等于位置j,如果指向为0，则i右移
                nums[j]=nums[i];
                j++;
            }
        }
        for (int i = j; i <nums.length ; i++) {
            nums[i]=0;
        }
    }
}
