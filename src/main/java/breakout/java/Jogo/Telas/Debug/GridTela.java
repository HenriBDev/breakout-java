package breakout.java.Jogo.Telas.Debug;

import breakout.java.Resolucao;
import breakout.java.Jogo.Objetos._2D.BastaoObjeto;
import breakout.java.Jogo.Objetos._2D.BolaObjeto;
import breakout.java.Jogo.Objetos._2D.Obstaculos.AglomeradoBlocosObjeto;
import breakout.java.Jogo.Objetos._2D.Obstaculos.BlocoObjeto;
import breakout.java.Jogo.Telas.BaseTela;
import breakout.java.Motor.Componentes._2D.LabelComponente;
import breakout.java.Motor.Componentes._2D.QuadrilateroComponente;
import breakout.java.Motor.Componentes._3D.EsferaComponente;
import breakout.java.Motor.Componentes._3D.HexaedroComponente;

public class GridTela extends BaseTela {

    public float margemTela = 25;
    
    HexaedroComponente bastaoModelo = new BastaoObjeto().componente
        .centralizarComponente(false, true, true);
        
    EsferaComponente bolaModelo = new BolaObjeto().componente
        .centralizarComponente(false, true, true);

    public QuadrilateroComponente fundoGrid = new QuadrilateroComponente();
    public AglomeradoBlocosObjeto gridBlocos = new AglomeradoBlocosObjeto(14, 10, 10);
    public LabelComponente botaoGerarNivel;

    public GridTela()
    {
        bastaoModelo.moverComponente(bastaoModelo.x, Resolucao.SRUyMin + margemTela + bastaoModelo.altura / 2, bastaoModelo.z);
        componentes.add(bastaoModelo);

        bolaModelo.moverComponente(bolaModelo.x, bastaoModelo.y + bastaoModelo.altura / 2 + bolaModelo.altura + 1, bolaModelo.z);
        componentes.add(bolaModelo);

        // Tira preenchimento dos blocos no grid de debug
        for(int coluna = 0; coluna < gridBlocos.qtdBlocosHorizontal; coluna++)
        {
            for(int linha = 0; linha < gridBlocos.qtdBlocosVertical; linha++)
            {
                gridBlocos.blocos.get(coluna).get(linha).componente.preencherComponente(false);
            }
        }

        fundoGrid.redimensionarComponente(
            BlocoObjeto.larguraBloco * gridBlocos.qtdBlocosHorizontal + gridBlocos.espacamento * (gridBlocos.qtdBlocosHorizontal-1), 
            BlocoObjeto.alturaBloco * gridBlocos.qtdBlocosVertical + gridBlocos.espacamento * (gridBlocos.qtdBlocosVertical-1)
        ).moverComponente(
            Resolucao.SRUxCentral, 
            Resolucao.SRUyMax - margemTela - 100 - fundoGrid.altura/2, 
            Resolucao.SRUzCentral
        );
        componentes.add(fundoGrid);

        botaoGerarNivel = new LabelComponente("Gerar nível")
            .moverComponente(fundoGrid.x, fundoGrid.y - fundoGrid.altura/2 - 50, fundoGrid.z)
            .alterarEspessuraPreenchimento(20)
            .alterarEspessuraBorda(5);
        botaoGerarNivel.bordaHabilitada = true;
        componentes.add(botaoGerarNivel);

        // Desenha linhas do grid
        gridBlocos.moverAglomerado(fundoGrid.x, fundoGrid.y, fundoGrid.z);
        for(int coluna = 0; coluna < gridBlocos.qtdBlocosHorizontal; coluna++)
        {
            for(int linha = 0; linha < gridBlocos.qtdBlocosVertical; linha++)
            {
                componentes.add(gridBlocos.blocos.get(coluna).get(linha).componente);
            }
        }
    }

}
