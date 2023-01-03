package Model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageObject extends GameObjectAbstract {


    public ImageObject(int pX, int pY, String path) {
        this(pX, pY, path, 0);
    }

    public ImageObject(int pX, int pY, String path, int type) {
        this.x = pX;
        this.y = pY;
        this.type = type;
        this.visible = true;
        this.MAX_MSTATE = 1;
        this.spriteImages = new BufferedImage[MAX_MSTATE];
        try {
            spriteImages[0] = ImageIO.read(getClass().getResourceAsStream(path));
        } catch (IOException | IllegalArgumentException e) {
            System.out.println(getClass().getResourceAsStream(path));
        }
    }

    @Override
    public int getWidth() {
        return spriteImages[0].getWidth();
    }

    @Override
    public int getHeight() {
        return spriteImages[0].getHeight();
    }

    @Override
    public BufferedImage[] getSpriteImages() {
        return spriteImages;
    }

    @Override
    public void setY(int y) {
        if (type == 0) {
            return;
        }
        this.y = y;
    }
}
