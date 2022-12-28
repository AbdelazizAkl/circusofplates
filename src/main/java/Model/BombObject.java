/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import eg.edu.alexu.csd.oop.game.GameObject;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author PC
 */
public class BombObject implements GameObject{
    private static final int MAX_MSTATE = 2;
    private BufferedImage[] spriteImages = new BufferedImage[MAX_MSTATE];
    private int x;
    private int y;
    private boolean visible;
    private int type;

    public BombObject(int pX, int pY, String path1,String path2) {
        this(pX, pY, path1,path2, 0);
    }

    public BombObject(int pX, int pY, String path1,String path2, int type) {
        this.x = pX;
        this.y = pY;
        this.type = type;
        this.visible = true;
        try {
            spriteImages[0] = ImageIO.read(getClass().getResourceAsStream(path1));
            spriteImages[1] = ImageIO.read(getClass().getResourceAsStream(path2));
        } catch (IOException | IllegalArgumentException e) {
            System.out.println(getClass().getResourceAsStream(path1));
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
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
