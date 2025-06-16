package org.pt.design.templatedesign;

/*
在Java中，四个作用域修饰符及其作用范围如下：

1. **default（无修饰符）**：同一包内可见。
2. **public**：任何地方都可见。
3. **protected**：同一包内及不同包的子类可见。
4. **private**：仅在本类内可见。

 */
public abstract class Game {
    protected abstract void initialize();

    protected abstract void startPlay();

    protected abstract void endPlay();

    protected final void playGame(){
        initialize();
        startPlay();
        endPlay();
    }

    public static void main(String[] args) {
        FootBall footBall = new FootBall();
        footBall.initialize();
        footBall.startPlay();
        footBall.endPlay();
        BasketBallGame basketBallGame = new BasketBallGame();
        basketBallGame.initialize();
        basketBallGame.startPlay();
        basketBallGame.endPlay();
    }
}
