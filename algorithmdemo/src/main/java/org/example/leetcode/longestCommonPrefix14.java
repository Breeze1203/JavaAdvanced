package org.example.leetcode;
//编写一个函数来查找字符串数组中的最长公共前缀。
//
//        如果不存在公共前缀，返回空字符串 ""
//
//        示例 1：
//
//        输入：strs = ["flower","flow","flight"]
//        输出："fl"
//        示例 2：
//
//        输入：strs = ["dog","racecar","car"]
//        输出：""
//        解释：输入不存在公共前缀。
// 思路，最长公共前缀，一定是所有数组中最短的字符串
// 将每个字符与最短的字符比较，如果字符.startsWith（）最短字符串，则
// 最短字符串为公共最短前缀，如果不满足，则去掉最短字符串最后一位，接着比较，一次类推

import java.util.Arrays;

public class longestCommonPrefix14 {
    public static String longestCommonPrefix(String[] str) {
        // 如果一个字符串数组长度小于等于一，则没有
        if (str.length <= 1) return "";
        //公共前缀比所有字符串都短，找出最短先
        String s=str[0];
        for (int i = 1; i <str.length ; i++) {
            if(s.length()>str[i].length()){
                s=str[i];
            }
        }
        for (String string : str) {
            while(!string.startsWith(s)){
                if(s.length()==0)return "";
                //公共前缀不匹配就让它变短！
                s=s.substring(0,s.length()-1);
            }
        }
        return s;
    }

    public static void main(String[] args) {
        String[] s={"flowr","flow","floght"};
        String s1 = longestCommonPrefix(s);
        System.out.println(s1);
    }

}
