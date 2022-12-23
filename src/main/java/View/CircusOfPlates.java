package View;

import Model.BarObject;
import Model.ImageObject;
import eg.edu.alexu.csd.oop.game.*;
import static java.awt.Color.black;
import java.util.LinkedList;
import java.util.List;

public class CircusOfPlates implements World {

    private static int MAX_TIME = 1 * 60 * 1000;
    private int score = 0;
    private long startTime = System.currentTimeMillis();
    private final int width;
    private final int height;
    private final int plateWidth = 54;
    GameObject clown;
    private final List<GameObject> constant = new LinkedList<>();
    private final List<GameObject> moving = new LinkedList<>();
    private final List<GameObject> control = new LinkedList<>();

    public CircusOfPlates(int screenWidth, int screenHeight) {

        width = screenWidth;
        height = screenHeight;

        constant.add(new BarObject(0, height / 5, 200, true, black));//upperLeft
        constant.add(new BarObject(width - 200, height / 5, 200, true, black));//upperRight
        constant.add(new BarObject(0, height / 3, 100, true, black));//lowerLeft
        constant.add(new BarObject(width - 100, height / 3, 100, true, black));//lowerRight

        clown = new ImageObject(screenWidth / 3, 435, "/clown1.png");
        control.add(clown);

        //first, moving objects move on shelf then drop
        // add 20 moving objects with random colors
        // factory pattern to create plate with random pattern
        // singleton pattern to create the clown
        // pool pattern to get the plate from the pool of plates
        // array of 30 random plates
        // replace this platesColors with factory
        String[] platesColors = {"greenPlate", "redPlate", "bluePlate"};
        //for (int i = 0; i < 10; i++) {
        moving.add(new ImageObject(constant.get(0).getX(), constant.get(0).getY(), "/" + platesColors[(int) Math.floor(Math.random() * 3)] + ".png", 1));
        moving.add(new ImageObject(constant.get(1).getX() + constant.get(1).getWidth() - plateWidth, constant.get(1).getY(), "/" + platesColors[(int) Math.floor(Math.random() * 3)] + ".png", 1));
        moving.add(new ImageObject(constant.get(2).getX(), constant.get(2).getY(), "/" + platesColors[(int) Math.floor(Math.random() * 3)] + ".png", 1));
        moving.add(new ImageObject(constant.get(3).getX() + constant.get(3).getWidth() - plateWidth, constant.get(3).getY(), "/" + platesColors[(int) Math.floor(Math.random() * 3)] + ".png", 1));
        //kda we created 40 plates bas kolohom fe nafs el 4 7etat fa msh beyetfara2o
        //}
    }

    private boolean intersect(GameObject o1, GameObject o2) {

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

    @Override
    public boolean refresh() {
        boolean timeout = System.currentTimeMillis() - startTime > MAX_TIME;

//        System.out.println("shelf upper left x = " + constant.get(0).getX() + " plate upper left x = " + moving.get(0).getX());
//        System.out.println("shelf upper left y = " + constant.get(0).getY() + " plate upper left y = " + moving.get(0).getY());
//        
//        System.out.println("shelf lower left x = " + constant.get(2).getX() + " plate lower left x = " + moving.get(2).getX());
//        System.out.println("shelf lower left y = " + constant.get(2).getY() + " plate lower left y = " + moving.get(2).getY());
        if (intersect(moving.get(0), clown)) {
            System.out.println("correct");
        }

        for (GameObject plate : moving) {

            if (intersect(plate, this.constant.get(0)) || intersect(plate, this.constant.get(2))) {
                plate.setX(plate.getX() + 1);
            } else if (intersect(plate, this.constant.get(1)) || intersect(plate, this.constant.get(3))) {
                plate.setX(plate.getX() - 1);
            } else if (!intersect(plate, clown)) {
                plate.setY((plate.getY() + 1));
            }
        }
        return !timeout;
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
