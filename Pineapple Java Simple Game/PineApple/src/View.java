import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;


public class View extends JPanel implements Runnable, KeyListener {

    private static int HEIGHT_V, WIDTH_V;
    private Thread thread;







    //private String highScore;

    private boolean  gameRun = false, gameOver = false;
//gameStarted

    private String saveDataPath;
    private String fileName = "HghScore";



    int scoreDuring, highScore;

    Ground ground;
    PineApple pineApple;
    Obstacles objects;

    public View(){

        try{
            saveDataPath = View.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            loadingHighScore();
        } catch (Exception e) {
            e.printStackTrace();
        }
        HEIGHT_V = Game.getWindowHeight();
        WIDTH_V = Game.getWindowWidth();
        setSize(WIDTH_V, HEIGHT_V);
        setVisible(true);
        setBackground(Color.CYAN);
        //scoreDuring = 0;
        //gameStarted = false;
        ground = new Ground(HEIGHT_V);
        pineApple = new PineApple();
        objects = new Obstacles((int)(1000));
    }

    private void createHighScore(){
        try{
            File file = new File(saveDataPath, fileName);
            FileWriter output = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(output);
            writer.write("" + 0);
            writer.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    private void loadingHighScore(){
        try{
            File f = new File(saveDataPath, fileName);
            if(!f.isFile()){
                createHighScore();
            }
            BufferedReader reader = new BufferedReader((new InputStreamReader(new FileInputStream(f))));
            highScore = Integer.parseInt(reader.readLine());
            reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setHighScore(){
        FileWriter output =null;
        try{
            File f = new File(saveDataPath, fileName);
            output = new FileWriter(f);
            BufferedWriter writer = new BufferedWriter(output);
            writer.write(""+highScore);
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void paint(Graphics g) {
        super.paint(g);

        g.setFont(new Font("Serif Bold Italic", Font.BOLD, 35));
        g.setColor(Color.red);
        g.drawString("Best " + highScore, getWidth()/2 - 60, 50 );
        g.setFont(new Font("Monospaced", Font.ITALIC, 25));
        g.setColor(Color.pink);
        g.drawString("Actual " + scoreDuring, getWidth()/2 - 57, 100);
        ground.create(g);
        pineApple.create(g);
        objects.create(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == ' '){
            if(gameOver) reset();
            if(thread == null || !gameRun)
            {
                System.out.println("oho its goin to destroy your head");
                thread = new Thread(this);
                thread.start();
                pineApple.startin();
            }else pineApple.jump();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

   public void updateGame() throws IOException {
        scoreDuring += 1;
        if(scoreDuring >= highScore)highScore = scoreDuring;
        ground.update();
        objects.update();
        if(objects.crashed()) {
            pineApple.die();
            repaint();
            gameRun = false;
            gameOver = true;
            setHighScore();
            System.out.println("looser");
        }
   }
   public void reset() {
        scoreDuring = 0;
        objects.resume();
        gameOver = false;
   }

    @Override
    public void run() {
        gameRun = true;

        while(gameRun)
        {
            try {
                updateGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
            repaint();
            try {
                Thread.sleep(50);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
