package org.pt.model.factorymodel;


public class MacButtonFactory extends Factory {

    @Override
    public Button createButton() {
        return new MacButton();
    }
}
