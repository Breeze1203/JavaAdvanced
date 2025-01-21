package com.example.kafkademo;

/**
 * @ClassName Channel
 * @Author pt
 * @Description
 * @Date 2025/1/16 13:36
 **/
public enum Channel {
    app(1),
    H5(1);

    Channel(int code) {
        this.code = code;
    }

    private final int code;

    public int getCode() {
        return code;
    }

    // 通过整数值获取枚举常量
    public static Channel getValue(int value) {
        for (Channel channel : Channel.values()) {
            if (channel.getCode() == value) {
                return channel;
            }
        }
        throw new IllegalArgumentException("Invalid channel value: " + value);
    }
}
