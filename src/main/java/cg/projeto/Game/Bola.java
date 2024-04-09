package cg.projeto.Game;

import cg.projeto.Game.Estados.EstadosBola;
import cg.projeto.UI._3D.Componentes.Esfera;

public class Bola {
    
    public EstadosBola estado = EstadosBola.PARADA;
    public Esfera elemento = new Esfera();
    public float velocidadeMovimento = 10;
    public int direcaoMovimentacaoX = 0; // 1 = Direita, -1 = Esquerda
    public int direcaoMovimentacaoY = 0; // 1 = Cima, -1 = Baixo

    public void mudarEstado(EstadosBola novoEstado){
        this.estado = novoEstado;
    }

}
