import java.awt.*;

public class Block {
    public int x;
    public int y;
    public int width;
    public int height;
    public Image img;
    public boolean alive = true;
    public boolean used = false;

    public Block(int x, int y, int width, int height, Image img) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.img = img;
    }
}
