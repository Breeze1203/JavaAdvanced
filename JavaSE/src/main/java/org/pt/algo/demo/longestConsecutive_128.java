package org.pt.algo.demo;
/*
给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
        请你设计并实现时间复杂度为 O(n) 的算法解决此问题。
        示例 1：
        输入：nums = [100,4,200,1,3,2]
        输出：4
        解释：最长数字连续序列是 [1, 2, 3, 4]。它的长度为 4。
        示例 2：
        输入：nums = [0,3,7,2,5,8,4,6,0,1]
        输出：9

        怎么判断呢，就是用哈希表查找这个数前面一个数是否存在，即num-1在序列中是否存在。存在那这个数肯定不是开头，直接跳过。
因此只需要对每个开头的数进行循环，直到这个序列不再连续，因此复杂度是O(n)。 以题解中的序列举例:
[100，4，200，1，3，4，2]
去重后的哈希序列为：
[100，4，200，1，3，2]
按照上面逻辑进行判断：
元素100是开头,因为没有99，且以100开头的序列长度为1
元素4不是开头，因为有3存在，过，
元素200是开头，因为没有199，且以200开头的序列长度为1
元素1是开头，因为没有0，且以1开头的序列长度为4，因为依次累加，2，3，4都存在。
元素3不是开头，因为2存在，过，
元素2不是开头，因为1存在，过。
完
 */

import java.util.HashSet;

public class longestConsecutive_128 {
    public int longestConsecutive(int[] nums) {
        int size=0;
        // 去重
        HashSet<Integer> integerHashSet = new HashSet<>();
        for (Integer i:nums) {
            integerHashSet.add(i);
        }
        //遍历hashset
        for (Integer s:integerHashSet) {
            // 当前遍历的数，如果hashset里面有比他小一的数字，说明他不是第一个数，直接跳过，反之继续
            if(!integerHashSet.contains(s-1)){
                // 存储当前变量的值
                int current=s;
                // 序列数为1
                int currentStack=1;
                // 如果有比当前这个数刚好大一的数
                while (integerHashSet.contains(current+1)){
                    // 序列数自增
                    currentStack=currentStack+1;
                    // 当前数增一，接着遍历
                    current=current+1;
                }
                // 取current和size中较大的一个
                size=Math.max(size,currentStack);
            }
        }

        return size;
    }
}
