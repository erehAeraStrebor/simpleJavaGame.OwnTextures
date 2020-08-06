import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {

    public static int getWindowHeight() {
        return WINDOW_HEIGHT;
    }
    public static int getWindowWidth() {
        return WINDOW_WIDTH;
    }

    private static final int WINDOW_HEIGHT = 400;
    private static final int WINDOW_WIDTH = 2*WINDOW_HEIGHT;

    public Game()
    {
        super("Pineapple");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocation(370, 200);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        Container c = this.getContentPane();
        View view = new View();
        view.addKeyListener(view);
        view.setFocusable(true);

        c.setLayout(new BorderLayout());
        c.add(view, BorderLayout.CENTER);

        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(() -> new Game());
        /*javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Game();
            }
        });*/
    }
}
