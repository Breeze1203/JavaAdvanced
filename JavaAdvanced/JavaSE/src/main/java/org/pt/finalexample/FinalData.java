package org.pt.finalexample;

import java.util.Arrays;
import java.util.Random;

public class FinalData {
    private static Random rand=new Random(47);

    public FinalData(String id) {
        this.id = id;
    }

    private String id;
    private final int valueOne=9;
    private static final int VALUE_TWO=99;
    private static final int VALUE_THREE=39;
    private final int i4=rand.nextInt(20);
    private final int INT_5=rand.nextInt(20);
    private Value v1=new Value(11);
    private final Value v2=new Value(22);
    private static final  Value v3=new Value(33);
    private final int[] a={1,2,3,4,5,6};

    @Override
    public String toString() {
        return "FinalData{" +
                "id='" + id + '\'' +
                ", valueOne=" + valueOne +
                ", i4=" + i4 +
                ", INT_5=" + INT_5 +
                ", v1=" + v1 +
                ", v2=" + v2 +
                ", a=" + Arrays.toString(a) +
                '}';
    }

    public static void main(String[] args) {
        // final对基本数据类型来说，数值恒定不变
        // 对于引用类型，引用不可变，也就是一个被final修饰的引用变量，不能指向另一个引用
        FinalData fd1=new FinalData("fd1");
        // fd1.valueOne++; can't change
        fd1.v1=new Value(9);
        fd1.a[2]=3;
    }
}
