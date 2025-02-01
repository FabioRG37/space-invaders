import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class SpaceInvaders extends JPanel implements ActionListener, KeyListener {
    public Texture texture = new Texture();
    public Block ship;
    public Timer gameLoop;

    public Image shipImg;
    public ArrayList<Image> alienImgArray;

    public int shipWidth = Constants.TILE_SIZE * 2;
    public int shipHeight = Constants.TILE_SIZE;
    public int shipX = Constants.TILE_SIZE * Constants.COLUMNS / 2 - Constants.TILE_SIZE;
    public int shipY = Constants.BOARD_HEIGHT - Constants.TILE_SIZE * 2;
    public int shipVelocityX = Constants.TILE_SIZE;

    SpaceInvaders() {
        setPreferredSize(new Dimension(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        shipImg = texture.player;
        alienImgArray = texture.enemies;

        ship = new Block(shipX, shipY, shipWidth, shipHeight, shipImg);

        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.drawImage(ship.img, ship.x, ship.y, ship.width, ship.height, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT && ship.x - shipVelocityX >= 0) ship.x -= shipVelocityX;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && ship.x + ship.width + shipVelocityX <= Constants.BOARD_WIDTH) {
            ship.x += shipVelocityX;
        }
    }
}
