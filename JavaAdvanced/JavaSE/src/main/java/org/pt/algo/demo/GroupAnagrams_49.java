package org.pt.algo.demo;
/*
给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
字母异位词 是由重新排列源单词的所有字母得到的一个新单词。
示例 1:
输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
示例 2:
输入: strs = [""]
输出: [[""]]

由于互为字母异位词的两个字符串包含的字母相同，因此对两个字符串分别进行排序之后得到的字符串一定是相同的
，故可以将排序之后的字符串作为哈希表的键
 */
import java.util.*;

public class GroupAnagrams_49 {
    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        for (String str : strs) {
            char[] array = str.toCharArray();
            Arrays.sort(array);
            String key = new String(array);
            //如果 map 中存在键为 key 的映射，则会返回与该键关联的值；如果不存在，则会返回一个新创建的 ArrayList<String> 对象作为默认值
            List<String> list = map.getOrDefault(key, new ArrayList<String>());
            list.add(str);
            map.put(key, list);
        }
        return new ArrayList<List<String>>(map.values());
    }

    public static void main(String[] args) {
        String[] strs = {"eat", "tea", "tan", "ate", "nat", "bat"};
        System.out.println(groupAnagrams(strs));
        Map<String, String> map = new HashMap<String, String>();
        map.put("s1", "a");
        map.put("s1", "b");
        System.out.println(map.containsKey("s1"));
        String s1 = map.get("s1");
        System.out.println(s1);
    }
}
