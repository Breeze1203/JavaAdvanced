package org.pt.model.abstractfactory;

public class WindowButton implements Button{
    @Override
    public void paint() {
        System.out.println("this is pain WindowButton");
    }
}
