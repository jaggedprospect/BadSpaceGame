package stellarapex;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Input Class
 *
 * @author Nate Heppard
 * # Based on code by Majoolwip
 */

public class Input implements KeyListener{
    
    private final int NUM_KEYS=256; // for standard keyboard
    private boolean[] keys=new boolean[NUM_KEYS];
    private boolean[] keysLast=new boolean[NUM_KEYS];

    public Input(){
        // nothing here yet
    }
    
    public boolean isKey(int keyCode){
        return keys[keyCode];
    }
    
    public boolean isKeyUp(int keyCode){
        return !keys[keyCode] && keysLast[keyCode];
    }
    
    public boolean isKeyDown(int keyCode){
        return keys[keyCode] && !keysLast[keyCode];
    }
    
    public void update(){ // called after each input
        for(int i=0;i<NUM_KEYS;i++){
            keysLast[i]=keys[i];
        }
    }

    @Override
    public void keyPressed(KeyEvent e){
        keys[e.getKeyCode()]=true;
    }

    @Override
    public void keyReleased(KeyEvent e){
        keys[e.getKeyCode()]=false;
    }
    
    @Override
    public void keyTyped(KeyEvent e){}
}
