package org.pt.design.observerdesign;

/**
 * @ClassName StatisticsDisplay
 * @Author pt
 * @Description
 * @Date 2025/6/18 12:50
 **/

public class StatisticsDisplay implements Observer {
    private float maxTemp = 0.0f;
    private float minTemp = 200f;
    private float tempSum = 0.0f;
    private int numReadings;

    public StatisticsDisplay(Subject weatherData) {
        weatherData.registerObserver(this);
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {
        tempSum += temperature;
        numReadings++;

        if (temperature > maxTemp) {
            maxTemp = temperature;
        }

        if (temperature < minTemp) {
            minTemp = temperature;
        }

        display();
    }

    public void display() {
        System.out.println("  统计布告板: 平均 " + (tempSum / numReadings)
                + "F 度, 最高 " + maxTemp + "F 度, 最低 " + minTemp + "F 度。");
    }
}