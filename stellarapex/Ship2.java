package stellarapex;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

public final class Ship2{
    
    private final int RSIZE=280;    // regular size
    private final int DSIZE=350;    // diagonal size
    private final String[] paths={
        "up_split","down_split","left_split","right_split",
        "upleft_split","upright_split","downleft_split","downright_split"
    };
    
    private ArrayList<ImageIcon[]> sprites;
    private ImageIcon[] currentArray;
    private ImageIcon currentSprite,startSprite,shipNeutral;
    private int x,y,sx,sy,spriteIndex;  // x/y = true origin, sx/sy = img center
    private int currentSize;    
    private boolean isDiagonal;
    
    public Ship2(int x,int y){
        this.x=x;
        this.y=y;
        
        sx=this.x+(RSIZE/2);
        sy=this.y+(RSIZE/2);
        
        sprites=new ArrayList<>();
        
        shipNeutral=resize(new ImageIcon(getClass()
                .getResource("/res/IExU-100 (engine off).gif")),isDiagonal);
        
        startSprite=shipNeutral;
        
        spriteIndex=0;
        currentSprite=startSprite;
        currentSize=RSIZE;
        isDiagonal=false;
        
        for(int i=0;i<8;i++)
            sprites.add(new ImageIcon[3]);
        
        int index=0; // DO NOT ALTER
        
        for(String path : paths){
            if(index>1)
                isDiagonal=true;
            fillSpriteArrays(path,sprites.get(index));
            index++;
        }
        currentArray=sprites.get(0);
    }
    
    /*--- Getters/Setters ---*/
    
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
        return currentSize;
    }
    
    public ImageIcon getCurrentSprite(){
        return currentSprite;
    }
    
    /*--- Class Methods --- */
    
    public void setSpriteArray(int index){
        currentArray=sprites.get(index);
    }
    
    public void forwardSprite(){
        if(spriteIndex>2)
            spriteIndex=0;
        currentSprite=currentArray[spriteIndex];
        spriteIndex++;
    }
    
    public void printArray(ImageIcon[] list){
        for(ImageIcon img : list){
            System.out.println(img);
        }
    }
    
    public void neutralSprite(){
        currentSprite=shipNeutral;
    }
    
    public void increaseSize(){
        currentSize=DSIZE;
    }
    
    public void decreaseSize(){
        currentSize=RSIZE;
    }
    
    /*--- Helper Methods ---*/
    
    public ImageIcon resize(ImageIcon icon,boolean isDiagonal){
        int size=RSIZE;
        
        if(isDiagonal)
            size=DSIZE;
        
        Image img=icon.getImage();
        Image newImg=img.getScaledInstance(size,size,Image.SCALE_SMOOTH);
        icon=new ImageIcon(newImg);
        
        return icon;
    }
    
    public void fillSpriteArrays(String path,ImageIcon[] list){
        URL url=getClass().getResource("/res/ship_sprites/"+path);
        File dir=new File(url.getPath());
        String[] files=dir.list();
        
        int index=0; // DO NOT ALTER

        if(files==null){
            System.out.println("dir does not exist/cannot be found");
        }else{
            for(String file : files){
                ImageIcon img=resize(new ImageIcon(getClass()
                        .getResource("/res/ship_sprites/"+path+"/"+file)),isDiagonal);
                list[index]=img;
                index++;
            }
        }
    }
}
