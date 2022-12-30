package View;

import Model.BarObject;
import Model.BombObject;
import Model.Clown;
import Model.ImageObject;
import eg.edu.alexu.csd.oop.game.*;
import static java.awt.Color.black;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//lesa 3ayzeen nezabatha MVC
//momken nekhally el explosion ye flicker be second spriteImage
public class CircusOfPlates implements World {

    private static int MAX_TIME = 1 * 60 * 1000;
    private int score = 0;
    private final long startTime = System.currentTimeMillis();
    private final int width;
    private final int height;
    private final List<GameObject> constant = new LinkedList<>();
    private final List<GameObject> moving = new LinkedList<>();
    private final List<GameObject> control = new LinkedList<>();
    MovingObjectsFactory movingObjectsFactory;
    private final int NUMBER_OF_PLATES = 5;
    private final int NUMBER_OF_SQUARES = 5;
    private final int NUMBER_OF_BOMBS = 2;
    private final int NUMBER_OF_NUKES = 1;

    public CircusOfPlates(int screenWidth, int screenHeight) {

        width = screenWidth;
        height = screenHeight;
        movingObjectsFactory = new MovingObjectsFactory();
        constant.add(new ImageObject(0, 0, "/background.png"));
        addControlObjects();
        addMovingObjects();
    }

    private void addControlObjects() {
        Clown clown = Clown.getInstance();//singleton pattern        
        clown.createClown("/clown1.png");
        control.add(clown.clownObject);
        control.add(new BarObject(clown.clownObject.getX() - 10, clown.clownObject.getY() - 5, 40, true, black));
        control.add(new BarObject(clown.clownObject.getX() + 120, clown.clownObject.getY() - 5, 40, true, black));
    }

    private void addMovingObjects() {
        for (int i = 0; i < NUMBER_OF_BOMBS; i++) {
            moving.add(movingObjectsFactory.getBomb(width, height));
        }
        for (int i = 0; i < NUMBER_OF_PLATES; i++) {
            moving.add(movingObjectsFactory.getRandomPlate(width, height));
        }
        for (int i = 0; i < NUMBER_OF_SQUARES; i++) {
            moving.add(movingObjectsFactory.getRandomSquare(width, height));
        }
        for (int i = 0; i < NUMBER_OF_NUKES; i++) {
            moving.add(movingObjectsFactory.getNuke(width, height));
        }
    }

    private boolean intersect(GameObject o1, GameObject o2) {
        if (o1 == null || o2 == null) {
            return false;
        }

        return (Math.abs((o1.getX() + o1.getWidth() / 2) - (o2.getX() + o2.getWidth() / 2)) <= o1.getWidth()) && (Math.abs((o1.getY() + o1.getHeight() / 2) - (o2.getY() + o2.getHeight() / 2)) <= o1.getHeight());
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

    private ArrayList<GameObject> leftStack = new ArrayList<>();
    private ArrayList<GameObject> rightStack = new ArrayList<>();
    private int numOfCaughtObjects;
    private boolean timeout;

    @Override
    public boolean refresh() {
        timeout = System.currentTimeMillis() - startTime > MAX_TIME;
        removeExplosion();
        moveClownSticksWithClown();
        moveStackWithClown();
        catchThreePlates(leftStack);
        catchThreePlates(rightStack);
        for (GameObject movingObject : moving) {
            if (leftStack.contains(movingObject) || rightStack.contains(movingObject)) {
                continue;
            }
            if (intersect(movingObject, control.get(1)) && leftStack.isEmpty()) { //if movingObject is caught on left bar
                if (ifBombOrNuke(movingObject, leftStack)) {
                    break;
                }
                movingObject.setX(control.get(1).getX() - 8);
                placeObjectOnTop(movingObject, control.get(1));
                leftStack.add(movingObject);
                numOfCaughtObjects++;

            } else if (intersect(movingObject, control.get(2)) && rightStack.isEmpty()) {  //if movingObject is caught on right bar
                if (ifBombOrNuke(movingObject, rightStack)) {
                    break;
                }
                movingObject.setX(control.get(2).getX() - 8);
                placeObjectOnTop(movingObject, control.get(1));
                rightStack.add(movingObject);
                numOfCaughtObjects++;

            } else if (intersect(movingObject, getObjectOnTop(leftStack)) && !leftStack.isEmpty()) {
                if (ifBombOrNuke(movingObject, leftStack)) {
                    break;
                }
                movingObject.setX(control.get(1).getX());
                placeObjectOnTop(movingObject, getObjectOnTop(leftStack));
                leftStack.add(movingObject);
                numOfCaughtObjects++;

            } else if (intersect(movingObject, getObjectOnTop(rightStack)) && !rightStack.isEmpty()) {
                if (ifBombOrNuke(movingObject, rightStack)) {
                    break;
                }
                movingObject.setX(control.get(2).getX());
                placeObjectOnTop(movingObject, getObjectOnTop(rightStack));
                rightStack.add(movingObject);
                numOfCaughtObjects++;
            } else {
                if (!timeout) {
                    movingObject.setY((movingObject.getY() + 3));
                }
            }
            respawn(movingObject);
        }
        replaceCaughtObjects();
        return !timeout;
    }
    int x = 0;
    boolean bombTriggered = false;

    public void removeExplosion() {

        if (bombTriggered == true) {
            x++;
            if (x == 10) {
                constant.remove(1);
                bombTriggered = false;
                x = 0;
            }
        }
    }

    public void placeObjectOnTop(GameObject movingObject, GameObject top) {
        if (isPlate(movingObject)) {
            movingObject.setY(top.getY() - 5);
        } else {
            movingObject.setY(top.getY() - 18);
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
            } else if (bombObject.getType() == 2) {
                for (GameObject caughtItems : stack) {
                    moving.remove(caughtItems);
                }
                stack.clear();
                moving.remove(bombObject);
                constant.add(new ImageObject(bombObject.getX() - 120, bombObject.getY() - 85, "/nuclear.png"));
                MAX_TIME = 0;
                score = 0;
            }
            return true;
        }
        return false;
    }

    public boolean isNuke(GameObject movingObject) {
        if (movingObject instanceof BombObject bombObject) {
            if (bombObject.getType() == 2) {
                return true;
            }
        }
        return false;
    }

    public boolean isPlate(GameObject movingObject) {
        return movingObject.getHeight() == 7;
    }

    public boolean isSquare(GameObject movingObject) {
        return movingObject.getHeight() == 20;
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
        for (GameObject movingObject : leftStack) {
            movingObject.setX(control.get(1).getX() - 8);
        }
        for (GameObject plate : rightStack) {
            plate.setX(control.get(2).getX() - 8);
        }
    }

    public void catchThreePlates(ArrayList<GameObject> stack) {
        int threeCounter = 0;
        for (GameObject plate : stack) {
            int objectIndexInStack = stack.indexOf(plate);
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
            movingObject.setY(0);
            movingObject.setX((int) (Math.random() * width));
        }
    }

    @Override
    public String getStatus() {
        return "Score=" + score + "   |   Time=" + Math.max(0, (MAX_TIME - (System.currentTimeMillis() - startTime)) / 1000);
    }

    @Override
    public int getSpeed() {
        return 30;
    }

    @Override
    public int getControlSpeed() {
        return 20;
    }

}
