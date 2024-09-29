package breakout.java.Jogo.Telas.Debug;

import breakout.java.Resolucao;
import breakout.java.Jogo.DebugLoop;
import breakout.java.Jogo.Estados.Debug.EstadosEditor;
import breakout.java.Jogo.Telas.BaseTela;
import breakout.java.Motor.Renderizador;
import breakout.java.Motor.Componentes._2D.CirculoComponente;
import breakout.java.Motor.Componentes._2D.CoracaoComponente;
import breakout.java.Motor.Componentes._2D.OctagonoComponente;
import breakout.java.Motor.Componentes._2D.QuadrilateroComponente;
import breakout.java.Motor.Componentes._2D.TextoComponente;
import breakout.java.Motor.Componentes._3D.EsferaComponente;
import breakout.java.Motor.Componentes._3D.HexaedroComponente;

public class EdicaoTela extends BaseTela {

    public TextoComponente titulo, textoModoEdicao, textoEscala, textoColidiu;
    
    public QuadrilateroComponente quadrado = new QuadrilateroComponente()
        .moverComponente(Resolucao.SRUxCentral, Resolucao.SRUyCentral, Resolucao.SRUzMax - 2)
        .trocarCor(0, 1, 0, 1f)
        .preencherComponente(false)
        .redimensionarComponente(500, 500)
        .rotacionarComponente(0, 0, 45)
        .centralizarComponente(true, true, false);
    
    public OctagonoComponente octagono = new OctagonoComponente()
        .moverComponente(Resolucao.SRUxCentral, Resolucao.SRUyCentral, Resolucao.SRUzMax - 2)
        .trocarCor(1, 0, 0, 1f)
        .preencherComponente(false)
        .redimensionarComponente(400, 400)
        .centralizarComponente(true, true, false);

    public CirculoComponente circulo = new CirculoComponente()
        .moverComponente(Resolucao.SRUxCentral, Resolucao.SRUyCentral, Resolucao.SRUzMax - 2)
        .trocarCor(0, 0, 1, 1f)
        .preencherComponente(false)
        .redimensionarComponente(350, 350)
        .centralizarComponente(true, true, false);
    
    public CoracaoComponente coracao = new CoracaoComponente()
        .moverComponente(Resolucao.SRUxCentral, Resolucao.SRUyCentral, Resolucao.SRUzMax - 2)
        .trocarCor(0, 1, 1, 1f)
        .preencherComponente(false)
        .redimensionarComponente(400, 400)
        .centralizarComponente(true, true, false);

    public float anguloHexaedroDebug = 0, margemTela = 25;

    HexaedroComponente hexaedro = new HexaedroComponente()
        .trocarCor(1, 0, 1, 0.2f)
        .redimensionarComponente(200, 200, 200)
        .rotacionarComponente(anguloHexaedroDebug, anguloHexaedroDebug, anguloHexaedroDebug)
        .centralizarComponente(true, true, true);

    EsferaComponente esfera = new EsferaComponente()
        .trocarCor(1, 0, 0, 1)
        .redimensionarComponente(75, 75, 75)
        .preencherComponente(false)
        .centralizarComponente(true, true, true)
        .rotacionarComponente(0, 90, 0);
        
    QuadrilateroComponente pontoVermelho = new QuadrilateroComponente()
        .trocarCor(1, 0, 0, 1)
        .redimensionarComponente(10, 10)
        .centralizarComponente(true, true, true);

    EsferaComponente esfera1 = new EsferaComponente()
        .trocarCor(1, 1, 0, 1)
        .redimensionarComponente(75, 75, 75)
        .preencherComponente(false)
        .centralizarComponente(true, true, true)
        .rotacionarComponente(0, 90, 0)
        .moverComponente(Resolucao.SRUxMin, Resolucao.SRUyMin, 0);

    EsferaComponente esfera2 = new EsferaComponente()
        .trocarCor(0, 1, 1, 1)
        .redimensionarComponente(75, 75, 75)
        .preencherComponente(false)
        .centralizarComponente(true, true, true)
        .rotacionarComponente(0, 90, 0)
        .moverComponente(Resolucao.SRUxMin+10, Resolucao.SRUyMin+10, 0);

    public EdicaoTela()
    {    
        titulo = new TextoComponente("Bem-vindo ao editor do jogo de Pong! (Para acessar o grid pressione G)")
            .centralizarComponente(false, true, true);
        titulo.moverComponente(Resolucao.SRUxCentral, Resolucao.SRUyMax - margemTela - titulo.altura, titulo.z);

        textoModoEdicao = new TextoComponente("Modo de edição (Alterne com M ou R): "+ DebugLoop.modoEdicao);
        textoModoEdicao.moverComponente(
            Resolucao.SRUxMin + textoModoEdicao.largura / 2 + margemTela, 
            Resolucao.SRUyMin + textoModoEdicao.altura / 2 + margemTela, 
            Resolucao.SRUzCentral
        );

        // Adiciona escala
        textoEscala = new TextoComponente("Escala (Altere com o scroll do mouse): "+Math.round(Renderizador.escalaCamera * 100)+"%");
        textoEscala.moverComponente(
            Resolucao.SRUxMin + textoEscala.largura / 2 + margemTela, 
            Resolucao.SRUyMin + textoEscala.altura / 2 + margemTela + textoModoEdicao.altura, 
            Resolucao.SRUzCentral
        );

        textoColidiu = new TextoComponente("Colidiu?: " + esfera1.largura + (esfera1.colidiuComComponente(esfera2) ? "Sim" : "Não"));
        textoColidiu.moverComponente(
            Resolucao.SRUxMin + textoEscala.largura + margemTela + textoColidiu.largura/2 + 30, 
            Resolucao.SRUyMin + textoColidiu.altura / 2 + margemTela + textoModoEdicao.altura, 
            Resolucao.SRUzCentral
        );

        montarTela();
    }

    private void montarTela()
    {
        componentes.clear();
        componentes.add(titulo);
        componentes.add(quadrado);
        componentes.add(octagono);
        componentes.add(circulo);
        componentes.add(coracao);
        componentes.add(hexaedro);
        componentes.add(esfera);
        componentes.add(pontoVermelho);
        componentes.add(textoModoEdicao);
        componentes.add(textoEscala);
        componentes.add(esfera1);
        componentes.add(esfera2);
        componentes.add(textoColidiu);
    }

    public void setHexaedroAngulo(float novoAngulo)
    {
        anguloHexaedroDebug = novoAngulo;
        hexaedro.rotacionarComponente(anguloHexaedroDebug, anguloHexaedroDebug, anguloHexaedroDebug);
        montarTela();
    }

    public void setTextoModoEdicao(EstadosEditor novoModo)
    {
        textoModoEdicao.setConteudo("Modo de edição (Alterne com M ou R): " + novoModo);
        textoModoEdicao.moverComponente(
            Resolucao.SRUxMin + textoModoEdicao.largura / 2 + margemTela, 
            Resolucao.SRUyMin + textoModoEdicao.altura / 2 + margemTela, 
            Resolucao.SRUzCentral
        );
        montarTela();
    }

}
