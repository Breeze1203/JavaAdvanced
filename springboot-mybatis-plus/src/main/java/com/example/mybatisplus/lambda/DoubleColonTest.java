package com.example.mybatisplus.lambda;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class DoubleColonTest {
    public static void main(String[] args) {
        Consumer<String> str = DoubleColon::printStr;
        str.accept("printStrConsumer");
        // 方法参数个数=函数式接口参数个数，通过【new 类的实例::方法名】引用
        // 使用的时候，直接传入需要的参数即可
        Consumer<Integer> toPrintConsumer = new DoubleColon()::printInteger;
        toPrintConsumer.accept(123);
        // 方法参数个数=函数式接口参数个数-1，通过【类的实例::方法名】引用
        // 使用的时候，传入的第一个参数是类的实例，后面是方法的参数
        Consumer<DoubleColon> toUpperConsumer = DoubleColon::toUpper;
        toUpperConsumer.accept(new DoubleColon());

        BiConsumer<DoubleColon, String> toLowerConsumer = DoubleColon::toLower;
        DoubleColon doubleColon = new DoubleColon();
        toLowerConsumer.accept(doubleColon, "toLowerConsumer");

        BiFunction<DoubleColon, String, Integer> toIntFunction = DoubleColon::toInt;
        int i = toIntFunction.apply(new DoubleColon(), "toInt");
        System.out.println(i);
    }
}
