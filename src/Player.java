import java.awt.*;

public class Player extends Rectangle {
    public boolean right, left;
    private int speed = 4;
    private int time = 0, targetTime = 18;
    public int imageIndex = 0;
    public int lastDir = 1;

    public Player(int x, int y) {
        setBounds(x, y, 11, 7);
    }

    public void ticK(){

    }
}
