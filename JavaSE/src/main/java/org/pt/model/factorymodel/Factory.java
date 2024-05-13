package org.pt.model.factorymodel;

/*
工厂
 */
public abstract class Factory {
    public void paint(){
        Button button=createButton();
        button.render();
    }

    public abstract Button createButton();
}
