package Singleton;

import Model.ImageObject;
import eg.edu.alexu.csd.oop.game.GameObject;

public class Clown {

    private static Clown instance = null;
    public GameObject clownObject;

    private Clown() {
        clownObject = new ImageObject(800 / 3, 435, "/clown1.png");
    }

    public static Clown getInstance() {
        if (instance == null) {
            instance = new Clown();  
        }
        return instance;
    }

}
