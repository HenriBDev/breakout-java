package cg.projeto.Events;

import java.util.Random;

import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;

import cg.projeto.Main;
import cg.projeto.Game.Jogo;
import cg.projeto.Game.Estados.EstadosBola;
import cg.projeto.Game.Estados.EstadosJogo;
import cg.projeto.UI.Tela;

public class Mouse implements MouseListener{

    public Mouse(){}

    public void mouseExited(MouseEvent e){}

    public void mouseDragged(MouseEvent e){
        
    }

    public void mouseReleased(MouseEvent e){}

    public void mouseClicked(MouseEvent e){
        if(!(Main.DEBUG) && Jogo.estado == EstadosJogo.JOGANDO && Jogo.bola.estado == EstadosBola.PARADA){
            Jogo.bola.direcaoMovimentacaoX = new Random().nextBoolean() ? 1 : -1;
            Jogo.bola.direcaoMovimentacaoY = 1;
            Jogo.bola.estado = EstadosBola.MOVENDO;
        }
    }

    public void mousePressed(MouseEvent e){}

    public void mouseEntered(MouseEvent e){}

    public void mouseMoved(MouseEvent e){

        if(!(Main.DEBUG) && Jogo.estado == EstadosJogo.JOGANDO && Jogo.bola.estado == EstadosBola.MOVENDO){
            float distanciaBolaBastaoX = Jogo.bastao.elemento.x - Jogo.bola.elemento.x;
            float posicaoBastaoX;
            if(e.getX() - Tela.screenWidth/2 + Jogo.bastao.elemento.largura/2 >= Tela.screenWidth/2){
                posicaoBastaoX = Tela.screenWidth/2 - Jogo.bastao.elemento.largura/2;
            }
            else if(e.getX() - Tela.screenWidth/2 - Jogo.bastao.elemento.largura/2 <= Tela.screenWidth/2 * -1){
                posicaoBastaoX = Tela.screenWidth/2 * -1 + Jogo.bastao.elemento.largura/2;
            }
            else{
                posicaoBastaoX = e.getX() - Tela.screenWidth/2;
            }
            Jogo.bastao.elemento.moverComponente(posicaoBastaoX, Jogo.bastao.elemento.y, Jogo.bastao.elemento.z);
            if(Jogo.bola.estado == EstadosBola.PARADA){
                Jogo.bola.elemento.moverComponente(posicaoBastaoX + distanciaBolaBastaoX, Jogo.bola.elemento.y, Jogo.bola.elemento.z);
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
