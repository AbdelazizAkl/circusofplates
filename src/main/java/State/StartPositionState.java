package State;

import Model.GameObjectAbstract;
import eg.edu.alexu.csd.oop.game.GameObject;

public class StartPositionState implements MovingState {

    GameObjectAbstract movingObject;

    public StartPositionState(GameObjectAbstract movingObject) {
        this.movingObject = movingObject;
    }
    
    @Override
    public void placeOnTop(GameObject top, int x) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
    }

    @Override
    public void letObjectFall(int speed) {
        movingObject.setY(movingObject.getY() + speed);
        movingObject.setcurrentMovingState(movingObject.getFallingState());
    }
}
