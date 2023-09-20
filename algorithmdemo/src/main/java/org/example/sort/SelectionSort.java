package org.example.sort;

import java.lang.module.Configuration;

public class SelectionSort {
    public static <E extends Comparable> void sort(E[] arr){
        for (int i = 0; i < arr.length; i++) {
            // 选择arr[i.....n]中最小索引值
            int minIndex=i;
            // 比较索引i后面的数组值
            for(int j=i;j<arr.length;j++){
                if(arr[j].compareTo(arr[minIndex])<0){
                    minIndex=j;
                }
            }
            // 交换索引位置的值
            swap(arr,i,minIndex);
        }
    }


    // swap(arr,i,minIndex);
    private static <E> void swap(E[] arr,int i,int j){
        E temp=arr[i];
        arr[i]=arr[j];
        arr[j]=temp;
    }

    public static void main(String[] args) {
        Integer[] arr={1,4,2,3,6,5};
//        SelectionSort.sort(arr);
        InsertionSort.sort(arr);
        for (int i = 0; i <arr.length ; i++) {
            System.out.print(arr[i]+"->");
        }
        System.out.println();

        Student[] students=new Student[]{new Student("zs",97),new Student("ls",99),new Student("ww",76)};
        SelectionSort.sort(students);
        for (Student s:students) {
            System.out.println(s);
        }

    }
}
