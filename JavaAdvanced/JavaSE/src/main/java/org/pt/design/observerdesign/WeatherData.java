package org.pt.design.observerdesign;

/**
 * @ClassName WeatherData
 * @Author pt
 * @Description
 * @Date 2025/6/18 12:39
 **/

import java.util.ArrayList;
import java.util.List;

public class WeatherData implements Subject {
    private List<Observer> observers; // 存储所有注册的观察者
    private float temperature;
    private float humidity;
    private float pressure;

    public WeatherData() {
        observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
        System.out.println("观察者 [" + o.getClass().getSimpleName() + "] 已注册。");
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
        System.out.println("观察者 [" + o.getClass().getSimpleName() + "] 已移除。");
    }

    @Override
    public void notifyObservers() {
        System.out.println("\n通知所有观察者：气象数据已更新。");
        for (Observer observer : observers) {
            // 调用每个观察者的 update 方法，传递最新的气象数据
            observer.update(temperature, humidity, pressure);
        }
    }

    /**
     * 当气象数据发生变化时，调用此方法，并通知所有观察者
     * @param temperature 新的温度
     * @param humidity 新的湿度
     * @param pressure 新的气压
     */
    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged(); // 数据变化后通知观察者
    }

    private void measurementsChanged() {
        notifyObservers(); // 通知所有注册的观察者
    }

    // 也可以提供获取当前测量值的方法，供观察者按需拉取数据
    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getPressure() {
        return pressure;
    }
}
