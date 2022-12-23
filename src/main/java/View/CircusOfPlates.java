/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package View;

import Model.BarObject;
import Model.ImageObject;
import eg.edu.alexu.csd.oop.game.*;
import static java.awt.Color.black;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author PC
 */
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

        constant.add(new BarObject(0, this.height / 5, 200, true, black));
        constant.add(new BarObject(this.width - 200, this.height / 5, 200, true, black));
        constant.add(new BarObject(0, this.height / 3, 100, true, black));
        constant.add(new BarObject(this.width - 100, this.height / 3, 100, true, black));

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
        for (int i = 0; i < 10; i++) {
            moving.add(new ImageObject(this.constant.get(0).getX(), this.constant.get(0).getY(), "/" + platesColors[(int) Math.floor(Math.random() * 3)] + ".png", 1));
            moving.add(new ImageObject(this.constant.get(1).getX() + this.constant.get(1).getWidth() -this.plateWidth, this.constant.get(1).getY(), "/" + platesColors[(int) Math.floor(Math.random() * 3)] + ".png", 1));
            moving.add(new ImageObject(this.constant.get(2).getX(), this.constant.get(2).getY(), "/" + platesColors[(int) Math.floor(Math.random() * 3)] + ".png", 1));
            moving.add(new ImageObject(this.constant.get(3).getX() + this.constant.get(3).getWidth() - this.plateWidth, this.constant.get(3).getY(), "/" + platesColors[(int) Math.floor(Math.random() * 3)] + ".png", 1));

        }

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
        for (GameObject m : moving) {
            //if(m.getY()) //ne3mel method intersect
            m.setY((m.getY() + 1));

//            if(!timeout & intersect(clown,m))
//                score = Math.max(0, score-10);
        }
//        for(GameObject c: constant){
//            
//        }
        return !timeout;
    }

    @Override
    public String getStatus() {
        return "Score=" + score + "   |   Time=" + Math.max(0, (MAX_TIME - (System.currentTimeMillis() - startTime)) / 1000);
    }

    @Override
    public int getSpeed() {
        return 1;
    }

    @Override
    public int getControlSpeed() {
        return 20;
    }

}
