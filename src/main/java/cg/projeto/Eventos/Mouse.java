package cg.projeto.Eventos;

import java.util.Random;

import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;

import cg.projeto.Main;
import cg.projeto.Jogo.DebugLoop;
import cg.projeto.Jogo.GameLoop;
import cg.projeto.Jogo.Estados.EstadosBola;
import cg.projeto.Jogo.Estados.EstadosJogo;
import cg.projeto.Jogo.Estados.Debug.EstadosEditor;
import cg.projeto.Motor.Renderizador;
import cg.projeto.Motor.Resolucao;
import cg.projeto.Motor.Componentes.BaseComponente;
import cg.projeto.Motor.Componentes._2D.QuadrilateroComponente;
import cg.projeto.Motor.Componentes._3D.HexaedroComponente;

public class Mouse implements MouseListener{

    public BaseComponente componente = new QuadrilateroComponente().redimensionarComponente(1, 1);

    public Mouse(){}

    public void mouseExited(MouseEvent e){}

    public void mouseDragged(MouseEvent e){}

    public void mouseReleased(MouseEvent e){}

    public void mouseClicked(MouseEvent e)
    {
        System.err.println("Mouse clicked: (" + e.getX() + ", " + e.getY() + ")");

        if(e.getX() - Resolucao.larguraTela/2 != componente.x || Resolucao.alturaTela/2 - e.getY() != componente.y)
        {
            componente.moverComponente(e.getX() - Resolucao.larguraTela/2, Resolucao.alturaTela/2 - e.getY(), 0);
        }

        if(Main.DEBUG)
        {
            if(DebugLoop.modoEdicao == EstadosEditor.GRID)
            {
                for(int coluna = 0; coluna < DebugLoop.telaGrid.gridBlocos.qtdBlocosHorizontal; coluna++)
                {
                    for(int linha = 0; linha < DebugLoop.telaGrid.gridBlocos.qtdBlocosVertical; linha++)
                    {
                        HexaedroComponente celula = DebugLoop.telaGrid.gridBlocos.blocos.get(coluna).get(linha).componente;
                        if(celula.colidiuComComponente(componente)) celula.preencherComponente(!celula.preencher);
                    }
                }
            }
        }
        else if(GameLoop.estado == EstadosJogo.JOGANDO && GameLoop.bola.estado == EstadosBola.PARADA)
        {
            GameLoop.bola.direcaoMovimentacaoX = new Random().nextBoolean() ? 1 : -1;
            GameLoop.bola.direcaoMovimentacaoY = 1;
            GameLoop.bola.estado = EstadosBola.MOVENDO;
        }
    }

    public void mousePressed(MouseEvent e){}

    public void mouseEntered(MouseEvent e){}

    public void mouseMoved(MouseEvent e)
    {
        componente.moverComponente(e.getX() - Resolucao.larguraTela/2, Resolucao.alturaTela/2 - e.getY(), 0);

        if(!(Main.DEBUG) && GameLoop.estado == EstadosJogo.JOGANDO && GameLoop.bola.estado == EstadosBola.MOVENDO)
        {
            float distanciaBolaBastaoX = GameLoop.bastao.componente.x - GameLoop.bola.componente.x;
            float posicaoBastaoX;
            if(componente.x + GameLoop.bastao.componente.largura/2 >= Resolucao.larguraTela/2)
            {
                posicaoBastaoX = Resolucao.larguraTela/2 - GameLoop.bastao.componente.largura/2;
            }
            else if(componente.x  - GameLoop.bastao.componente.largura/2 <= Resolucao.larguraTela/2 * -1)
            {
                posicaoBastaoX = Resolucao.larguraTela/2 * -1 + GameLoop.bastao.componente.largura/2;
            }
            else
            {
                posicaoBastaoX = componente.x ;
            }
            GameLoop.bastao.componente.moverComponente(posicaoBastaoX, GameLoop.bastao.componente.y, GameLoop.bastao.componente.z);
            if(GameLoop.bola.estado == EstadosBola.PARADA)
            {
                GameLoop.bola.componente.moverComponente(posicaoBastaoX + distanciaBolaBastaoX, GameLoop.bola.componente.y, GameLoop.bola.componente.z);
            }
        }
    }

    public void mouseWheelMoved(MouseEvent e)
    {
        System.out.println("Mouse wheel moved: " + (e.getRotation()[1] == 1 ? "forward" : "backwards"));

        if(Main.DEBUG)
        {
            if(e.getRotation()[1] == 1) Renderizador.escalaCamera += .1f;
            else if (e.getRotation()[1] == -1 && Renderizador.escalaCamera - .25f > 0) Renderizador.escalaCamera -= .1f;
        }

    }
    
}
