package org.pt.model.abstractfactory;
/*
抽象工厂，被具体工厂实现
 */
public interface AbstractFactory {
    public Button createButton();
    public CheckBok createCheckBox();
}
