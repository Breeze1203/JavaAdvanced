package org.pt.design.observerdesign;

public interface Subject {

    /**
     * 注册观察者
     * @param o
     */
     void registerObserver(Observer o);

    /**
     * 移除观察者
     * @param o 要移除的观察者
     */
     void removeObserver(Observer o);

    /**
     * 当主题状态改变时，通知所有注册的观察者
     */
     void notifyObservers();
}
