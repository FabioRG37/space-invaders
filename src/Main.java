import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SpaceInvaders spaceInvaders = new SpaceInvaders();

        JFrame frame = new JFrame("Space Invaders");
//        frame.setVisible(true);
        frame.setSize(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(spaceInvaders);
        frame.pack();
        spaceInvaders.requestFocus();
        frame.setVisible(true);
    }
}