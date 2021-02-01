package stellarapex;

import javax.swing.*;

/**
 * Stellar Apex StellarApexMain
 * Date: 02/22/19
 *
 * @author Nate Heppard
 */

public class StellarApexMain extends JFrame{
    
    private static final int WIDTH=800,HEIGHT=800;

    public static void main(String[] args){
        JFrame frame=new JFrame("Stellar Apex");
        //frame.setSize(new Dimension(WIDTH,HEIGHT));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        StellarApexUI ui=new StellarApexUI(); // new instance of UI
        frame.add(ui);
        
        frame.getContentPane();
        frame.pack();
        frame.setLocationRelativeTo(null); // center on monitor
        frame.setVisible(true);
    } 
}
