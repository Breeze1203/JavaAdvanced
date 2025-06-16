package org.pt.design.factorydesign;

public class WindowsDialog extends Dialog{
    @Override
    Button createButton() {
        return new WindowsButton();
    }
}
