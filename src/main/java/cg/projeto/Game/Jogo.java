package cg.projeto.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cg.projeto.Game.Estados.EstadosBola;
import cg.projeto.Game.Estados.EstadosJogo;
import cg.projeto.UI.Menus;
import cg.projeto.UI.Tela;
import cg.projeto.UI._2D.Componentes.Texto;

public class Jogo {
    
    public static EstadosJogo estado = EstadosJogo.INICIAL;
    public static int vidas = 5;
    public static int pontuacao = 0;
    public static int fase = 1;

    // Componentes
    public static final Bastao bastao = new Bastao();
    public static final Parede teto = new Parede(),
        paredeDireita = new Parede(),
        paredeEsquerda = new Parede();
    public static final Bola bola = new Bola();
    public static final List<Obstaculo> obstaculos = new ArrayList<Obstaculo>();
    private Texto textoPontuacao, textoVidas, textoFase;

    public Jogo()
    {
        pontuacao = 0;
        textoPontuacao = new Texto("Pontuação: " + pontuacao);
        textoPontuacao.moverComponente(Tela.xMin + Tela.margem + textoPontuacao.largura/2, Tela.yMax - Tela.margem - textoPontuacao.altura/2, Tela.zMax - 2);
        vidas = 5;
        textoVidas = new Texto("Vidas: " + vidas);
        textoVidas.moverComponente(Tela.xMax - Tela.margem - textoVidas.largura/2, Tela.yMax - Tela.margem - textoVidas.altura/2, Tela.zMax - 2);
        fase = 1;
        textoFase = new Texto("Fase " + fase);
        textoFase.moverComponente(Tela.xPontoCentral, Tela.yMax - Tela.margem - textoFase.altura/2, Tela.zMax - 2);

        bastao.elemento.redimensionarComponente(200, 50, 50);
        teto.elemento.redimensionarComponente(Tela.screenWidth, 100, 100);
        paredeDireita.elemento.redimensionarComponente(100, Tela.screenHeight, 100);
        paredeEsquerda.elemento.redimensionarComponente(100, Tela.screenHeight, 100);
        resetarPosicoes();
    }

    public void desenharJogo()
    {
        // Verifica estado atual do jogo
        switch(estado){

            case INICIAL: Tela.montarMenu(Menus.INICIAL); break;

            case JOGANDO:

                textoPontuacao.conteudo = "Pontuação: " + pontuacao;
                Tela.elementosTela.add(textoPontuacao);
                
                textoVidas.conteudo = "Vidas: " + vidas;
                Tela.elementosTela.add(textoVidas);

                textoFase.conteudo = "Fase: " + fase;
                Tela.elementosTela.add(textoFase);
                
                if(bola.estado == EstadosBola.MOVENDO)
                {
                    if(bola.elemento.colidiuComComponente(bastao.elemento) && bola.elemento.y >= bastao.elemento.y)
                    {   
                        bola.aumentarVelocidade(.75f);
                        aumentarPontuacao(20);
                        if(bola.elemento.x > bastao.elemento.x){
                            bola.anguloX = bastao.elemento.largura/2 / 100 * (bola.elemento.x - bastao.elemento.x) / 100; 
                            bola.direcaoMovimentacaoX = 1;
                        }
                        if(bola.elemento.x < bastao.elemento.x){
                            bola.anguloX = bastao.elemento.largura/2 / 100 * (bastao.elemento.x - bola.elemento.x) / 100; 
                            bola.direcaoMovimentacaoX = -1;
                        }
                        if(bola.elemento.x == bastao.elemento.x){
                            bola.direcaoMovimentacaoX = new Random().nextBoolean() ? 1 : -1;
                        }
                        bola.direcaoMovimentacaoY = 1;
                    }
                    if(bola.elemento.colidiuComComponente(teto.elemento))
                    {
                        bola.inverterDirecaoMovimentacaoY();
                    }
                    if(bola.elemento.colidiuComComponente(paredeDireita.elemento) || bola.elemento.colidiuComComponente(paredeEsquerda.elemento))
                    {
                        bola.inverterDirecaoMovimentacaoX();
                    }

                    if(fase > 1)
                    {
                        int index = 0;
                        while(index < obstaculos.size())
                        {
                            Obstaculo obstaculo = obstaculos.get(index);

                            if(bola.elemento.colidiuComComponente(obstaculo.elemento))
                            {
                                float posicaoBolaXAnterior = bola.elemento.x - bola.direcaoMovimentacaoX * bola.velocidadeMovimento * bola.anguloX;
                                float posicaoBolaYAnterior = bola.elemento.y - bola.direcaoMovimentacaoY * bola.velocidadeMovimento;
                                Bola bolaAnteriora = new Bola();
                                bolaAnteriora.elemento.moverComponente(
                                    posicaoBolaXAnterior, 
                                    posicaoBolaYAnterior,  
                                    bola.elemento.z
                                );
                                if(!bolaAnteriora.elemento.colidiuComComponenteVerticalmente(obstaculo.elemento))
                                {
                                    bola.inverterDirecaoMovimentacaoY();
                                }
                                if(!bolaAnteriora.elemento.colidiuComComponenteHorizontalmente(obstaculo.elemento))
                                {
                                    bola.inverterDirecaoMovimentacaoX();
                                }

                                aumentarPontuacao(25);
                                obstaculos.remove(index);
                            }
                            else index++;
                        }
                    }

                    float novaPosicaoBolaX = bola.elemento.x + bola.direcaoMovimentacaoX * bola.velocidadeMovimento * bola.anguloX;
                    float novaPosicaoBolaY = bola.elemento.y + bola.direcaoMovimentacaoY * bola.velocidadeMovimento;
                    
                    if(novaPosicaoBolaY < bastao.elemento.y || (fase == 1 && pontuacao >= 200) || (fase == 2 && obstaculos.isEmpty()))
                    {
                        resetarPosicoes();
                        if(novaPosicaoBolaY < bastao.elemento.y) vidas--;
                        if((fase == 1 && pontuacao >= 200) || (fase == 2 && obstaculos.isEmpty())) trocarFase(fase + 1);
                    }
                    else bola.elemento.moverComponente(
                        novaPosicaoBolaX, 
                        novaPosicaoBolaY,  
                        bola.elemento.z
                    );
                }

                Tela.elementosTela.add(bastao.elemento);
                Tela.elementosTela.add(bola.elemento);
                Tela.elementosTela.add(teto.elemento);
                Tela.elementosTela.add(paredeDireita.elemento);
                Tela.elementosTela.add(paredeEsquerda.elemento);
                for(int index = obstaculos.size() - 1; index >= 0; index--)
                {    
                    Tela.elementosTela.add(obstaculos.get(index).elemento);
                }
                if(vidas == 0) estado = EstadosJogo.PERDEU;

            break;

            case PERDEU:

                Tela.elementosTela.add(textoVidas);
                Tela.elementosTela.add(textoPontuacao);
                Tela.elementosTela.add(bastao.elemento);
                Tela.elementosTela.add(bola.elemento);
                Tela.montarMenu(Menus.PERDEU);

            break;

            case PAUSADO:

                Tela.elementosTela.add(textoVidas);
                Tela.elementosTela.add(textoPontuacao);
                Tela.elementosTela.add(bastao.elemento);
                Tela.elementosTela.add(bola.elemento);
                Tela.montarMenu(Menus.PAUSADO);

            break;
        }
    }

