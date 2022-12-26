package View;

import Model.BarObject;
import Model.Clown;
import Model.ImageObject;
import eg.edu.alexu.csd.oop.game.*;
import static java.awt.Color.black;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//lesa 3ayzeen nezabatha MVC
public class CircusOfPlates implements World {

    private static int MAX_TIME = 1 * 60 * 1000;
    private int score = 0;
    private long startTime = System.currentTimeMillis();
    private final int width;
    private final int height;
    private final int plateWidth = 54;
    private final List<GameObject> constant = new LinkedList<>();
    private final List<GameObject> moving = new LinkedList<>();
    private final List<GameObject> control = new LinkedList<>();
    private ArrayList<Point> shelfLocation = new ArrayList<>();
    MovingObjectsFactory movingObjectsFactory;
    private final int NUMBER_OF_PLATES = 2;

    public CircusOfPlates(int screenWidth, int screenHeight) {

        width = screenWidth;
        height = screenHeight;
        movingObjectsFactory = new MovingObjectsFactory(shelfLocation);
        constant.add(new ImageObject(0, 0, "/background.png"));

        Clown clown = Clown.getInstance();//singleton pattern        
        clown.createClown("/clown1.png");
        control.add(clown.clownObject);
        control.add(new BarObject(clown.clownObject.getX() - 10, clown.clownObject.getY() - 5, 40, true, black));
        control.add(new BarObject(clown.clownObject.getX() + 120, clown.clownObject.getY() - 5, 40, true, black));

        for (int i = 0; i < NUMBER_OF_PLATES; i++) {
            moving.add(movingObjectsFactory.getRandomMovingObjectInstance(screenWidth, screenHeight));
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

    @Override
    public boolean refresh() {
        boolean timeout = System.currentTimeMillis() - startTime > MAX_TIME;
        moveClownSticksWithClown();

        for (GameObject plate : moving) {
            if (leftStack.contains(plate) || rightStack.contains(plate)) {
                continue;
            }
            if (intersect(plate, control.get(1)) && leftStack.isEmpty()) { //if plate is caught on left bar
                plate.setX(control.get(1).getX()-8);
                plate.setY(control.get(1).getY() - 5);
                leftStack.add(plate);
                numOfCaughtObjects++;
            } else if (intersect(plate, control.get(2)) && rightStack.isEmpty()) {  //if plate is caught on right bar
                plate.setX(control.get(2).getX()-8);
                plate.setY(control.get(2).getY() - 5);
                rightStack.add(plate);
                numOfCaughtObjects++;
            } else if (intersect(plate, getPlateOnTop(leftStack))) {
                plate.setX(control.get(1).getX());
                plate.setY(getPlateOnTop(leftStack).getY() - 5);
                leftStack.add(plate);
                numOfCaughtObjects++;
            } else if (intersect(plate, getPlateOnTop(rightStack))) {
                plate.setX(control.get(2).getX());
                plate.setY(getPlateOnTop(rightStack).getY() - 5);
                rightStack.add(plate);
                numOfCaughtObjects++;
            } else {
                if (!timeout) {
                    plate.setY((plate.getY() + 1));
                }
            }
            respawn(plate);
        }
        replaceCaughtObjects();
        return !timeout;
    }

    public void replaceCaughtObjects() {
        for (int i = 0; i < numOfCaughtObjects; i++) {
            moving.add(movingObjectsFactory.getRandomMovingObjectInstance(width, height));
        }
        numOfCaughtObjects = 0;
    }

    public GameObject getPlateOnTop(ArrayList<GameObject> stack) {
        if (stack.isEmpty()) {
            return null;
        }
        return stack.get(stack.size() - 1);
    }

    public void moveClownSticksWithClown() {
        int threeCounterLeft = 0;
        int threeCounterRight = 0;
        control.get(1).setX(control.get(0).getX() - 10);
        control.get(2).setX(control.get(0).getX() + 120);
        for (GameObject plate : leftStack) {
            plate.setX(control.get(1).getX()-8);
            int x = leftStack.indexOf(plate);
            if (x < 2) {

            } else if (((ImageObject) leftStack.get(x - 1)).getType() == ((ImageObject) leftStack.get(x)).getType() && ((ImageObject) leftStack.get(x)).getType() == ((ImageObject) leftStack.get(x - 2)).getType()) {
                moving.remove(leftStack.get(x));
                moving.remove(leftStack.get(x - 1));
                moving.remove(leftStack.get(x - 2));
                threeCounterLeft=x;
                score+=10;
            }
        }
        try {
            leftStack.remove(threeCounterLeft - 2);
            leftStack.remove(threeCounterLeft - 2);
            leftStack.remove(threeCounterLeft - 2);
        } catch (NullPointerException | IndexOutOfBoundsException e) {

        }
        for (GameObject plate : rightStack) {
            plate.setX(control.get(2).getX()-8);
            int x = rightStack.indexOf(plate);
            if (x < 2) {

            } else if (((ImageObject) rightStack.get(x - 1)).getType() == ((ImageObject) rightStack.get(x)).getType() && ((ImageObject) rightStack.get(x)).getType() == ((ImageObject) rightStack.get(x - 2)).getType()) {
                moving.remove(rightStack.get(x));
                moving.remove(rightStack.get(x - 1));
                moving.remove(rightStack.get(x - 2));
                score+=10;
                threeCounterRight=x;
            }
        }
        try {
            rightStack.remove(threeCounterRight - 2);
            rightStack.remove(threeCounterRight - 2);
            rightStack.remove(threeCounterRight - 2);
        } catch (NullPointerException | IndexOutOfBoundsException e) {

        }
    }

    public void respawn(GameObject plate) {
        if (plate.getY() > height) {
            plate.setY(0);
            plate.setX((int) (Math.random() * width));
        }
    }

    @Override
    public String getStatus() {
        return "Score=" + score + "   |   Time=" + Math.max(0, (MAX_TIME - (System.currentTimeMillis() - startTime)) / 1000);
    }

    @Override
    public int getSpeed() {
        return 10;
    }

    @Override
    public int getControlSpeed() {
        return 30;
    }

}
