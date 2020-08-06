

import java.awt.*;
import java.awt.image.BufferedImage;

public class PineApple {
    static BufferedImage stand;
    BufferedImage right, left, death;
    private static int pineH, pineTopH, pineStartW, pineEndW, appleTOP, appleBOT, pointTOP, jumper = 15;
    private static boolean pointTopReached;
    private static int actualState;
    public static final int STENDI = 1, RUNNIN = 2, JUMPIN = 3, DEAD = 4;
    private int foot;
    private final int ON_LEFT = 1, ON_RIGHT = 2, NO_FOOT = 3;


    public PineApple()
    {
        stand = new Resource().getResourceImage("images\\PineApple-stand.png");
        right = new Resource().getResourceImage("images\\Pineapple-rightFoot.png");
        left = new Resource().getResourceImage("images\\PineApple-leftFoot.png");
        death = new Resource().getResourceImage("images\\PineApple-dead.png");

        pineH = Ground.getHeightGround() + 3;
        pineTopH = Ground.getHeightGround() - stand.getHeight() + 3;
        pineStartW = 200;
        pineEndW = pineStartW + stand.getWidth();
        pointTOP = pineTopH - 120;

        actualState = 1;
        foot = NO_FOOT;
    }

    public void create(Graphics g)
    {
        appleBOT = pineTopH + stand.getHeight();
        switch(actualState)
        {
            case STENDI:
                g.drawImage(stand, pineStartW, pineTopH, null);
                break;
            case RUNNIN:
                if(foot==NO_FOOT)
                {
                    foot = ON_RIGHT;
                    g.drawImage(right, pineStartW, pineTopH, null);
                }
                else if(foot==ON_RIGHT)
                {
                    foot = ON_LEFT;
                    g.drawImage(left, pineStartW, pineTopH, null);

                }else
                    {
                        foot = ON_RIGHT;
                        g.drawImage(right, pineStartW, pineTopH, null);
                    }
            break;

            case JUMPIN:
                if(appleTOP > pointTOP && !pointTopReached)
                {
                    g.drawImage(stand, pineStartW, appleTOP -= jumper, null);
                    break;
                }
                if(appleTOP >= pointTOP && !pointTopReached)
                {
                    pointTopReached = true;
                    g.drawImage(stand, pineStartW, appleTOP += jumper, null);
                    break;
                }
                if (appleTOP > pointTOP && pointTopReached)
                {
                    if(pineTopH == appleTOP && pointTopReached)
                    {
                        actualState = RUNNIN;
                        pointTopReached = false;
                        break;
                    }
                    g.drawImage(stand, pineStartW, appleTOP += jumper, null);
                    break;
                }
            case DEAD:
                g.drawImage(death, pineStartW, appleTOP, null);
                break;
        }
    }

    public void die(){actualState = DEAD;}
    public static Rectangle getPineApple()
    {
        Rectangle pineApple = new Rectangle();
        pineApple.x = pineStartW;

        if(actualState == JUMPIN && !pointTopReached) pineApple.y = appleTOP - jumper;
        else if (actualState == JUMPIN && pointTopReached) pineApple.y = appleTOP + jumper;
        else if(actualState != JUMPIN) pineApple.y = appleTOP;
        pineApple.height = stand.getHeight();
        pineApple.width = stand.getWidth();
        return pineApple;

    }
    public void startin()
    {
        appleTOP = pineTopH;
        actualState = RUNNIN;
    }
    public void jump()
    {
        appleTOP = pineTopH;
        pointTopReached = false;
        actualState = JUMPIN;
    }

}
