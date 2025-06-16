package org.pt.design.factorydesign;

public class WindowsButton implements Button{

    @Override
    public void render() {
        // 根据 WINDOW 样式渲染按钮
    }

    @Override
    public void onClick(Runnable f) {
        // 绑定本地操作系统点击事件
    }
}
