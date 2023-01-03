
package State;

import Model.GameObjectAbstract;
import eg.edu.alexu.csd.oop.game.GameObject;


public class CaughtState implements MovingState{
    
    GameObjectAbstract movingObject;

    public CaughtState(GameObjectAbstract movingObject) {
        this.movingObject = movingObject;
    }

    @Override
    public void letObjectFall(int speed) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void placeOnTop(GameObject top, int x) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void moveWithControlledObjects(GameObject bar) {
        movingObject.setX(bar.getX() - 8);
    }

    @Override
    public void resetPosition(int screenWidth) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void stackOfThree() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
