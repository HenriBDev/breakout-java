package cg.projeto.Game;

import cg.projeto.Game.Estados.EstadosJogo;
import cg.projeto.UI.Tela;

public class Jogo {
    
    public EstadosJogo estado = EstadosJogo.INICIAL;
    public int vidas = 5;
    public int pontuacao = 0;
    public int fase = 1;
    public Bastao bastao = new Bastao();
    public Bola bola = new Bola();

    public Jogo(){
        this.bastao.elemento.redimensionarComponente(200, 50, 50);
        this.bola.elemento.redimensionarComponente(25);
        this.resetarPosicoes();
    }

    public void mudarEstado(EstadosJogo novoEstado){
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

    public void resetarPosicoes(){
        this.bola.pararBola();
        this.bastao.elemento.centralizarComponente(false, true, true)
            .moverComponente(this.bastao.elemento.x, Tela.yMin + Tela.margem + this.bastao.elemento.altura / 2, this.bastao.elemento.z);
        this.bola.elemento.centralizarComponente(false, true, true)
            .moverComponente(this.bola.elemento.x, this.bastao.elemento.y + this.bastao.elemento.altura / 2 + this.bola.elemento.raio, this.bola.elemento.z);
    }

}
