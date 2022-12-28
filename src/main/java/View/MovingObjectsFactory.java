package View;

import Model.ImageObject;
import eg.edu.alexu.csd.oop.game.GameObject;

public class MovingObjectsFactory {

    private final String[] plateColorPath = {"/greenPlate.png", "/redPlate.png", "/bluePlate.png"};
    private final String[] squareColorPath = {"/greenSquare.png", "/redSquare.png", "/blueSquare.png"};

    public GameObject getRandomPlate(int screenWidth, int screenHeight) {

        int x = (int) Math.floor(Math.random() * plateColorPath.length);
        return new ImageObject((int) (Math.random() * screenWidth), (int) (Math.random() * screenHeight * -2), plateColorPath[x], x + 1);
    }

    public GameObject getRandomSquare(int screenWidth, int screenHeight) {
        int x = (int) Math.floor(Math.random() * squareColorPath.length);
        return new ImageObject((int) (Math.random() * screenWidth), (int) (Math.random() * screenHeight * -2), squareColorPath[x], x + 1);
    }

    public GameObject getRandomPlateOrSquare(int screenWidth, int screenHeight) {
        int x = (int) Math.ceil(Math.random() * 2);
        if (x == 1) {
            return getRandomPlate(screenWidth, screenHeight);
        } else if (x == 2) {
            return getRandomSquare(screenWidth, screenHeight);
        }
        return null;
    }
}
