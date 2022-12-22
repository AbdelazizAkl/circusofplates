/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package View;

import Model.ImageObject;
import eg.edu.alexu.csd.oop.game.*;
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
    private final List<GameObject> constant = new LinkedList<>();
    private final List<GameObject> moving = new LinkedList<>();
    private final List<GameObject> control = new LinkedList<>();

    public CircusOfPlates(int screenWidth, int screenHeight) {
        width = screenWidth;
        height = screenHeight;
        control.add(new ImageObject(300, 435, "/clown1.png"));
        //control.add(new ImageObject(550,420,"/clown2.png"));
        moving.add(new ImageObject((int) (Math.random() * screenWidth), 50, "/greenplate.png",1));
        moving.add(new ImageObject((int) (Math.random() * screenWidth), 50, "/blueplate.png",1));
        moving.add(new ImageObject((int) (Math.random() * screenWidth), 50, "/redplate.png",1));

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
        GameObject clown = control.get(0);
        for(GameObject m:moving){
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
