package cg.projeto.Jogo;

import java.util.Random;

import cg.projeto.Jogo.Estados.EstadosBola;
import cg.projeto.Jogo.Estados.EstadosJogo;
import cg.projeto.Jogo.Objetos._2D.Obstaculos.AglomeradoBlocos;
import cg.projeto.Jogo.Objetos._2D.Bastao;
import cg.projeto.Jogo.Objetos._2D.Bola;
import cg.projeto.Jogo.Objetos._2D.Parede;
import cg.projeto.Jogo.Objetos._2D.Obstaculos.Bloco;
import cg.projeto.Motor.Menus;
import cg.projeto.Motor.Tela;
import cg.projeto.Motor.Componentes._2D.Texto;

public class GameLoop {
    
    public static EstadosJogo estado = EstadosJogo.INICIAL;
    public static int vidas = 5;
    public static int pontuacao = 0;
    public static int fase = 1;
    public static int obstaculosRestantes;

    // Componentes
    public static final Bastao bastao = new Bastao();
    public static final Parede teto = new Parede(),
        paredeDireita = new Parede(),
        paredeEsquerda = new Parede();
    public static final Bola bola = new Bola();
    private Texto textoPontuacao, textoVidas, textoFase;
    public AglomeradoBlocos obstaculos;

    public GameLoop()
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

        teto.componente.redimensionarComponente(Tela.screenWidth, 100, 100);
        paredeDireita.componente.redimensionarComponente(100, Tela.screenHeight, 100);
        paredeEsquerda.componente.redimensionarComponente(100, Tela.screenHeight, 100);
        
        paredeDireita.componente
            .centralizarComponente(true, false, true)
            .moverComponente(Tela.xMax + paredeDireita.componente.largura/2, paredeDireita.componente.y, paredeDireita.componente.z);
        paredeEsquerda.componente
            .centralizarComponente(true, false, true)
            .moverComponente(Tela.xMin - paredeEsquerda.componente.largura/2, paredeEsquerda.componente.y, paredeEsquerda.componente.z);
        teto.componente
            .centralizarComponente(false, true, true)
            .moverComponente(teto.componente.x, Tela.yMax + teto.componente.altura/2, teto.componente.z);
        
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
                    if(bola.componente.colidiuComComponente(bastao.componente) && bola.componente.y >= bastao.componente.y)
                    {   
                        bola.aumentarVelocidade(.75f);
                        aumentarPontuacao(20);
                        if(bola.componente.x > bastao.componente.x){
                            bola.anguloX = bastao.componente.largura/2 / 100 * (bola.componente.x - bastao.componente.x) / 100; 
                            bola.direcaoMovimentacaoX = 1;
                        }
                        if(bola.componente.x < bastao.componente.x){
                            bola.anguloX = bastao.componente.largura/2 / 100 * (bastao.componente.x - bola.componente.x) / 100; 
                            bola.direcaoMovimentacaoX = -1;
                        }
                        if(bola.componente.x == bastao.componente.x){
                            bola.direcaoMovimentacaoX = new Random().nextBoolean() ? 1 : -1;
                        }
                        bola.direcaoMovimentacaoY = 1;
                    }
                    if(bola.componente.colidiuComComponente(teto.componente))
                    {
                        bola.inverterDirecaoMovimentacaoY();
                    }
                    if(bola.componente.colidiuComComponente(paredeDireita.componente) || bola.componente.colidiuComComponente(paredeEsquerda.componente))
                    {
                        bola.inverterDirecaoMovimentacaoX();
                    }

                    if(fase > 1)
                    {
                        for(int coluna = 0; coluna < obstaculos.qtdBlocosHorizontal; coluna++)
                        {
                            for(int linha = 0; linha < obstaculos.blocos.get(coluna).size(); linha++)
                            {
                                Bloco obstaculo = obstaculos.blocos.get(coluna).get(linha);

                                if(bola.componente.colidiuComComponente(obstaculo.componente))
                                {
                                    float posicaoBolaXAnterior = bola.componente.x - bola.direcaoMovimentacaoX * bola.velocidadeMovimento * bola.anguloX;
                                    float posicaoBolaYAnterior = bola.componente.y - bola.direcaoMovimentacaoY * bola.velocidadeMovimento;
                                    Bola bolaAnteriora = new Bola();
                                    bolaAnteriora.componente.moverComponente(
                                        posicaoBolaXAnterior, 
                                        posicaoBolaYAnterior,  
                                        bola.componente.z
                                    );
                                    if(!bolaAnteriora.componente.colidiuComComponenteVerticalmente(obstaculo.componente))
                                    {
                                        bola.inverterDirecaoMovimentacaoY();
                                    }
                                    if(!bolaAnteriora.componente.colidiuComComponenteHorizontalmente(obstaculo.componente))
                                    {
                                        bola.inverterDirecaoMovimentacaoX();
                                    }

                                    aumentarPontuacao(25);
                                    obstaculos.blocos.get(coluna).remove(obstaculo);
                                    obstaculosRestantes--;
                                }
                            }
                        }
                    }

