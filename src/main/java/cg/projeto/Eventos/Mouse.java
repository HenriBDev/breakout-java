package cg.projeto.Eventos;

import java.util.Random;

import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;

import cg.projeto.Main;
import cg.projeto.Debug.ModosEdicao;
import cg.projeto.Jogo.GameLoop;
import cg.projeto.Jogo.Estados.EstadosBola;
import cg.projeto.Jogo.Estados.EstadosJogo;
import cg.projeto.Motor.Tela;
import cg.projeto.Motor.Componentes._3D.Hexaedro;

public class Mouse implements MouseListener{

    public Mouse(){}

    public void mouseExited(MouseEvent e){}

    public void mouseDragged(MouseEvent e){
        
    }

    public void mouseReleased(MouseEvent e){}

    public void mouseClicked(MouseEvent e)
    {
        System.err.println("Mouse clicked: (" + e.getX() + ", " + e.getY() + ")");
        if(!(Main.DEBUG) && GameLoop.estado == EstadosJogo.JOGANDO && GameLoop.bola.estado == EstadosBola.PARADA)
        {
            GameLoop.bola.direcaoMovimentacaoX = new Random().nextBoolean() ? 1 : -1;
            GameLoop.bola.direcaoMovimentacaoY = 1;
            GameLoop.bola.estado = EstadosBola.MOVENDO;
        }
        else if (Main.DEBUG && Tela.modoEdicao == ModosEdicao.GRID)
        {
            float xMouse = e.getX() - Tela.screenWidth/2, yMouse = Tela.screenHeight/2 - e.getY();
            for(int coluna = 0; coluna < Tela.gridBlocos.qtdBlocosHorizontal; coluna++)
            {
                for(int linha = 0; linha < Tela.gridBlocos.qtdBlocosVertical; linha++)
                {
                    Hexaedro celula = Tela.gridBlocos.blocos.get(coluna).get(linha).elemento;
                    float pontaCimaCelula = celula.y + celula.altura/2;
                    float pontaBaixoCelula = celula.y - celula.altura/2;
                    float pontaDireitaCelula = celula.x + celula.largura/2;
                    float pontaEsquerdaCelula = celula.x - celula.largura/2;
                    if(
                        xMouse >= pontaEsquerdaCelula && 
                        xMouse <= pontaDireitaCelula && 
                        yMouse >= pontaBaixoCelula && 
                        yMouse <= pontaCimaCelula)
                    {
                        celula.preencherComponente(!celula.preencher);
                    }
                }
            }
        }
    }

    public void mousePressed(MouseEvent e){}

    public void mouseEntered(MouseEvent e){}

    public void mouseMoved(MouseEvent e){

        if(!(Main.DEBUG) && GameLoop.estado == EstadosJogo.JOGANDO && GameLoop.bola.estado == EstadosBola.MOVENDO){
            float distanciaBolaBastaoX = GameLoop.bastao.elemento.x - GameLoop.bola.elemento.x;
            float posicaoBastaoX;
            if(e.getX() - Tela.screenWidth/2 + GameLoop.bastao.elemento.largura/2 >= Tela.screenWidth/2){
                posicaoBastaoX = Tela.screenWidth/2 - GameLoop.bastao.elemento.largura/2;
            }
            else if(e.getX() - Tela.screenWidth/2 - GameLoop.bastao.elemento.largura/2 <= Tela.screenWidth/2 * -1){
                posicaoBastaoX = Tela.screenWidth/2 * -1 + GameLoop.bastao.elemento.largura/2;
            }
            else{
                posicaoBastaoX = e.getX() - Tela.screenWidth/2;
            }
            GameLoop.bastao.elemento.moverComponente(posicaoBastaoX, GameLoop.bastao.elemento.y, GameLoop.bastao.elemento.z);
            if(GameLoop.bola.estado == EstadosBola.PARADA){
                GameLoop.bola.elemento.moverComponente(posicaoBastaoX + distanciaBolaBastaoX, GameLoop.bola.elemento.y, GameLoop.bola.elemento.z);
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
