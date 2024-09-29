package breakout.java.Jogo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import breakout.java.Resolucao;
import breakout.java.Jogo.Estados.EstadosBola;
import breakout.java.Jogo.Estados.EstadosJogo;
import breakout.java.Jogo.Objetos._2D.Obstaculos.AglomeradoBlocosObjeto;
import breakout.java.Jogo.Objetos._2D.BastaoObjeto;
import breakout.java.Jogo.Objetos._2D.BolaObjeto;
import breakout.java.Jogo.Objetos._2D.ParedeObjeto;
import breakout.java.Jogo.Objetos._2D.Obstaculos.BlocoObjeto;
import breakout.java.Jogo.Telas.InicialTela;
import breakout.java.Jogo.Telas.PausaTela;
import breakout.java.Jogo.Telas.PerdeuTela;
import breakout.java.Motor.Componentes.BaseComponente;
import breakout.java.Motor.Componentes._2D.CoracaoComponente;
import breakout.java.Motor.Componentes._2D.TextoComponente;

public class GameLoop {
    
    public static EstadosJogo estado = EstadosJogo.INICIAL;
    public static final int TOTAL_VIDAS = 5;
    public static int vidas = TOTAL_VIDAS;
    public static int pontuacao = 0;
    public static int fase = 1;
    public static int obstaculosRestantes;
    public static float 
        margemTela = 25,
        larguraCoracoes = 20,
        espacamentoCoracoes = 10;

    // Telas
    public static InicialTela telaInicial;
    public static PausaTela telaPausa;
    public static PerdeuTela telaPerdeu;

    // Componentes
    public static final BastaoObjeto bastao = new BastaoObjeto();
    public static final ParedeObjeto teto = new ParedeObjeto(),
        paredeDireita = new ParedeObjeto(),
        paredeEsquerda = new ParedeObjeto();
    public static final BolaObjeto bola = new BolaObjeto();
    private TextoComponente textoPontuacao, textoVidas, textoFase;
    public AglomeradoBlocosObjeto obstaculos;
    public List<CoracaoComponente> coracoesVida = new ArrayList<CoracaoComponente>(TOTAL_VIDAS);

    public GameLoop()
    {
        telaInicial = new InicialTela();
        telaPausa = new PausaTela();
        telaPerdeu = new PerdeuTela();

        pontuacao = 0;
        textoPontuacao = new TextoComponente("Pontuação: " + pontuacao);
        textoPontuacao.moverComponente(Resolucao.SRUxMin + margemTela + textoPontuacao.largura/2, Resolucao.SRUyMax - margemTela - textoPontuacao.altura/2, Resolucao.SRUzMax - 2);
        
        vidas = TOTAL_VIDAS;
        for(int i = 0; i < TOTAL_VIDAS; i++)
        {
            coracoesVida.add(new CoracaoComponente()
                .redimensionarComponente(larguraCoracoes, larguraCoracoes)
                .trocarCor(1, 0, 0, 1)
                .moverComponente(
                    Resolucao.SRUxMax - margemTela - (i * larguraCoracoes * 2) - (i * espacamentoCoracoes) - larguraCoracoes/2, 
                    Resolucao.SRUyMax - margemTela - larguraCoracoes/2, 
                    Resolucao.SRUzMax - 2
                )
            );
        }
        textoVidas = new TextoComponente("Vidas:");
        textoVidas.moverComponente(
            Resolucao.SRUxMax - margemTela - (TOTAL_VIDAS * larguraCoracoes * 2) - (TOTAL_VIDAS * espacamentoCoracoes) - textoVidas.largura/2, 
            Resolucao.SRUyMax - margemTela - textoVidas.altura/2, 
            Resolucao.SRUzMax - 2
        );
        
        fase = 1;
        textoFase = new TextoComponente("Fase " + fase);
        textoFase.moverComponente(Resolucao.SRUxCentral, Resolucao.SRUyMax - margemTela - textoFase.altura/2, Resolucao.SRUzMax - 2);

        teto.componente.redimensionarComponente(Resolucao.larguraTela, 100, 100);
        paredeDireita.componente.redimensionarComponente(100, Resolucao.alturaTela, 100);
        paredeEsquerda.componente.redimensionarComponente(100, Resolucao.alturaTela, 100);
        
        paredeDireita.componente
            .centralizarComponente(true, false, true)
            .moverComponente(Resolucao.SRUxMax + paredeDireita.componente.largura/2, paredeDireita.componente.y, paredeDireita.componente.z);
        paredeEsquerda.componente
            .centralizarComponente(true, false, true)
            .moverComponente(Resolucao.SRUxMin - paredeEsquerda.componente.largura/2, paredeEsquerda.componente.y, paredeEsquerda.componente.z);
        teto.componente
            .centralizarComponente(false, true, true)
            .moverComponente(teto.componente.x, Resolucao.SRUyMax + teto.componente.altura/2, teto.componente.z);
        
        resetarPosicoes();
    }

