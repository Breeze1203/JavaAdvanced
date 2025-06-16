package org.pt.design.templatedesign;

public class BasketBallGame extends Game{


    @Override
    protected void initialize() {
        System.out.println("BasketBallGame initialize.....");
    }

    @Override
    protected void startPlay() {
        System.out.println("BasketBallGame startPlay.....");
    }

    @Override
    protected void endPlay() {
        System.out.println("BasketBallGame end.....");
    }
}
