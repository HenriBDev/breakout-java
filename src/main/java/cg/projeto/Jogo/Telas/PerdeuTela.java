package cg.projeto.Jogo.Telas;

import cg.projeto.Motor.Resolucao;
import cg.projeto.Motor.Componentes._2D.QuadrilateroComponente;
import cg.projeto.Motor.Componentes._2D.TextoComponente;

public class PerdeuTela extends BaseTela {
    
    TextoComponente textoPerdeu;

    QuadrilateroComponente telaTransparente = new QuadrilateroComponente()
        .redimensionarComponente(Resolucao.SRUxMax - Resolucao.SRUxMin, Resolucao.SRUyMax - Resolucao.SRUyMin)
        .trocarCor(0, 0, 0, 1)
        .moverComponente(Resolucao.SRUxCentral, Resolucao.SRUyCentral, Resolucao.SRUzMax - 1);

    public PerdeuTela(){
        textoPerdeu = new TextoComponente("Você perdeu, pressione R para recomeçar")
            .moverComponente(Resolucao.SRUxCentral, Resolucao.SRUyCentral, Resolucao.SRUzMax);
        componentes.add(textoPerdeu);
        componentes.add(telaTransparente);
    }

}
