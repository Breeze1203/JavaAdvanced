package org.pt.proxy;

public class SpaceShipDelegation {
    private String name;

    public SpaceShipDelegation(String name) {
        this.name = name;
    }

    private SpaceShipControls controls=new SpaceShipControls();

    void up(int velocity){
        controls.up(velocity);
    };
    void down(int velocity){
        controls.down(velocity);
    };
    void left(int velocity){
        controls.left(velocity);
    };
    void right(int velocity){
        controls.right(velocity);
    };
    void forward(int velocity){
        controls.forward(velocity);
    };

}
