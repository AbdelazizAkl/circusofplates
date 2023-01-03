package Singleton;

import Model.GameObjectAbstract;
import Model.ImageObject;
import eg.edu.alexu.csd.oop.game.GameObject;

public class Clown {

    private static Clown instance = null;
    public GameObjectAbstract clownObject;

    private Clown() {
    }

    public static Clown getInstance() {
        if (instance == null) {
            instance = new Clown();  
        }
        return instance;
    }
    
    public void createClown(String path){
        clownObject = new ImageObject(800 / 3, 435, path);
    }
}
