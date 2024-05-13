package org.pt.upcast;

public class Wind extends Instrument{
    public static void main(String[] args) {
        Wind wind = new Wind();
        // 向上转型 将子类对象赋值给父类引用
        Instrument.tune(wind);
    }
}