    public void mudarEstado(EstadosJogo novoEstado){
        estado = novoEstado;
    }

    public void diminuirVida(){
        vidas--;
    }

    public void aumentarPontuacao(int valorAdicional){
        pontuacao += valorAdicional;
    }

    public void trocarFase(int novaFase){
        fase = novaFase;
        obstaculos.clear();
        bola.velocidadeMovimento = 10;
        switch(fase)
        {
            case 2:

                int qtdColunas = 8,
                    qtdLinhas = 4;
                    
                for(int contHorizontal = 0; contHorizontal < qtdColunas; contHorizontal++)
                {
                    for(int contVertical = 0; contVertical < qtdLinhas; contVertical++)
                    {
                        float larguraObstaculo = 75,
                            alturaObstaculo = 25,
                            espacamentoObstaculos = 10;
                        Obstaculo novoObstaculo = new Obstaculo();
                        novoObstaculo.elemento
                            .redimensionarComponente(larguraObstaculo, alturaObstaculo, 10)
                            .moverComponente(
                                Tela.xPontoCentral - (larguraObstaculo * qtdColunas / 2) - (espacamentoObstaculos * qtdColunas / 2) + (larguraObstaculo * contHorizontal) + (espacamentoObstaculos * contHorizontal) + (larguraObstaculo/2),
                                Tela.yPontoCentral + (alturaObstaculo * qtdLinhas / 2) + (espacamentoObstaculos * qtdLinhas / 2) + 200 - (alturaObstaculo * contVertical) - (espacamentoObstaculos * contVertical) - (alturaObstaculo/2),
                                novoObstaculo.elemento.z
                            );
                        obstaculos.add(novoObstaculo);
                        Tela.elementosTela.add(novoObstaculo.elemento);
                    }
                }
            break;
        }
        resetarPosicoes();
    }

    public void resetarPosicoes(){
        bola.pararBola();
        bastao.elemento.centralizarComponente(false, true, true)
            .moverComponente(bastao.elemento.x, Tela.yMin + Tela.margem + bastao.elemento.altura / 2, bastao.elemento.z);
        bola.elemento.centralizarComponente(false, true, true)
            .moverComponente(bola.elemento.x, bastao.elemento.y + bastao.elemento.altura / 2 + bola.elemento.raio + 1, bola.elemento.z);
        paredeDireita.elemento.centralizarComponente(true, false, true)
            .moverComponente(Tela.xMax + paredeDireita.elemento.largura/2, paredeDireita.elemento.y, paredeDireita.elemento.z);
        paredeEsquerda.elemento.centralizarComponente(true, false, true)
            .moverComponente(Tela.xMin - paredeEsquerda.elemento.largura/2, paredeEsquerda.elemento.y, paredeEsquerda.elemento.z);
        teto.elemento.centralizarComponente(false, true, true)
            .moverComponente(teto.elemento.x, Tela.yMax + teto.elemento.altura/2, teto.elemento.z);
    }

}
