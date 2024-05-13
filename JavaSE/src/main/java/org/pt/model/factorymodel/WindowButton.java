package org.pt.model.factorymodel;

/*
window产品
 */
public class WindowButton implements Button{
    @Override
    public void render() {
        System.out.println("Window Button render");
    }

    @Override
    public void onClick() {
        System.out.println("Window Button Click");
    }
}
