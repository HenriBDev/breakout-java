package cg.projeto.Input;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

import cg.projeto.Debug.ModoEdicao;
import cg.projeto.UI.Tela;

public class Keyboard implements KeyListener{
    
    public Keyboard(){}
    
    public void keyPressed(KeyEvent e) {        

        System.out.println("Key pressed: " + e.getKeyCode());

        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            System.exit(0);

        // Setas
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            switch(Tela.modoEdicao){
                case MOVER:
                    Tela.posicaoCamera[0]+=10;
                break;
                case ROTACIONAR:
                    Tela.rotacaoCamera[0]+=1;
                break;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            switch(Tela.modoEdicao){
                case MOVER:
                    Tela.posicaoCamera[0]-=10;
                break;
                case ROTACIONAR:
                    Tela.rotacaoCamera[0]-=1;
                break;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_UP){
            switch(Tela.modoEdicao){
                case MOVER:
                    Tela.posicaoCamera[1]+=10;
                break;
                case ROTACIONAR:
                    Tela.rotacaoCamera[1]+=1;
                break;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            switch(Tela.modoEdicao){
                case MOVER:
                    Tela.posicaoCamera[1]-=10;
                break;
                case ROTACIONAR:
                    Tela.rotacaoCamera[1]-=1;
                break;
            }
        }

        // Debug
        if(e.getKeyCode() == KeyEvent.VK_M)
            if(Tela.modoEdicao != ModoEdicao.MOVER) Tela.modoEdicao = ModoEdicao.MOVER;
        if(e.getKeyCode() == KeyEvent.VK_R)
            if(Tela.modoEdicao != ModoEdicao.ROTACIONAR) Tela.modoEdicao = ModoEdicao.ROTACIONAR;

    }

    public void keyReleased(KeyEvent e) { }

}
