package org.example.sort;

public class InsertionSort {
    public static <E extends Comparable> void sort(E[] arr) {
        for (int i = 0; i < arr.length; i++) {
            // 将arr[i]插入到合适位置
            for (int j = i; i - 1 >= 0 && arr[j].compareTo(arr[j - 1]) < 0; j--) {
                swap(arr, j, j - 1);
            }
        }
    }
/*
1->4->3->5 从1开始遍历，j-1不满足条件，下标跳到2；此时4>1不满足条件，下标到3，此时满足条件
将4平移到3的位置 1->4->4->5，因为3的位置之前存储过，直接将要插入的位置赋值为3

 */
    public static <E extends Comparable> void sort2(E[] arr) {
        for (int i = 0; i < arr.length; i++) {
            // 将当前遍历的值存储起来
            E e = arr[i];
            int j;
            for (j = i; j - 1 >= 0 && arr[j - 1].compareTo(arr[j]) < 0;j--){
                arr[j]=arr[j-1];
            }
            arr[j]=e;
        }
    }

    private static <E> void swap(E[] arr, int i, int j) {
        E temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;

    }
}
