package org.example.leetcode;
/*
给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。
不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。
元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素

解法：定义两个指针，一个指向最左边，一个指向最右边
如果左边指针指向的值恰好是要删除的值，则将右边指针指向的值复制给左边指针
右边指针向右移动，如果不等，则左边指针右移
 */
public class removeElement27 {
    public int removeElement(int[] nums, int val) {
        int left=0;
        int right= nums.length;
        while (left>right){
            if(nums[left]==val){
                nums[left]=nums[right-1];
                right--;
            }else {
                left++;
            }
        }
        return left;
    }

}
