package cg.projeto.Input;

import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;

import cg.projeto.UI.Tela;

public class Mouse implements MouseListener{

    public Mouse(){}

    public void mouseExited(MouseEvent e){}

    public void mouseDragged(MouseEvent e){}

    public void mouseReleased(MouseEvent e){}

    public void mouseClicked(MouseEvent e){}

    public void mousePressed(MouseEvent e){}

    public void mouseEntered(MouseEvent e){}

    public void mouseMoved(MouseEvent e){}

    public void mouseWheelMoved(MouseEvent e){

        System.out.println("Mouse wheel moved: " + (e.getRotation()[1] == 1 ? "forward" : "backwards"));

        if(e.getRotation()[1] == 1){
            Tela.escalaCamera += .1f;
        } 
        else if (e.getRotation()[1] == -1){
            if(Tela.escalaCamera - .25f > 0){
                Tela.escalaCamera -= .1f;
            }
        }

    }
    
}
