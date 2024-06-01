package cg.projeto.Eventos;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

import cg.projeto.Main;
import cg.projeto.Jogo.DebugLoop;
import cg.projeto.Jogo.GameLoop;
import cg.projeto.Jogo.Estados.EstadosJogo;
import cg.projeto.Jogo.Estados.Debug.EstadosEditor;
import cg.projeto.Motor.Renderizador;

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
                if(Main.DEBUG)
                {
                    switch(DebugLoop.modoEdicao)
                    {
                        case MOVER:
                            Renderizador.posicaoCamera[0]+=10;
                        break;
                        case ROTACIONAR:
                            Renderizador.rotacaoCamera[0]+=1;
                        break;
                    }
                }
            break;

            case KeyEvent.VK_LEFT:
                if(Main.DEBUG)
                {
                    switch(DebugLoop.modoEdicao)
                    {
                        case MOVER:
                            Renderizador.posicaoCamera[0]-=10;
                        break;
                        case ROTACIONAR:
                            Renderizador.rotacaoCamera[0]-=1;
                        break;
                    }
                }
            break;

            case KeyEvent.VK_UP:
                if(Main.DEBUG)
                {
                    switch(DebugLoop.modoEdicao)
                    {
                        case MOVER:
                            Renderizador.posicaoCamera[1]+=10;
                        break;
                        case ROTACIONAR:
                            Renderizador.rotacaoCamera[1]+=1;
                        break;
                    }
                }
            break;

            case KeyEvent.VK_DOWN:
                if(Main.DEBUG)
                {
                    switch(DebugLoop.modoEdicao)
                    {
                        case MOVER:
                            Renderizador.posicaoCamera[1]-=10;
                        break;
                        case ROTACIONAR:
                            Renderizador.rotacaoCamera[1]-=1;
                        break;
                    }
                }
            break;

            // Debug
            case KeyEvent.VK_M:
                if(Main.DEBUG)
                {
                    if(DebugLoop.modoEdicao != EstadosEditor.MOVER)
                    {
                        DebugLoop.setModoEdicao(EstadosEditor.MOVER);
                    }
                }
            break;

            case KeyEvent.VK_R:

                if(Main.DEBUG && DebugLoop.modoEdicao != EstadosEditor.ROTACIONAR)
                {
                    DebugLoop.setModoEdicao(EstadosEditor.ROTACIONAR);
                }
                else if(GameLoop.estado == EstadosJogo.JOGANDO || GameLoop.estado == EstadosJogo.PERDEU)
                {
                    Renderizador.jogo = new GameLoop();
                    Renderizador.jogo.resetarPosicoes();
                    if(GameLoop.estado == EstadosJogo.PERDEU) GameLoop.estado = EstadosJogo.JOGANDO;
                }
            break;
            
            case KeyEvent.VK_G:

                if(Main.DEBUG && DebugLoop.modoEdicao != EstadosEditor.GRID)
                {
                    DebugLoop.modoEdicao = EstadosEditor.GRID;
                }
            break;

            // Jogo
            case KeyEvent.VK_ENTER:

                if(!(Main.DEBUG) && GameLoop.estado == EstadosJogo.INICIAL)
                {
                    Renderizador.jogo.mudarEstado(EstadosJogo.JOGANDO);
                }
            break;

            case KeyEvent.VK_P:
                if(!(Main.DEBUG))
                {
                    if(GameLoop.estado == EstadosJogo.JOGANDO) Renderizador.jogo.mudarEstado(EstadosJogo.PAUSADO);
                    else if(GameLoop.estado == EstadosJogo.PAUSADO) Renderizador.jogo.mudarEstado(EstadosJogo.JOGANDO);
                }
            break;
        }
    }

    public void keyReleased(KeyEvent e) { }

}
