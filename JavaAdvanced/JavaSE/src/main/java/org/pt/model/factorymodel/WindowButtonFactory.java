package org.pt.model.factorymodel;

public class WindowButtonFactory extends Factory{
    @Override
    public Button createButton() {
        return new WindowButton();
    }
}
