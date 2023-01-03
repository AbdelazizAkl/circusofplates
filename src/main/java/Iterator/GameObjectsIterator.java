package Iterator;

import eg.edu.alexu.csd.oop.game.GameObject;
import java.util.ArrayList;

public class GameObjectsIterator implements Iterator {

    private ArrayList<GameObject> objects = new ArrayList<>();
    private int i = 0;

    public GameObjectsIterator(ArrayList<GameObject> objects) {
        this.objects = objects;
    }

    @Override
    public boolean hasNext() {
        try {
            return objects.get(i) != null;
        } catch (IndexOutOfBoundsException ex) {
        }
        return false;
    }

    @Override
    public GameObject next() {
        GameObject object = objects.get(i);
        i++;
        return object;
    }

}
