package stellarapex;

/**
 * Star Class
 *
 * @author Nate Heppard
 */

public final class Star{
        
    private int starSize,ticks;
    private double x,y,moveX,moveY,angle;
    
    public Star(double x,double y,int starSize,double angle){
        this.x=x;
        this.y=y;
        this.starSize=starSize;
        this.angle=angle;
        this.ticks=0;
    }
    
    public double getX(){
        return x;
    }
    
    public void setX(double x){
        this.x=x;
    }
    
    public double getY(){
        return y;
    }
    
    public void setY(double y){
        this.y=y;
    }
    
    public double getAngle(){
        return angle;
    }
    
    public void setAngle(double angle){
        this.angle=angle;
    }
    
    public int getStarSize(){
        return starSize;
    }
    
    public void setStarSize(int starSize){
        this.starSize=starSize;
    }
    
    public int getTicks(){
        return ticks;
    }
    
    public void addTicks(){
        this.ticks++;
    }
    
    public void resetTicks(){
        this.ticks=0;
    }
    
    public void enlargeStar(){
        this.starSize++;
    }
    
    public void determineMoves(){
        moveX=(Math.cos(angle));
        moveY=(Math.sin(angle)*-1);
    }
    
    public void transformStar(){
        this.x+=moveX;
        this.y+=moveY;
    }
}

