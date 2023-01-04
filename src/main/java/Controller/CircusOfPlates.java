package Controller;

import State.MovingState;
import Factory.MovingObjectsFactory;
import Iterator.GameObjectsIterator;
import Iterator.Iterator;
import Model.BarObject;
import Model.BombObject;
import Singleton.Clown;
import Model.GameObjectAbstract;
import Model.ImageObject;
import eg.edu.alexu.csd.oop.game.*;
import static java.awt.Color.black;
import java.util.ArrayList;
import java.util.List;

//lesa 3ayzeen nezabatha MVC
//momken nekhally el explosion ye flicker be second spriteImage
//momken na3mel factory for control and constant
public class CircusOfPlates implements World {

    private static int MAX_TIME = 1 * 60 * 1000;
    private int score = 0;
    private final long startTime = System.currentTimeMillis();
    private final int width = 800;
    private final int height = 600;
    private final ArrayList<GameObject> constant = new ArrayList<>();
    private final ArrayList<GameObject> moving = new ArrayList<>();
    private final ArrayList<GameObject> control = new ArrayList<>();
    private final MovingObjectsFactory movingObjectsFactory;
    private final int numOfPlates;
    private final int numOfSquares;
    private final int numOfBombs;
    private final int numOfNukes;
    private final int speed;
    private final ArrayList<GameObject> leftStack = new ArrayList<>();
    private final ArrayList<GameObject> rightStack = new ArrayList<>();
    private int numOfCaughtObjects;
    private boolean timeout;
    private int explosionTime = 0;
    private boolean bombTriggered = false;
    private boolean nukeCaught = false;

    public CircusOfPlates(int speed, int numOfPlates, int numOfSquares, int numOfBombs, int numOfNukes) {
        this.speed = speed;
        this.numOfPlates = numOfPlates;
        this.numOfSquares = numOfSquares;
        this.numOfBombs = numOfBombs;
        this.numOfNukes = numOfNukes;
        movingObjectsFactory = new MovingObjectsFactory();
        constant.add(new ImageObject(0, 0, "/background.png"));
        addControlObjects();
        addMovingObjects();

    }

    private void addControlObjects() {
        Clown clown = Clown.getInstance();//singleton pattern        
        control.add(clown.clownObject);
        control.add(new BarObject(clown.clownObject.getX() - 10, clown.clownObject.getY() - 5, 40, true, black));
        control.add(new BarObject(clown.clownObject.getX() + 120, clown.clownObject.getY() - 5, 40, true, black));
    }

    private void addMovingObjects() {
        for (int i = 0; i < numOfBombs; i++) {
            moving.add(movingObjectsFactory.getBomb(width, height));
        }
        for (int i = 0; i < numOfPlates; i++) {
            moving.add(movingObjectsFactory.getRandomPlate(width, height));
        }
        for (int i = 0; i < numOfSquares; i++) {
            moving.add(movingObjectsFactory.getRandomSquare(width, height));
        }
        for (int i = 0; i < numOfNukes; i++) {
            moving.add(movingObjectsFactory.getNuke(width, height));
        }
    }

    private boolean intersect(GameObject o1, GameObject o2) {
        if (o1 == null || o2 == null) {
            return false;
        }

        return (Math.abs((o1.getX() + o1.getWidth() / 2) - (o2.getX() + o2.getWidth() / 2)) <= o1.getWidth()) && (Math.abs((o1.getY() + o1.getHeight() / 2) - (o2.getY() + o2.getHeight() / 2)) <= o1.getHeight());
    }

    private Iterator createGameObjectsIterator(ArrayList<GameObject> objects) {
        return new GameObjectsIterator(objects);
    }

    @Override
    public List<GameObject> getConstantObjects() {
        return constant;
    }

    @Override
    public List<GameObject> getMovableObjects() {
        return moving;
    }

