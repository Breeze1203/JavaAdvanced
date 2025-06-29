package org.pt.design.observerdesign;

public interface Observer {
    /**
     * 当主题状态发生变化时，此方法会被调用以更新观察者
     * @param temperature 温度数据
     * @param humidity 湿度数据
     * @param pressure 气压数据
     */
    void update(float temperature, float humidity, float pressure);
}
