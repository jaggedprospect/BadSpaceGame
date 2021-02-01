package stellarapex;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Ship Class
 *
 * @author Nate Heppard
 */

public final class Ship{
    
    private final int SIZE=280;
    
    private ImageIcon up,down,left,right,straight,current;
    private ArrayList<ImageIcon> sprites=new ArrayList<>();
    private int x,y;
    
    public Ship(int x,int y){
        this.x=x;
        this.y=y;
        
        // declare sprite paths
        String upPath="/res/IExU-100 (up).gif";
        String downPath="/res/IExU-100 (down).gif";
        String straightPath="/res/IExU-100 (straight).gif";
        String animatedPath="/res/IExU-100 (stationary,animated).gif";
        
        // set sprite images
        up=imageResize(new ImageIcon(getClass().getResource(upPath)));
        down=imageResize(new ImageIcon(getClass().getResource(downPath)));
        straight=imageResize(new ImageIcon(getClass().getResource(straightPath)));
        left=imageResize(new ImageIcon(getClass().getResource(straightPath)));
        right=imageResize(new ImageIcon(getClass().getResource(straightPath)));
        
        sprites.add(up);
        sprites.add(down);
        sprites.add(left);
        sprites.add(right);
        sprites.add(straight);
        
        current=straight;
    }
    
    public int getX(){
        return x;
    }
    
    public void setX(int x){
        this.x=x;
    }
    
    public int getY(){
        return y;
    }
    
    public void setY(int y){
        this.y=y;
    }
    
    public int getSize(){
        return SIZE;
    }
    
    public ImageIcon getSprite(int index){
        return sprites.get(index);
    }
    
    public void setSprite(int index){
        current=sprites.get(index);
    }
    
    public ImageIcon currentSprite(){
        return current;
    }
    
    public ImageIcon imageResize(ImageIcon icon){ // resizes ship images
        Image img=icon.getImage();
        Image newImg=img.getScaledInstance(SIZE,SIZE,java.awt.Image.SCALE_SMOOTH);
        icon=new ImageIcon(newImg);
        return icon ;
    }
}
