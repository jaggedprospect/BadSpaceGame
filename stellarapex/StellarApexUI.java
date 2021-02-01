package stellarapex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

/**
 * Stellar Apex User Interface
 * Date: 02/22/19
 *
 * @author Nate Heppard
 */

class StellarApexUI extends JPanel{

    private final int WIDTH=800,HEIGHT=600;
    private final int MAIN_DELAY=10; // refresh rate
    /*-------------------------------------*/
    // Constants for stars
    private final int STAR_SIZE=4; // final star xMargin
    private final int STAR_POP=60; // number of stars
    private final DecimalFormat DF=new DecimalFormat("#0.00");
    /*-------------------------------------*/
    // Constants for ship
    private final int JUMP=5;
    private final int IMAGE_SIZE=31;
    private final int SHIP_DELAY=80;
    /*========================================================*/
    // Fields for stars
    private ArrayList<Star> stars=new ArrayList<>();
    private Timer mainTimer;
    private ImageIcon background;
    private double centerX,centerY,moveX,moveY;
    /*-------------------------------------*/
    // Fields for ship
    private Ship2 ship;
    private Timer animationTimer;
    private AffineTransform transform;
    //private int shipX,shipY;
    private Input input; // for getting key input
    /*-------------------------------------*/
    // Fields for background
    private int bgX,bgY;
    /*-------------------------------------*/
    // Fields for frame rate
    private Timer frameTimer;
    private int frames,fps;
    /*-------------------------------------*/
    private boolean showStats=true;
    
    public StellarApexUI(){ // Initial JPanel setup
        mainTimer=new Timer(MAIN_DELAY,new TimerListener());
        frameTimer=new Timer(1000,new FrameTask());
        animationTimer=new Timer(SHIP_DELAY,new AnimationTask());
        centerX=WIDTH/2;
        centerY=HEIGHT/2;
        bgX=bgY=-100;
        
        // get background image
        background=new ImageIcon(getClass().getResource("/res/spacebg2.gif"));
        Image img=background.getImage();
        Image newImg=img.getScaledInstance(1000,800,Image.SCALE_SMOOTH);
        background=new ImageIcon(newImg);
        
        createStars(); // fill array of Star objects
        setStarMoves(); // set initial star transformation values
        /*-------------------------------------*/
        input=new Input();
        addKeyListener(input);
                
        // for moving sprite images
        transform=new AffineTransform();
                
        ship=new Ship2((int)centerX,(int)(centerY*1.5));
        
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setBackground(Color.GREEN);
        setFocusable(true);
        
        // start timers
        mainTimer.start();
        frameTimer.start();
        animationTimer.start();
    }
    
    public void createStars(){ // Initializes array of Star objects
        for(int i=0;i<STAR_POP;i++)
            stars.add(new Star(centerX,centerY,1,originAngle()));
    }
    
    public double originAngle(){ // determines where Star begins
        Random rn=new Random();
        int degree=rn.nextInt(360);
        double angle=Double.valueOf(DF.format(degree*(Math.PI/180))); // degree 0-359
        
        return angle;
    }
    
    public void checkStarSize(Star star){ // checks the xMargin of the Star
        if(star.getStarSize()<STAR_SIZE){
            star.enlargeStar();
        }
    }
    
    public void setStarMoves(){ // determines Star transformation values (moveX,moveY)
        for(Star s : stars)
            s.determineMoves();
    }
    
    public boolean inBounds(int change){ // ** NEEDS WORK - STUCK IN CORNERS **
        int xMargin=ship.getSize()-200;
        int yMargin=ship.getSize()-240;
        return !(ship.getX()+change<=xMargin || ship.getX()+change>=WIDTH-xMargin ||
                ship.getY()+change<=yMargin || ship.getY()+change>=HEIGHT-yMargin);
    }
    
    @Override
    public void paintComponent(Graphics g){ // paints graphics
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D)g;
        g.setColor(Color.WHITE);  
        
        background.paintIcon(this,g,bgX,bgY);
        Ellipse2D star;
        
