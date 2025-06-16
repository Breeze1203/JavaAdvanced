package org.pt.model.abstractfactory;

/*
具体工厂
 */
public class MacFactory implements AbstractFactory{
    @Override
    public Button createButton() {
        return new MacButton();
    }

    @Override
    public CheckBok createCheckBox() {
        return new MacCheckBox();
    }
}