                    float novaPosicaoBolaX = bola.componente.x + bola.direcaoMovimentacaoX * bola.velocidadeMovimento * bola.anguloX;
                    float novaPosicaoBolaY = bola.componente.y + bola.direcaoMovimentacaoY * bola.velocidadeMovimento;
                    
                    if(novaPosicaoBolaY < bastao.componente.y || (fase == 1 && pontuacao >= 200) || (fase == 2 && obstaculosRestantes == 0))
                    {
                        resetarPosicoes();
                        if(novaPosicaoBolaY < bastao.componente.y) vidas--;
                        if((fase == 1 && pontuacao >= 200) || (fase == 2 && obstaculosRestantes == 0)) trocarFase(fase + 1);
                    }
                    else bola.componente.moverComponente(
                        novaPosicaoBolaX, 
                        novaPosicaoBolaY,  
                        bola.componente.z
                    );
                }

                Tela.elementosTela.add(bastao.componente);
                Tela.elementosTela.add(bola.componente);
                Tela.elementosTela.add(teto.componente);
                Tela.elementosTela.add(paredeDireita.componente);
                Tela.elementosTela.add(paredeEsquerda.componente);
                if(fase > 1)
                {
                    for(int coluna = 0; coluna < obstaculos.qtdBlocosHorizontal; coluna++)
                    {
                        for(int linha = 0; linha < obstaculos.blocos.get(coluna).size(); linha++)
                        {
                            Tela.elementosTela.add(obstaculos.blocos.get(coluna).get(linha).componente);
                        }
                    }
                }
                if(vidas == 0) estado = EstadosJogo.PERDEU;

            break;

            case PERDEU:

                Tela.elementosTela.add(textoVidas);
                Tela.elementosTela.add(textoPontuacao);
                Tela.elementosTela.add(bastao.componente);
                Tela.elementosTela.add(bola.componente);
                Tela.montarMenu(Menus.PERDEU);

            break;

            case PAUSADO:

                Tela.elementosTela.add(textoVidas);
                Tela.elementosTela.add(textoPontuacao);
                Tela.elementosTela.add(bastao.componente);
                Tela.elementosTela.add(bola.componente);
                Tela.montarMenu(Menus.PAUSADO);

            break;
        }
    }

    public void mudarEstado(EstadosJogo novoEstado){ estado = novoEstado; }

    public void diminuirVida(){ vidas--; }

    public void aumentarPontuacao(int valorAdicional){ pontuacao += valorAdicional; }

    public void trocarFase(int novaFase)
    {
        fase = novaFase;
        bola.velocidadeMovimento = 10;
        switch(fase)
        {
            case 2:

                obstaculosRestantes = 8 * 4;
                obstaculos = new AglomeradoBlocos(8, 4, 10);
                obstaculos.moverAglomerado(Tela.xPontoCentral, Tela.yMax - Tela.margem - 150, obstaculos.z);

                for(int coluna = 0; coluna < obstaculos.qtdBlocosHorizontal; coluna++)
                {
                    for(int linha = 0; linha < obstaculos.qtdBlocosVertical; linha++)
                    {
                        Tela.elementosTela.add(obstaculos.blocos.get(coluna).get(linha).componente);
                    }
                }
            break;
        }
        resetarPosicoes();
    }

    public void resetarPosicoes()
    {
        bola.pararBola();
        bastao.componente.centralizarComponente(false, true, true)
            .moverComponente(bastao.componente.x, Tela.yMin + Tela.margem + bastao.componente.altura / 2, bastao.componente.z);
        bola.componente.centralizarComponente(false, true, true)
            .moverComponente(bola.componente.x, bastao.componente.y + bastao.componente.altura / 2 + bola.componente.raio + 1, bola.componente.z);
    }

}
