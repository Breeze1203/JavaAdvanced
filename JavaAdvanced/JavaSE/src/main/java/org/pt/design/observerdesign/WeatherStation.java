package org.pt.design.observerdesign;

/**
 * @ClassName WeatherStation
 * @Author pt
 * @Description
 * @Date 2025/6/18 12:51
 **/

public class WeatherStation {
    public static void main(String[] args) {
        // 1. 创建主题（被观察者）
        WeatherData weatherData = new WeatherData();
        // 2. 创建观察者，并在创建时注册到主题
        CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay(weatherData);
        StatisticsDisplay statisticsDisplay = new StatisticsDisplay(weatherData);

        // 3. 模拟气象数据变化并通知观察者
        System.out.println("\n--- 第一次气象数据更新 ---");
        weatherData.setMeasurements(80, 65, 30.4f);

        System.out.println("\n--- 第二次气象数据更新 ---");
        weatherData.setMeasurements(82, 70, 29.2f);

        // 4. 移除一个观察者
        System.out.println("\n--- 移除统计布告板 ---");
        weatherData.removeObserver(statisticsDisplay);

        // 5. 第三次气象数据更新，此时统计布告板不再接收通知
        System.out.println("\n--- 第三次气象数据更新 ---");
        weatherData.setMeasurements(78, 90, 29.0f);
    }
}
