import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Texture {
    public BufferedImage spritesheet;
    public Image player;
    public Image blueEnemy;
    public Image yellowEnemy;
    public Image redEnemy;
    public Image greenEnemy;
    public Image whiteEnemy;
    public ArrayList<Image> enemies = new ArrayList<Image>();

    public Texture() {
        InputStream inputStream = Texture.class.getClassLoader().getResourceAsStream("sprites/space-invaders-sprites.png");
        try {
            spritesheet = ImageIO.read(inputStream);
        } catch (IOException e) {
            System.out.println("Erro ao carregar imagem: " + e.getMessage());
        }
        player = new ImageIcon(getPlayerSprite(51, 0)).getImage();

//        player[0] = getPlayerSprite(0, 0);
//        player[1] = getPlayerSprite(12, 0);
//        player[2] = getPlayerSprite(24, 0);
//        player[3] = getPlayerSprite(36, 0);
//        player[4] = getPlayerSprite(48, 0);

        // first emeny
        blueEnemy = new ImageIcon(getEnemieSprite(0, 8)).getImage();
        yellowEnemy = new ImageIcon(getEnemieSprite(17, 8)).getImage();
        redEnemy = new ImageIcon(getEnemieSprite(34, 8)).getImage();
        greenEnemy = new ImageIcon(getEnemieSprite(51, 8)).getImage();
        whiteEnemy = new ImageIcon(getEnemieSprite(68, 8)).getImage();
        enemies.add(blueEnemy);
        enemies.add(yellowEnemy);
        enemies.add(redEnemy);
        enemies.add(greenEnemy);
        enemies.add(whiteEnemy);


        // first emeny
//        enemies[0][0] = getEnemieSprite(0, 8);
//        enemies[0][1] = getEnemieSprite(12, 8);
//        enemies[0][2] = getEnemieSprite(24, 8);
//        enemies[0][3] = getEnemieSprite(36, 8);
//        enemies[0][4] = getEnemieSprite(48, 8);


        // second emeny
//        enemies[1][0] = getEnemieSprite(0, 17);
//        enemies[1][1] = getEnemieSprite(12, 17);
//        enemies[1][2] = getEnemieSprite(24, 17);
//        enemies[1][3] = getEnemieSprite(36, 17);
//        enemies[1][4] = getEnemieSprite(48, 17);


        // third emeny
//        enemies[2][0] = getEnemieSprite(0, 26);
//        enemies[2][1] = getEnemieSprite(12, 26);
//        enemies[2][2] = getEnemieSprite(24, 26);
//        enemies[2][3] = getEnemieSprite(36, 26);
//        enemies[2][4] = getEnemieSprite(48, 26);


        // fourth emeny
//        enemies[3][0] = getEnemieSprite(0, 35);
//        enemies[3][1] = getEnemieSprite(12, 35);
//        enemies[3][2] = getEnemieSprite(24, 35);
//        enemies[3][3] = getEnemieSprite(36, 35);
//        enemies[3][4] = getEnemieSprite(48, 35);


        // fifth emeny
//        enemies[4][0] = getEnemieSprite(0, 44);
//        enemies[4][1] = getEnemieSprite(12, 44);
//        enemies[4][2] = getEnemieSprite(24, 44);
//        enemies[4][3] = getEnemieSprite(36, 44);
//        enemies[4][4] = getEnemieSprite(48, 44);


        // sixth emeny
//        enemies[5][0] = getEnemieSprite(0, 53);
//        enemies[5][1] = getEnemieSprite(12, 53);
//        enemies[5][2] = getEnemieSprite(24, 53);
//        enemies[5][3] = getEnemieSprite(36, 53);
//        enemies[5][4] = getEnemieSprite(48, 53);

    }

    public BufferedImage getPlayerSprite(int xx, int yy) {
        return spritesheet.getSubimage(xx, yy, 16, 9);
    }

    public BufferedImage getEnemieSprite(int xx, int yy) {
        return spritesheet.getSubimage(xx, yy, 17, 10);
    }
}
