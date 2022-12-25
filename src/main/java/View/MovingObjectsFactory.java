package View;

import Model.ImageObject;
import eg.edu.alexu.csd.oop.game.GameObject;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

public class MovingObjectsFactory {

    private ArrayList<Point> shelfLocation;
    private final String[] paths = {"/greenPlate.png", "/redPlate.png", "/bluePlate.png"};

    public MovingObjectsFactory(ArrayList<Point> shelfLocation) {
        this.shelfLocation = shelfLocation;
    }

    public GameObject getRandomMovingObjectInstance(int screenWidth) {
        Collections.shuffle(shelfLocation);
        return new ImageObject((int) (Math.random()*screenWidth), 0, paths[(int) Math.floor(Math.random() * paths.length)], 1);
    }
}
