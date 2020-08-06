import java.awt.*;
import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.Iterator;

public class Obstacles {
    private class Obstacle
    {
        BufferedImage image;
        int x,y;

        Rectangle getObstacle()
        {
            Rectangle obstacle = new Rectangle();
            obstacle.x = x;
            obstacle.y = y;
            obstacle.width = image.getWidth();
            obstacle.height = image.getHeight();

            return obstacle;
        }
    }

    private int first, objectFar, speed;

    private ArrayList <BufferedImage> imageList;
    private ArrayList <Obstacle> obList;

    private Obstacle blocking;

    public Obstacles(int fPosition)
    {
     obList = new ArrayList<Obstacle>();
     imageList = new ArrayList<BufferedImage>();

     first = fPosition;
     objectFar = 300;
     speed = 13;

     imageList.add(new Resource().getResourceImage("images\\blender.png"));
        imageList.add(new Resource().getResourceImage("images\\fork.png"));
        imageList.add(new Resource().getResourceImage("images\\knife.png"));

     int x = first;
     for(BufferedImage bi : imageList)
     {
         Obstacle ob = new Obstacle();

         ob.image = bi;
         ob.x = x;
         ob.y = Ground.getHeightGround() - bi.getHeight() + 3;
         x += objectFar;

         obList.add(ob);
     }
    }

    public void update()
    {
        Iterator<Obstacle> looper = obList.iterator();

        Obstacle firstOb = looper.next();
        firstOb.x -= speed;

        while(looper.hasNext())
        {
            Obstacle ob = looper.next();
            ob.x -= speed;
        }


        if(firstOb.x < -firstOb.image.getWidth())
        {
            obList.remove(firstOb);
            firstOb.x = obList.get(obList.size() - 1).x + objectFar;
            obList.add(firstOb);
        }
    }
    public void create(Graphics g)
    {
        for(Obstacle ob : obList) { g.drawImage(ob.image, ob.x, ob.y, null); }
    }

    public boolean crashed()
    {
        for(Obstacle ob : obList)
        {
            if(PineApple.getPineApple().intersects(ob.getObstacle())) { return true; }
        }
        return false;
    }

    public void resume()
    {
        int x = first/2;
        obList = new ArrayList<Obstacle>();

        for(BufferedImage bi : imageList)
        {
            Obstacle ob = new Obstacle();

            ob.image =bi;
            ob.x = x;
            ob.y = Ground.getHeightGround() - bi.getHeight() + 3;
            x += objectFar;

            obList.add(ob);
        }
    }
}
