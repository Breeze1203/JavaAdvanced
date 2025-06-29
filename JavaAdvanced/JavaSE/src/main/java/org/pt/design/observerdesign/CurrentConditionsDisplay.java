package org.pt.design.observerdesign;

/**
 * @ClassName CurrentConditionsDisplay
 * @Author pt
 * @Description
 * @Date 2025/6/18 12:41
 **/

public class CurrentConditionsDisplay implements Observer {
    private float temperature;
    private float humidity;
    // private Subject weatherData; // 如果需要取消注册，可以持有主题引用

    // 构造器可以接收 Subject 实例，并在创建时注册自己
    public CurrentConditionsDisplay(Subject weatherData) {
        // this.weatherData = weatherData; // 可以选择持有引用
        weatherData.registerObserver(this); // 在创建时自动注册
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        display(); // 更新后显示
    }

    public void display() {
        System.out.println("  当前布告板: " + temperature + "F 度, " + humidity + "% 湿度。");
    }
}