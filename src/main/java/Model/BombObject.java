
package Model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BombObject extends GameObjectAbstract{

    public BombObject(int pX, int pY, String path1,String path2) {
        this(pX, pY, path1,path2, 0);
    }

    public BombObject(int pX, int pY, String path1,String path2, int type) {
        this.x = pX;
        this.y = pY;
        this.type = type;
        this.MAX_MSTATE = 2;
        this.spriteImages = new BufferedImage[MAX_MSTATE]; 
        this.visible = true;
        try {
            spriteImages[0] = ImageIO.read(getClass().getResourceAsStream(path1));
            spriteImages[1] = ImageIO.read(getClass().getResourceAsStream(path2));
        } catch (IOException | IllegalArgumentException e) {
            System.out.println(getClass().getResourceAsStream(path1));
        }
    }
}