        for(Star s : stars){
            star=new Ellipse2D.Double(s.getX(),s.getY(),s.getStarSize(),s.getStarSize());
            g2.fill(star);
        }
        
        transform.setToTranslation(ship.getX()-140,ship.getY()-140); // moves ship image
        g2.drawImage(ship.getCurrentSprite().getImage(),transform,this);
        
        // show current stats (optional)
        if(showStats){
            g.setColor(Color.GREEN);
            g.drawString("Star count: "+STAR_POP,20,20);
            g.drawString("Ship coordinates: ("+ship.getX()+", "+ship.getY()+")",20,40);
            g.drawString("FPS: "+fps,20,60);        
        }
    }   
    
    private class TimerListener implements ActionListener{ // main loop
 
        @Override
        public void actionPerformed(ActionEvent event){     
            // reset Star if it travels outside of panel
            for(Star s : stars){
                if(s.getX()<=0 || s.getX()>=WIDTH+STAR_SIZE || s.getY()<=0 || s.getY()>=HEIGHT+STAR_SIZE){ // resets everything
                    s.setX(WIDTH/2);
                    s.setY(HEIGHT/2);
                    s.setStarSize(1);
                    s.setAngle(originAngle());
                    s.determineMoves();
                    s.resetTicks();
                }

                // enlarge Star every 85 ticks
                if(s.getTicks()==85){
                    checkStarSize(s);
                    s.resetTicks();
                }
                
                s.transformStar(); // move Star position on screen
                s.addTicks(); // move tick forward 1
            }
            
            /*--- Key Input ---*/
            if(input.isKey(KeyEvent.VK_UP)){
                ship.setSpriteArray(0);
                //ship.getSprite();
                if(inBounds(-JUMP)){
                    ship.setY(ship.getY()-JUMP);
                    bgY++; // move background
                }
            }
            else if(input.isKey(KeyEvent.VK_DOWN)){
                ship.setSpriteArray(1);
                //ship.getSprite();
                if(inBounds(JUMP)){
                    ship.setY(ship.getY()+JUMP);
                    bgY--;
                }
            }
            
            if(input.isKey(KeyEvent.VK_LEFT)){
                ship.setSpriteArray(2);
                //ship.getSprite();
                if(inBounds(-JUMP)){
                    ship.setX(ship.getX()-JUMP);
                    bgX++;
                }
            }
            else if(input.isKey(KeyEvent.VK_RIGHT)){
                ship.setSpriteArray(3);
                //ship.getSprite();
                if(inBounds(JUMP)){
                    ship.setX(ship.getX()+JUMP);
                    bgX--;
                }
            }
                
            if(input.isKeyDown(KeyEvent.VK_UP) && input.isKeyDown(KeyEvent.VK_LEFT)){
                //ship.increaseSize();
                System.out.println("upleft");
                ship.setSpriteArray(4);
            }
            else if(input.isKeyDown(KeyEvent.VK_UP) && input.isKeyDown(KeyEvent.VK_RIGHT)){
                //ship.increaseSize();
                System.out.println("upright");
                ship.setSpriteArray(5);
            }
            else if(input.isKeyDown(KeyEvent.VK_DOWN) && input.isKeyDown(KeyEvent.VK_LEFT)){
                //ship.increaseSize();
                System.out.println("donwleft");
                ship.setSpriteArray(6);
            }
            else if(input.isKeyDown(KeyEvent.VK_DOWN) && input.isKeyDown(KeyEvent.VK_RIGHT)){
                //ship.increaseSize();
                System.out.println("downright");
                ship.setSpriteArray(7);
            }
                
            /*--- Updates ---*/
            input.update();
            frames++;
            repaint();
        }
    }
    
    private class FrameTask implements ActionListener{ // determines frames per second
        // Task action repeats every second 
        @Override
        public void actionPerformed(ActionEvent event){
            fps=frames; // set fps to final frame value
            frames=0; // reset frame value
        }
    }
    
    private class AnimationTask implements ActionListener{
        
        @Override 
        public void actionPerformed(ActionEvent event){
            ship.forwardSprite();
        }
    }
}
