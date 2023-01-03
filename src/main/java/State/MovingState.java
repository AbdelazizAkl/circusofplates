
package State;

import eg.edu.alexu.csd.oop.game.GameObject;

public interface MovingState {
    
    public void letObjectFall(int speed);
    
    public void placeOnTop(GameObject top, int x);
    
    public void stackOfThree();
    
    public void moveWithControlledObjects(GameObject bar);
    
    public void resetPosition(int screenWidth);
}
