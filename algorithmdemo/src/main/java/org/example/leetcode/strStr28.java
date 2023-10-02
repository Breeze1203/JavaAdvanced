package org.example.leetcode;

public class strStr28 {
    public static int strStr(String haystack, String sta) {
        int i=0;
        String a=haystack;
        while(true){
            if(!a.startsWith(sta)){
                a=a.substring(i+1,a.length());
                i++;
            }else {
                break;
            }
        }
        return i;
    }

    public static void main(String[] args) {
        int i = strStr("haystack", "sta");
        System.out.println(i);

    }
}
