package org.example;

public class Main {
    public static void main(String[] args) {
        boolean palindrome = isPalindrome(1212);
        System.out.println(palindrome);
    }

    public static boolean isPalindrome(int x) {
        if (x < 0 || x % 10 == 0) return false;
        int length = Integer.toString(x).length();
        int n = 0;
        int t = x;
        for (int i = 0; i < length / 2; i++) {
            n = n * 10 + x % 10;
            x = x / 10;
        }
        if (length % 2 != 0) {
            int p= (int) (t/Math.pow(10,length/2+1));
            if (p== n) {
                return true;
            } else {
                return false;
            }
        }else {
            int p= (int) (t/Math.pow(10,length/2));
            if (p==n) {
                return true;
            } else {
                return false;
            }
        }
    }
}