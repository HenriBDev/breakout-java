package cg.projeto.Jogo.Objetos._2D.Obstaculos;

import java.util.ArrayList;
import java.util.List;

public class AglomeradoBlocos 
{
    public float 
        qtdBlocosHorizontal, 
        qtdBlocosVertical, 
        espacamento,
        x = 0,
        y = 0,
        z = 0;
    public List<ArrayList<Bloco>> blocos = new ArrayList<ArrayList<Bloco>>();

    public AglomeradoBlocos(int qtdBlocosHorizontal, int qtdBlocosVertical, float espacamento)
    {
        this.qtdBlocosHorizontal = qtdBlocosHorizontal;
        this.qtdBlocosVertical = qtdBlocosVertical;
        this.espacamento = espacamento;
        for(int coluna = 0; coluna < qtdBlocosHorizontal; coluna++)
        {
            blocos.add(new ArrayList<Bloco>());
            for(int linha = 0; linha < qtdBlocosVertical; linha++)
            {
                Bloco bloco = new Bloco();
                bloco.componente.moverComponente(
                    x - (bloco.componente.largura * qtdBlocosHorizontal / 2) - (espacamento * qtdBlocosHorizontal / 2) + (bloco.componente.largura * coluna) + (espacamento * coluna) + (bloco.componente.largura/2),
                    y + (bloco.componente.altura * qtdBlocosVertical / 2) + (espacamento * qtdBlocosVertical / 2) - (bloco.componente.altura * linha) - (espacamento * linha) - (bloco.componente.altura/2),
                    z
                );
                blocos.get(coluna).add(bloco);
            }
        }
    }

    public AglomeradoBlocos moverAglomerado(float x, float y, float z)
    {
        for(int coluna = 0; coluna < qtdBlocosHorizontal; coluna++)
        {
            for(int linha = 0; linha < qtdBlocosVertical; linha++)
            {
                Bloco bloco = blocos.get(coluna).get(linha);
                bloco.componente.moverComponente(
                    x - (bloco.componente.largura * qtdBlocosHorizontal / 2) - (espacamento * qtdBlocosHorizontal / 2) + (bloco.componente.largura * coluna) + (espacamento * coluna) + (bloco.componente.largura/2) + (espacamento/2),
                    y + (bloco.componente.altura * qtdBlocosVertical / 2) + (espacamento * qtdBlocosVertical / 2) - (bloco.componente.altura * linha) - (espacamento * linha) - (bloco.componente.altura/2) - (espacamento/2),
                    z
                );
            }
        }
        return this;
    }
}