    public List<BaseComponente> desenharJogo()
    {
        // Verifica estado atual do jogo
        switch(estado){

            case INICIAL: 

                return telaInicial.componentes;

            case JOGANDO:

                textoPontuacao.setConteudo("Pontuação: " + pontuacao);
                textoFase.setConteudo("Fase: " + fase);
                
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
                                BlocoObjeto obstaculo = obstaculos.blocos.get(coluna).get(linha);

                                if(bola.componente.colidiuComComponente(obstaculo.componente))
                                {
                                    float posicaoBolaXAnterior = bola.componente.x - bola.direcaoMovimentacaoX * bola.velocidadeMovimento * bola.anguloX;
                                    float posicaoBolaYAnterior = bola.componente.y - bola.direcaoMovimentacaoY * bola.velocidadeMovimento;
                                    BolaObjeto bolaAnteriora = new BolaObjeto();
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
                        if(novaPosicaoBolaY < bastao.componente.y) diminuirVida();
                        if((fase == 1 && pontuacao >= 200) || (fase == 2 && obstaculosRestantes == 0)) trocarFase(fase + 1);
                    }
                    else bola.componente.moverComponente(
                        novaPosicaoBolaX, 
                        novaPosicaoBolaY,  
                        bola.componente.z
                    );
                }

                if(vidas == 0) estado = EstadosJogo.PERDEU;

            break;
        }

        return montarTela();
    }

    public void mudarEstado(EstadosJogo novoEstado){ estado = novoEstado; }

    public void diminuirVida()
    { 
        vidas--;
        for(int i = 0; i < TOTAL_VIDAS; i++)
        {
            if(coracoesVida.get(i).cor[1] != 1)
            {
                coracoesVida.get(i).trocarCor(1, 1, 1, 1);
                break;
            }
        }
    }

    public void aumentarPontuacao(int valorAdicional){ pontuacao += valorAdicional; }

    public void trocarFase(int novaFase)
    {
        fase = novaFase;
        bola.velocidadeMovimento = 10;
        switch(fase)
        {
            case 2:

                obstaculosRestantes = 8 * 4;
                obstaculos = new AglomeradoBlocosObjeto(8, 4, 10);
                obstaculos.moverAglomerado(Resolucao.SRUxCentral, Resolucao.SRUyMax - margemTela - 150, obstaculos.z);

            break;
        }
        resetarPosicoes();
    }

    public void resetarPosicoes()
    {
        bola.pararBola();
        bastao.componente.centralizarComponente(false, true, true)
            .moverComponente(bastao.componente.x, Resolucao.SRUyMin + margemTela + bastao.componente.altura / 2, bastao.componente.z);
        bola.componente.centralizarComponente(false, true, true)
            .moverComponente(bola.componente.x, bastao.componente.y + bastao.componente.altura / 2 + bola.componente.altura + 1, bola.componente.z);
    }

    public List<BaseComponente> montarTela()
    {
        List<BaseComponente> componentes = new ArrayList<BaseComponente>();
        switch (estado) 
        {
            case JOGANDO:

                componentes.add(textoPontuacao);
                componentes.add(textoVidas);
                componentes.addAll(coracoesVida);
                componentes.add(textoFase);
                componentes.add(bastao.componente);
                componentes.add(bola.componente);
                componentes.add(teto.componente);
                componentes.add(paredeDireita.componente);
                componentes.add(paredeEsquerda.componente);
                if(fase > 1)
                {
                    for(int coluna = 0; coluna < obstaculos.qtdBlocosHorizontal; coluna++)
                    {
                        for(int linha = 0; linha < obstaculos.blocos.get(coluna).size(); linha++)
                        {
                            componentes.add(obstaculos.blocos.get(coluna).get(linha).componente);
                        }
                    }
                }
            break;
        
            case PERDEU:

                componentes.add(textoVidas);
                componentes.add(textoPontuacao);
                componentes.add(bastao.componente);
                componentes.add(bola.componente);
                componentes.addAll(telaPerdeu.componentes);
            break;

            case PAUSADO:

                componentes.add(textoVidas);
                componentes.add(textoPontuacao);
                componentes.add(bastao.componente);
                componentes.add(bola.componente);
                componentes.addAll(telaPausa.componentes);
            break;
        }
        return componentes;
    }

}