    @Override
    public List<GameObject> getControlableObjects() {
        return control;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    Iterator movingObjectsIterator;
    GameObject movingObject;
    MovingState state;
    //we can also do the iterator pattern for stacks(le ay loop ya3ny)

    @Override
    public boolean refresh() {
        if (!nukeCaught) {
            timeout = System.currentTimeMillis() - startTime > MAX_TIME;
            movingObjectsIterator = createGameObjectsIterator(moving);
            removeExplosion();
            moveClownSticksWithClown();
            moveStackWithClown();
            catchThreePlates(leftStack);
            catchThreePlates(rightStack);
            while (movingObjectsIterator.hasNext()) {
                movingObject = movingObjectsIterator.next();
                state = ((GameObjectAbstract) movingObject).getCurrentState();
                if (leftStack.contains(movingObject) || rightStack.contains(movingObject)) {
                    continue;
                }
                if (intersect(movingObject, control.get(1)) && leftStack.isEmpty()) { //if movingObject is caught on left bar
                    if (ifBombOrNuke(movingObject, leftStack)) {
                        break;
                    }
                    state.placeOnTop(control.get(1), control.get(1).getX() - 8);
                    leftStack.add(movingObject);
                    numOfCaughtObjects++;

                } else if (intersect(movingObject, control.get(2)) && rightStack.isEmpty()) {  //if movingObject is caught on right bar
                    if (ifBombOrNuke(movingObject, rightStack)) {
                        break;
                    }
                    state.placeOnTop(control.get(1), control.get(2).getX() - 8);
                    rightStack.add(movingObject);
                    numOfCaughtObjects++;

                } else if (intersect(movingObject, getObjectOnTop(leftStack)) && !leftStack.isEmpty()) {
                    if (ifBombOrNuke(movingObject, leftStack)) {
                        break;
                    }
                    state.placeOnTop(getObjectOnTop(leftStack), control.get(1).getX());
                    leftStack.add(movingObject);
                    numOfCaughtObjects++;

                } else if (intersect(movingObject, getObjectOnTop(rightStack)) && !rightStack.isEmpty()) {
                    if (ifBombOrNuke(movingObject, rightStack)) {
                        break;
                    }
                    state.placeOnTop(getObjectOnTop(rightStack), control.get(2).getX());
                    rightStack.add(movingObject);
                    numOfCaughtObjects++;
                } else {
                    if (!timeout) {
                        state.letObjectFall(speed);
                    }
                }
                respawn(movingObject);
            }
            replaceCaughtObjects();
            return !timeout;
        }
        return !nukeCaught;
    }

    public void removeExplosion() {

        if (bombTriggered == true) {
            explosionTime++;
            if (explosionTime == 10) {
                constant.remove(1);
                bombTriggered = false;
                explosionTime = 0;
            }
        }
    }

    public void replaceCaughtObjects() {
        for (int i = 0; i < numOfCaughtObjects; i++) {
            moving.add(movingObjectsFactory.getRandomPlateOrSquare(width, height));
        }
        numOfCaughtObjects = 0;
    }

    public boolean ifBombOrNuke(GameObject movingObject, ArrayList<GameObject> stack) {
        if (movingObject instanceof BombObject bombObject) {
            if (bombObject.getType() == 1) {
                for (GameObject caughtItems : stack) {
                    moving.remove(caughtItems);
                }
                stack.clear();
                moving.remove(bombObject);
                constant.add(new ImageObject(bombObject.getX() - 140, bombObject.getY() - 120, "/explosion.png"));
                bombTriggered = true;
                moving.add(movingObjectsFactory.getBomb(width, height));
                if (score != 0) {
                    score -= 1;
                }
            } else if (bombObject.getType() == 2) { //if nuke
                for (GameObject caughtItems : stack) {
                    moving.remove(caughtItems);
                }
                stack.clear();
                moving.remove(bombObject);
                constant.add(new ImageObject(bombObject.getX() - 120, control.get(1).getY() - 170, "/nuclear.png"));
                nukeCaught=true;
                score = 0;
            }
            return true;
        }
        return false;
    }

    public GameObject getObjectOnTop(ArrayList<GameObject> stack) {
        if (stack.isEmpty()) {
            return null;
        }
        return stack.get(stack.size() - 1);
    }

    public void moveClownSticksWithClown() {

        control.get(1).setX(control.get(0).getX() - 10);
        control.get(2).setX(control.get(0).getX() + 120);
    }

    public void moveStackWithClown() {
        for (GameObject object : leftStack) {
            ((GameObjectAbstract) object).getCurrentState().moveWithControlledObjects(control.get(1));
        }
        for (GameObject object : rightStack) {
            ((GameObjectAbstract) object).getCurrentState().moveWithControlledObjects(control.get(2));
        }
    }

    public synchronized void catchThreePlates(ArrayList<GameObject> stack) {
        int threeCounter = 0;
        for (GameObject object : stack) {
            int objectIndexInStack = stack.indexOf(object);
            if (objectIndexInStack >= 2 && (isSameColor(objectIndexInStack, stack))) {
                moving.remove(stack.get(objectIndexInStack));
                moving.remove(stack.get(objectIndexInStack - 1));
                moving.remove(stack.get(objectIndexInStack - 2));
                score += 1;
                threeCounter = objectIndexInStack;
            }
        }
        try {
            stack.remove(threeCounter - 2);
            stack.remove(threeCounter - 2);
            stack.remove(threeCounter - 2);
        } catch (NullPointerException | IndexOutOfBoundsException e) {

        }
    }

    public boolean isSameColor(int objectIndexInStack, ArrayList<GameObject> stack) {
        int firstObjectType = ((ImageObject) stack.get(objectIndexInStack)).getType();
        int secondObjectType = ((ImageObject) stack.get(objectIndexInStack - 1)).getType();
        int thirdObjectType = ((ImageObject) stack.get(objectIndexInStack - 2)).getType();
        return firstObjectType == secondObjectType && firstObjectType == thirdObjectType;
    }

    public void respawn(GameObject movingObject) {
        if (movingObject.getY() > height) {
            ((GameObjectAbstract) (movingObject)).getCurrentState().resetPosition(width);
        }
    }

    @Override
    public String getStatus() {
        return "Score=" + score + "   |   Time=" + Math.max(0, (MAX_TIME - (System.currentTimeMillis() - startTime)) / 1000);
    }

    @Override
    public int getSpeed() {
        //the more the slower
        return 10;
    }

    @Override
    public int getControlSpeed() {
        return 20;
    }
//        @Override
//    public void setVisualState(boolean b) {
//        this.visualState = b;
//        notifyAllObservers();
//    }
//
//    @Override
//    public boolean getVisualState() {
//        return visualState == true;
//    }
//
//    @Override
//    public void attach(Observer observer) {
//        observers.add(observer);
//    }
//
//    @Override
//    public void notifyAllObservers() {
//        for (int i = 0; i < observers.size(); i++) {
//            observers.get(i).updateGameFrame();
//        }
//    }

}
