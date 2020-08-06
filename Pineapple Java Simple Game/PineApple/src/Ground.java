import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

public class Ground {
    private class GroundImage
    {
        BufferedImage image;
        int x;
    }

    public static int getHeightGround() { return HEIGHT_GROUND; }

    private static int HEIGHT_GROUND;
    private BufferedImage img;
    private ArrayList<GroundImage> groundSet;

    public Ground(int windowH)
    {
        HEIGHT_GROUND = (int)(windowH - 0.22 * windowH -1);
        img = new Resource().getResourceImage("images\\Ground.png");
        groundSet = new ArrayList<GroundImage>();
        for(int i = 0; i < 3; i++)
        {
            GroundImage obj = new GroundImage();
            obj.image = img;
            obj.x = 0;
            groundSet.add(obj);
        }
    }

    public void update()
    {
        Iterator<GroundImage> looper = groundSet.iterator();
        GroundImage first = looper.next();
        first.x -= 10;

        int previous = first.x;
        while(looper.hasNext())
        {
            GroundImage next = looper.next();
            next.x = previous + img.getWidth();
            previous = next.x;
        }
        if(first.x < -img.getWidth())
        {
         groundSet.remove(first);
         first.x = previous + img.getWidth();
         groundSet.add(first);
        }
    }

    public void create(Graphics g)
    {
        for(GroundImage gi : groundSet) { g.drawImage(gi.image, (int)gi.x, HEIGHT_GROUND, null); }
    }
}
