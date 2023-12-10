package org.example.leetcode;

public class searchRange34 {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 3, 4,4, 4,5,5,77,77};
        int firstPosition = findFirstPosition(arr, 5);
        System.out.println(firstPosition);
        System.out.println(findLastPosition(arr, 5));
    }

    private static int findFirstPosition(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (arr[mid] < target) {
                left = mid + 1;
            } else if (arr[mid] > target) {
                right = mid - 1;
            } else if (arr[mid] == target) {
                if(arr[mid]==arr[mid-1]){
                    right = mid-1;
                }else {
                    return mid;
                }
            }
        }
        return -1;
    }

    private static int findLastPosition(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int middle = (left + right) / 2;
            if (arr[middle] == target) {
                if(arr[middle]==arr[middle+1]){
                    left = middle + 1;
                }else {
                    return middle;
                }
            } else if (arr[middle] > target) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return -1;
    }

}
