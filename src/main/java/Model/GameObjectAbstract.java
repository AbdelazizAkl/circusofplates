package Model;

import State.CaughtState;
import State.FallingState;
import State.MovingState;
import State.StartPositionState;
import eg.edu.alexu.csd.oop.game.GameObject;
import java.awt.image.BufferedImage;

public class GameObjectAbstract implements GameObject {

    protected int MAX_MSTATE;
    protected BufferedImage[] spriteImages;
    protected int x;
    protected int y;
    protected boolean visible;
    protected int type;

    protected MovingState startPositionState = new StartPositionState(this);
    protected MovingState fallingState = new FallingState(this);
    protected MovingState caughtState = new CaughtState(this);
    protected MovingState currentMovingState = startPositionState;

    public MovingState getStartPositionState() {
        return startPositionState;
    }

    public MovingState getFallingState() {
        return fallingState;
    }

    public MovingState getCaughtState() {
        return caughtState;
    }


    public MovingState getCurrentState() {
        return currentMovingState;
    }

    public void setcurrentMovingState(MovingState movingState) {
        this.currentMovingState = movingState;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getWidth() {
        return spriteImages[0].getWidth();
    }

    @Override
    public int getHeight() {
        return spriteImages[0].getHeight();
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public BufferedImage[] getSpriteImages() {
        return this.spriteImages;
    }
    
    public boolean isPlate() {
        return this.getHeight() == 7;
    }

    public boolean isSquare() {
        return this.getHeight() == 20;
    }
}
