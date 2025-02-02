import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

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

    public ArrayList<Block> alienArray;
    public int alienWidth = Constants.TILE_SIZE * 2;
    public int alienHeight = Constants.TILE_SIZE;
    public int alienX = Constants.TILE_SIZE;
    public int alienY = Constants.TILE_SIZE;

    public int alienRows = 2;
    public int alienColumns = 3;
    public int alienCount = 0;
    public int alienVelocityX = 1;

    SpaceInvaders() {
        setPreferredSize(new Dimension(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        shipImg = texture.player;
        alienImgArray = texture.enemies;

        ship = new Block(shipX, shipY, shipWidth, shipHeight, shipImg);
        alienArray = new ArrayList<Block>();

        gameLoop = new Timer(1000 / 60, this);
        createAliens();
        gameLoop.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.drawImage(ship.img, ship.x, ship.y, ship.width, ship.height, null);
        for (int i = 0; i < alienArray.size(); i++) {
            Block alien = alienArray.get(i);
            if (alien.alive) {
                g.drawImage(alien.img, alien.x, alien.y, alien.width, alien.height, null);
            }
        }
    }

    public void move() {
        for (int i = 0; i < alienArray.size(); i++) {
            Block alien = alienArray.get(i);
            if (alien.alive) {
                alien.x += alienVelocityX;

                if (alien.x + alien.width >= Constants.BOARD_WIDTH || alien.x <= 0) {
                    alienVelocityX *= -1;
                    alien.x += alienVelocityX * 2;
                    for (int j = 0; j < alienArray.size(); j++) {
                        alienArray.get(j).y += alienHeight;
                    }
                }
            }
        }
    }

    public void createAliens() {
        Random random = new Random();
        for (int r = 0; r < alienRows; r++) {
            for (int c = 0; c < alienColumns; c++) {
                int randomImageIndex = random.nextInt(alienImgArray.size());
                Block alien = new Block(
                        alienX + c * alienWidth,
                        alienY + r * alienHeight,
                        alienWidth,
                        alienHeight,
                        alienImgArray.get(randomImageIndex)
                );
                alienArray.add(alien);
            }
        }
        alienCount = alienArray.size();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
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
