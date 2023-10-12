package org.example;

public class test {
    public static int FindMinimumValue(int[] arr) {
      return FindAndMerge(arr);
    }

    private static int FindAndMerge(int[] arr) {
        int start = 0;
        int end = arr.length - 1;

        while (start < end) {
            int mid = (start + end) / 2;
            int leftMin = Math.min(arr[start], arr[mid]);
            int rightMin = Math.min(arr[mid + 1], arr[end]);
            start = leftMin < rightMin ? start : mid + 1;
            end = leftMin < rightMin ? mid : end;
        }

        return arr[start];
    }

    public static void main(String[] args) {
        int[] arr = {5, 3, 9, 1, 7};
        int min = FindMinimumValue(arr);
        System.out.println("最小值: " + min);
    }
    


}
