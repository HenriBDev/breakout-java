package cg.projeto.Events;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

import cg.projeto.Main;
import cg.projeto.Debug.ModoEdicao;
import cg.projeto.Game.Estados.EstadosJogo;
import cg.projeto.UI.Tela;

public class Keyboard implements KeyListener{
    
    public Keyboard(){}
    
    public void keyPressed(KeyEvent e) {        

        System.out.println("Key pressed: " + e.getKeyCode());

        switch(e.getKeyCode()){

            case KeyEvent.VK_ESCAPE:
                System.exit(0);
            break;

            // Setas
            case KeyEvent.VK_RIGHT:
                if(Main.DEBUG){
                    switch(Tela.modoEdicao){
                        case MOVER:
                            Tela.posicaoCamera[0]+=10;
                        break;
                        case ROTACIONAR:
                            Tela.rotacaoCamera[0]+=1;
                        break;
                    }
                }
            break;
            case KeyEvent.VK_LEFT:
                if(Main.DEBUG){
                    switch(Tela.modoEdicao){
                        case MOVER:
                            Tela.posicaoCamera[0]-=10;
                        break;
                        case ROTACIONAR:
                            Tela.rotacaoCamera[0]-=1;
                        break;
                    }
                }
            break;
            case KeyEvent.VK_UP:
                if(Main.DEBUG){
                    switch(Tela.modoEdicao){
                        case MOVER:
                            Tela.posicaoCamera[1]+=10;
                        break;
                        case ROTACIONAR:
                            Tela.rotacaoCamera[1]+=1;
                        break;
                    }
                }
            break;
            case KeyEvent.VK_DOWN:
                if(Main.DEBUG){
                    switch(Tela.modoEdicao){
                        case MOVER:
                            Tela.posicaoCamera[1]-=10;
                        break;
                        case ROTACIONAR:
                            Tela.rotacaoCamera[1]-=1;
                        break;
                    }
                }
            break;

            // Debug
            case KeyEvent.VK_M:
                if(Main.DEBUG){
                    if(Tela.modoEdicao != ModoEdicao.MOVER){
                        Tela.modoEdicao = ModoEdicao.MOVER;
                    }
                }
            break;
            case KeyEvent.VK_R:
                if(Main.DEBUG)
                {
                    if(Tela.modoEdicao != ModoEdicao.ROTACIONAR)
                    {
                        Tela.modoEdicao = ModoEdicao.ROTACIONAR;
                    }
                }
                else
                {
                    if(Tela.jogo.estado == EstadosJogo.JOGANDO)
                    {
                        Tela.jogo.pontuacao = 0;
                        Tela.jogo.vidas = 5;
                        Tela.jogo.fase = 1;
                        Tela.jogo.resetarPosicoes();
                    }
                }
            break;

            // Jogo
            case KeyEvent.VK_ENTER:
                if(!(Main.DEBUG) && Tela.jogo.estado == EstadosJogo.INICIAL){
                    Tela.jogo.mudarEstado(EstadosJogo.JOGANDO);
                }
            break;
            case KeyEvent.VK_P:
                if(!(Main.DEBUG)){
                    if(Tela.jogo.estado == EstadosJogo.JOGANDO) Tela.jogo.mudarEstado(EstadosJogo.PAUSADO);
                    else if(Tela.jogo.estado == EstadosJogo.PAUSADO) Tela.jogo.mudarEstado(EstadosJogo.JOGANDO);
                }
            break;
        }
    }

    public void keyReleased(KeyEvent e) { }

}
