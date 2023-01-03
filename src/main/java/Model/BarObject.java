package Model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;


public class BarObject extends GameObjectAbstract {

    private final int SPRITE_HEIGHT;
    private final int width;
    private int height;
    private final boolean horizontalOnly;

    public BarObject(int posX, int posY, int width, boolean horizontalOnly, Color color) {
        this.x = posX;
        this.y = posY;
        this.width = width;
        this.horizontalOnly = horizontalOnly;
        this.visible = true;
        this.height = 5;
        this.SPRITE_HEIGHT = 5;
        this.MAX_MSTATE = 1;
        spriteImages = new BufferedImage[MAX_MSTATE];
        spriteImages[0] = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = spriteImages[0].createGraphics();
        g2.setColor(color);
        g2.setBackground(color);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(20));
        g2.drawLine(0, 0, width, 0);
        g2.dispose();
    }

    @Override
    public void setY(int mY) {
        if (horizontalOnly) {
            return;
        }
        this.y = mY;
    }
    
    @Override
    public int getWidth() {
        return width;
    }

    public int getBarHeight() {
        return SPRITE_HEIGHT;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
