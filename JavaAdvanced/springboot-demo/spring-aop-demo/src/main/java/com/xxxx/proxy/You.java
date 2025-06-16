package com.xxxx.proxy;

/**
 * 静态代理 -> 目标角色（真实角色）
 *      实现行为
 */
public class You implements Marry {
    // 实现行为
    @Override
    public void toMarry() {
        System.out.println("我要结婚了...");
    }

    @Override
    public String toMarry02() {
        System.out.println("二婚...");
        return "Hello";
    }
}
