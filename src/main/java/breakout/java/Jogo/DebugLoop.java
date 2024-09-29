package breakout.java.Jogo;

import java.util.ArrayList;
import java.util.List;

import breakout.java.Jogo.Estados.Debug.EstadosEditor;
import breakout.java.Jogo.Telas.Debug.EdicaoTela;
import breakout.java.Jogo.Telas.Debug.GridTela;
import breakout.java.Motor.Componentes.BaseComponente;

public class DebugLoop {

    public static EdicaoTela telaEdicao;
    public static GridTela telaGrid;

    public static EstadosEditor modoEdicao = EstadosEditor.MOVER;
    
    public DebugLoop()
    {
        telaEdicao = new EdicaoTela();
        telaGrid = new GridTela();
    }

    public static void setModoEdicao(EstadosEditor novoModo){
        modoEdicao = novoModo;
        telaEdicao.setTextoModoEdicao(novoModo);
    }

    public List<BaseComponente> desenharDebug(){

        switch(modoEdicao)
        {
            case GRID:

                return telaGrid.componentes;

            case MOVER:
            case ROTACIONAR:

                if(telaEdicao.anguloHexaedroDebug < 360) 
                    telaEdicao.setHexaedroAngulo(telaEdicao.anguloHexaedroDebug+1);
                else telaEdicao.setHexaedroAngulo(0);

                return telaEdicao.componentes;

        }

        return montarTela();
    }

    public List<BaseComponente> montarTela()
    {
        List<BaseComponente> componentes = new ArrayList<BaseComponente>();
        return componentes;
    }

}
