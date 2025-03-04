import javax.swing.*;

public class Constants {
    public static final int TILE_SIZE = 32;
    public static final int ROWS = 16;
    public static final int COLUMNS = 16;
    public static final int BOARD_WIDTH = TILE_SIZE * COLUMNS; // 32 * 16 = 512
    public static final int BOARD_HEIGHT = TILE_SIZE * ROWS; // 32 * 16 = 512
    public static final long FIRE_INTERVAL = 250;
    public static Timer GAME_LOOP;
    public static int SCORE = 0;
    public static boolean GAME_OVER = false;
}
