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
    private Sound sounds = new Sound();

    public Image shipImg;
    public ArrayList<Image> alienImgArray;

    public int shipWidth = Constants.TILE_SIZE * 2;
    public int shipHeight = Constants.TILE_SIZE;
    public int shipX = Constants.TILE_SIZE * Constants.COLUMNS / 2 - Constants.TILE_SIZE;
    public int shipY = Constants.BOARD_HEIGHT - Constants.TILE_SIZE * 2;
    public int shipVelocityX = Constants.TILE_SIZE / 6;
    private boolean moveLeft = false;
    private boolean moveRight = false;

    public ArrayList<Block> alienArray;
    public int alienWidth = Constants.TILE_SIZE * 2;
    public int alienHeight = Constants.TILE_SIZE;
    public int alienX = Constants.TILE_SIZE;
    public int alienY = Constants.TILE_SIZE;

    public int alienRows = 2;
    public int alienColumns = 3;
    public int alienCount = 0;
    public int alienVelocityX = 1;

    ArrayList<Block> bulletArray;
    public int bulletWidth = Constants.TILE_SIZE / 8;
    public int bulletHeight = Constants.TILE_SIZE / 2;
    public int bulletVelocityY = -10;
    private long lastFireTime = 0;
    private boolean spacePressed = false;

    SpaceInvaders() {
        setPreferredSize(new Dimension(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        shipImg = texture.player;
        alienImgArray = texture.enemies;

        ship = new Block(shipX, shipY, shipWidth, shipHeight, shipImg);
        alienArray = new ArrayList<Block>();
        bulletArray = new ArrayList<Block>();

        Constants.GAME_LOOP = new Timer(1000 / 60, this);
        createAliens();
        Constants.GAME_LOOP.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        //ship
        g.drawImage(ship.img, ship.x, ship.y, ship.width, ship.height, null);

        //aliens
        for (int i = 0; i < alienArray.size(); i++) {
            Block alien = alienArray.get(i);
            if (alien.alive) {
                g.drawImage(alien.img, alien.x, alien.y, alien.width, alien.height, null);
            }
        }

        //bullet
        g.setColor(Color.WHITE);
        for (int i = 0; i < bulletArray.size(); i++) {
            Block bullet = bulletArray.get(i);
            if (!bullet.used) {
//                g.drawRect(bullet.x, bullet.y, bullet.width, bullet.height);
                g.fillRect(bullet.x, bullet.y, bullet.width, bullet.height);
            }
        }

        //score
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if (Constants.GAME_OVER) {
            g.drawString("Game Over: " + String.valueOf(Constants.SCORE), 110, 35);
        } else {
            g.drawString(String.valueOf(Constants.SCORE), 10, 35);
        }
    }

    public void move() {
        //aliens
        for (int i = 0; i < alienArray.size(); i++) {
            Block alien = alienArray.get(i);
            if (alien.alive) {
                alien.x += alienVelocityX;

                //if alien touches the border
                if (alien.x + alien.width >= Constants.BOARD_WIDTH || alien.x <= 0) {
                    alienVelocityX *= -1;
                    alien.x += alienVelocityX * 2;

                    //move all aliens down by one row
                    for (int j = 0; j < alienArray.size(); j++) {
                        alienArray.get(j).y += alienHeight;
                    }
                }

                if (alien.y + alien.height > ship.y) {
                    Constants.GAME_OVER = true;
                    sounds.hurt();
                }
            }
        }

        //bullets
        for (int i = 0; i < bulletArray.size(); i++) {
            Block bullet = bulletArray.get(i);
            bullet.y += bulletVelocityY;

            //bullet collision with aliens
            for (int j = 0; j < alienArray.size(); j++) {
                Block alien = alienArray.get(j);
                if (!bullet.used && alien.alive && detectCollision(bullet, alien)) {
                    sounds.explosion();
                    bullet.used = true;
                    alien.alive = false;
                    alienCount--;
                    Constants.SCORE += 100;
                }
            }
        }

        //clear bullets
        while (bulletArray.size() > 0 && (bulletArray.get(0).used || bulletArray.get(0).y < 0)) {
            bulletArray.remove(0); //removes the fist element of the array
        }

        //next level
        if (alienCount == 0) {
            //increase the number of aliens in columns and rows by 1
            Constants.SCORE += alienColumns * alienRows * 100; //bonus points for cleaning the level
            alienColumns = Math.min(alienColumns + 1, Constants.COLUMNS / 2 - 2); //cap column at 16/2 -2 = 6
            alienRows = Math.min(alienRows + 1, Constants.ROWS - 6); //cp row at 16 -6 = 10
            alienArray.clear();
            bulletArray.clear();
            alienVelocityX = 1;
            createAliens();
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

    public boolean detectCollision(Block a, Block b) {
        return a.x < b.x + b.width &&  //a's top left corner doens't reach b's top right corner
                a.x + a.width > b.x &&  //a's top right corner passes b's top left corner
                a.y < b.y + b.height && //a's top left corner doesn't reach b's bottom left corner
                a.y + a.height > b.y;   //a's bottom left corner passes b's top left corner
    }

    private void fireBullet() {
        long currentTime = System.currentTimeMillis();
        if (spacePressed && currentTime - lastFireTime >= Constants.FIRE_INTERVAL) {
            lastFireTime = currentTime;
            Block bullet = new Block(ship.x + shipWidth * 16 / 32, ship.y, bulletWidth, bulletHeight, null);
            bulletArray.add(bullet);
            sounds.shoot();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        fireBullet();
        if (moveLeft && ship.x - shipVelocityX >= 0) {
            ship.x -= shipVelocityX;
        }
        if (moveRight && ship.x + shipWidth + shipVelocityX <= Constants.BOARD_WIDTH) {
            ship.x += shipVelocityX;
        }
        repaint();
        if (Constants.GAME_OVER) {
            Constants.GAME_LOOP.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            moveLeft = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            moveRight = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (Constants.GAME_OVER) {
            ship.x = shipX;
            alienArray.clear();
            bulletArray.clear();
            Constants.SCORE = 0;
            alienVelocityX = 1;
            alienColumns = 3;
            alienRows = 2;
            Constants.GAME_OVER = false;
            createAliens();
            Constants.GAME_LOOP.start();
        }
//        if (e.getKeyCode() == KeyEvent.VK_LEFT && ship.x - shipVelocityX >= 0) ship.x -= shipVelocityX;
//        if (e.getKeyCode() == KeyEvent.VK_RIGHT && ship.x + ship.width + shipVelocityX <= Constants.BOARD_WIDTH) {
//            ship.x += shipVelocityX;
//        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            moveLeft = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            moveRight = false;
        }
//        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
//            Block bullet = new Block(ship.x + shipWidth * 16 / 32, ship.y, bulletWidth, bulletHeight, null);
//            bulletArray.add(bullet);
//            sounds.shoot();
//        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            spacePressed = false;
            lastFireTime = 0;
        }
    }
}
