package cg.projeto;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

public class Input implements KeyListener{
    
    public Input(){}
    
    public void keyPressed(KeyEvent e) {        

        System.out.println("Key pressed: " + e.getKeyCode());

        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            System.exit(0);
    }

    public void keyReleased(KeyEvent e) { }

}
