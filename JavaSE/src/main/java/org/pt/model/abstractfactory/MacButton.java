package org.pt.model.abstractfactory;

public class MacButton implements Button{
    @Override
    public void paint() {
        System.out.println("this is paint MacButton");
    }
}
