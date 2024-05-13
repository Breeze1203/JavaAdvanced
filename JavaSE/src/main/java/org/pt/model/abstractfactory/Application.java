package org.pt.model.abstractfactory;

public class Application {
    private Button button;
    private CheckBok checkBok;

    public Application(AbstractFactory factory) {
        button=factory.createButton();
        checkBok=factory.createCheckBox();
    }

    public void paint() {
        button.paint();
        checkBok.paint();
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public CheckBok getCheckBok() {
        return checkBok;
    }

    public void setCheckBok(CheckBok checkBok) {
        this.checkBok = checkBok;
    }
}
