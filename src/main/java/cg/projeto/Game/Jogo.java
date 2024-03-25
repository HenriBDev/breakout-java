package cg.projeto.Game;

public class Jogo {
    
    public Estado estado;
    public int vidas;
    public int pontuacao;
    public int fase;

    public Jogo(){
        this.estado = Estado.INICIAL;
        this.vidas = 5;
        this.pontuacao = 0;
        this.fase = 1;
    }

    public void mudarEstado(Estado novoEstado){
        this.estado = novoEstado;
    }

    public void diminuirVida(){
        this.vidas--;
    }

    public void aumentarPontuacao(int valorAdicional){
        this.pontuacao += valorAdicional;
    }

    public void trocarFase(int novaFase){
        this.fase = novaFase;
    }

}
