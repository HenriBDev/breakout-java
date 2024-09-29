package breakout.java.Jogo.Objetos._2D;

import breakout.java.Jogo.Estados.EstadosBola;
import breakout.java.Motor.Componentes._3D.EsferaComponente;

public class BolaObjeto {
    
    public EstadosBola estado = EstadosBola.PARADA;
    public EsferaComponente componente = new EsferaComponente();
    public float velocidadeMovimento = 10,
        anguloX = 1;
    public int direcaoMovimentacaoX = 0; // 1 = Direita, -1 = Esquerda
    public int direcaoMovimentacaoY = 0; // 1 = Cima, -1 = Baixo

    public BolaObjeto()
    {
        componente.redimensionarComponente(25, 25, 25);
    }

    public void mudarEstado(EstadosBola novoEstado)
    {
        this.estado = novoEstado;
    }

    public void aumentarVelocidade(float aumento)
    {
        this.velocidadeMovimento += aumento;
    }

    public void inverterDirecaoMovimentacaoX()
    {
        this.direcaoMovimentacaoX *= -1;
    }
    
    public void inverterDirecaoMovimentacaoY()
    {
        this.direcaoMovimentacaoY *= -1;
    }

    public void pararBola()
    {
        this.direcaoMovimentacaoX = 0;
        this.direcaoMovimentacaoY = 0;
        this.velocidadeMovimento = 10;
        this.anguloX = 1;
        this.estado = EstadosBola.PARADA;
    }

    public void iniciarMovimentoBolaX(boolean moverParaDireita)
    {
        this.direcaoMovimentacaoX = moverParaDireita ? 1 : -1;
    }
    
    public void iniciarMovimentoBolaY(boolean moverParaCima)
    {
        this.direcaoMovimentacaoY = moverParaCima ? 1 : -1;
    }

}
