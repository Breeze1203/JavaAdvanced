package org.pt.model.abstractfactory;

public class WindowFactory implements AbstractFactory{
    @Override
    public Button createButton() {
        return new WindowButton();
    }

    @Override
    public CheckBok createCheckBox() {
        return new WindowCheckBox();
    }
}
