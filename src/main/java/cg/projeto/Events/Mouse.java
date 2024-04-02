package cg.projeto.Events;

import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;

import cg.projeto.Main;
import cg.projeto.Game.Bastao;
import cg.projeto.Game.Estados.EstadosBola;
import cg.projeto.UI.Tela;

public class Mouse implements MouseListener{

    public Mouse(){}

    public void mouseExited(MouseEvent e){}

    public void mouseDragged(MouseEvent e){
        
    }

    public void mouseReleased(MouseEvent e){}

    public void mouseClicked(MouseEvent e){}

    public void mousePressed(MouseEvent e){}

    public void mouseEntered(MouseEvent e){}

    public void mouseMoved(MouseEvent e){

        if(!(Main.DEBUG)){
            float distanciaBolaBastaoX = Tela.jogo.bastao.elemento.x - Tela.jogo.bola.elemento.x;
            Tela.jogo.bastao.elemento.moverComponente(e.getX(), Tela.jogo.bastao.elemento.y, Tela.jogo.bastao.elemento.z);
            if(Tela.jogo.bola.estado == EstadosBola.PARADA){
                Tela.jogo.bola.elemento.moverComponente(e.getX() + distanciaBolaBastaoX, Tela.jogo.bola.elemento.y, Tela.jogo.bola.elemento.z);
            }
        }

    }

    public void mouseWheelMoved(MouseEvent e){

        System.out.println("Mouse wheel moved: " + (e.getRotation()[1] == 1 ? "forward" : "backwards"));

        if(Main.DEBUG){
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
    
}
