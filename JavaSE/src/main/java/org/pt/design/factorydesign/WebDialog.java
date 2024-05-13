package org.pt.design.factorydesign;

public class WebDialog extends Dialog{
    @Override
    Button createButton() {
        return new HTMLButton();
    }
}
