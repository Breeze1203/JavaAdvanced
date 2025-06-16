package org.pt.array;

public class ArraysOfPrimitives {
    public static void main(String[] args) {
        int[] a={1,2,3,4,5,6};
        int[] b;
        // 将a的引用指向b 所有对b元素的操作后 a数组也会变化
        b=a;
        for(int i=0;i< b.length;i++){
            b[i]=b[i]+1;
        }
        for(int i=0;i< a.length;i++){
            System.out.println(a[i]);
        }

    }
}
