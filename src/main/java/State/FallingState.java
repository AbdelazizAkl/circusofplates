package State;

import Model.GameObjectAbstract;
import eg.edu.alexu.csd.oop.game.GameObject;

public class FallingState implements MovingState {

    GameObjectAbstract movingObject;

    public FallingState(GameObjectAbstract movingObject) {
        this.movingObject = movingObject;
    }

    @Override
    public void letObjectFall(int speed) {
        movingObject.setY(movingObject.getY() + speed);
    }

    @Override
    public void placeOnTop(GameObject top, int x) {

        if (movingObject.getHeight() == 7) {
            movingObject.setY(top.getY() - 5);
        } else {
            movingObject.setY(top.getY() - 18);
        }
        movingObject.setX(x);
        movingObject.setcurrentMovingState(movingObject.getCaughtState());
    }

    @Override
    public void stackOfThree() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void moveWithControlledObjects(GameObject bar) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void resetPosition(int screenWidth) {

        movingObject.setY(0);
        movingObject.setX((int) (Math.random() * screenWidth));
        
        movingObject.setcurrentMovingState(movingObject.getStartPositionState());
    }

}
