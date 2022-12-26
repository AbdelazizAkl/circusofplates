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
    private final int NUMBER_OF_PLATES = 20;

    public CircusOfPlates(int screenWidth, int screenHeight) {

        width = screenWidth;
        height = screenHeight;

//        constant.add(new BarObject(0, height / 5, 200, true, black));//upperLeft
//        constant.add(new BarObject(width - 200, height / 5, 200, true, black));//upperRight
//        constant.add(new BarObject(0, height / 3, 100, true, black));//lowerLeft
//        constant.add(new BarObject(width - 100, height / 3, 100, true, black));//lowerRight
//
//        shelfLocation.add(new Point(constant.get(0).getX(), constant.get(0).getY()));
//        shelfLocation.add(new Point(constant.get(1).getX() + constant.get(1).getWidth() - plateWidth, constant.get(1).getY()));
//        shelfLocation.add(new Point(constant.get(2).getX(), constant.get(2).getY()));
//        shelfLocation.add(new Point(constant.get(3).getX() + constant.get(3).getWidth() - plateWidth, constant.get(3).getY()));
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

    @Override
    public boolean refresh() {
        boolean timeout = System.currentTimeMillis() - startTime > MAX_TIME;
        moveClownSticksWithClown();

        GameObject plateOnTopLeftStack = getPlateOnTop(leftStack);
        GameObject plateOnTopRightStack = getPlateOnTop(rightStack);

        for (GameObject plate : moving) {
            if (leftStack.contains(plate) || rightStack.contains(plate)) {
                continue;
            }
            if (intersect(plate, control.get(1))&&leftStack.isEmpty()) { //if plate is caught on left bar
                plate.setX(control.get(1).getX());
                plate.setY(control.get(1).getY() - 5);
                leftStack.add(plate);

            } else if (intersect(plate, control.get(2))&&rightStack.isEmpty()) {  //if plate is caught on right bar
                plate.setX(control.get(2).getX());
                try {
                    plate.setY(plateOnTopRightStack.getY() - 5);
                } catch (NullPointerException e) {
                    plate.setY(control.get(2).getY() - 5);
                }
                rightStack.add(plate);
            } else if (intersect(plate, plateOnTopLeftStack)) {
                plate.setX(control.get(1).getX());
                plate.setY(plateOnTopLeftStack.getY() - 5);
                leftStack.add(plate);
//                System.out.println("left stack");
            } else if (intersect(plate, plateOnTopRightStack)) {
                plate.setX(control.get(2).getX());//fi bug lamma bee intersect men el ganb fa hana3mel setY - 1;
                plate.setY(plateOnTopRightStack.getY() - 5);
                rightStack.add(plate);
//                System.out.println("right stack");
            } else {
                if (!timeout) {
                    plate.setY((plate.getY() + 1));
                }
            }
            respawn(plate);
        }
        return !timeout;
    }

    public GameObject getPlateOnTop(ArrayList<GameObject> stack) {
        if (stack.isEmpty()) {
            return null;
        }
        return stack.get(stack.size() - 1);
    }

    public void moveClownSticksWithClown() {
        control.get(1).setX(control.get(0).getX() - 10);
        control.get(2).setX(control.get(0).getX() + 120);
        for (GameObject plate : leftStack) {
            plate.setX(control.get(1).getX());
        }
        for (GameObject plate : rightStack) {
            plate.setX(control.get(2).getX());
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
