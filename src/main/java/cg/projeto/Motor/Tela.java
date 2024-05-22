package cg.projeto.Motor;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL4bc;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.gl2.GLUT;

import cg.projeto.Main;
import cg.projeto.Utils;
import cg.projeto.Debug.ModosEdicao;
import cg.projeto.Jogo.GameLoop;
import cg.projeto.Jogo.Objetos._2D.Bastao;
import cg.projeto.Jogo.Objetos._2D.Bola;
import cg.projeto.Jogo.Objetos._2D.Obstaculos.AglomeradoBlocos;
import cg.projeto.Jogo.Objetos._2D.Obstaculos.Bloco;
import cg.projeto.Motor.Componentes.ComponenteBase;
import cg.projeto.Motor.Componentes._2D.Circulo;
import cg.projeto.Motor.Componentes._2D.Octagono;
import cg.projeto.Motor.Componentes._2D.Quadrilatero;
import cg.projeto.Motor.Componentes._2D.Texto;
import cg.projeto.Motor.Componentes._3D.Esfera;
import cg.projeto.Motor.Componentes._3D.Hexaedro;

public class Tela implements GLEventListener
{    
    // Elementos UI
    public static Dimension screenSize;
    public static final float 
        limiteZ = 1000,
        margem = 25;
    public static float
        screenWidth, 
        screenHeight,
        escalaCamera = 1,
        xMin, xMax, xPontoCentral,
        yMin, yMax, yPontoCentral,
        zMin, zMax, zPontoCentral,
        anguloHexaedroDebug = 0;
    public static float[]
        rotacaoCamera = {0, 0, 0},
        posicaoCamera = {0, 0, 0};
    public static final String[] textoInicial = {
        "Bem-vindo ao jogo de pong!",
        "A D ou <- -> ou MOUSE (Movimentação).",
        "P (Pause)", 
        "R (Recomeçar jogo)",
        "Seu objetivo é acumular 500 pontos para avançar",
        "para a próxima fase. Rebata a esfera e ganhe pontos.",
        "Mas cuidado com a pegadinha do malandro 👀... (A bolinha fica mais rápida)",
        "",
        "Pressione enter para começar."
    };
    public final static List<ComponenteBase> elementosTela = new ArrayList<ComponenteBase>(); 

    // Conteúdo do jogo
    public static GameLoop jogo;

    // Renderizadores
    public static GL2 drawer2D;
    public static final GLUT drawer3D = new GLUT();
    public static final TextRenderer textRenderer = new TextRenderer(new Font(Fonte.FAMILY, Fonte.STYLE, Fonte.SIZE));

    // Debug
    public static ModosEdicao modoEdicao = ModosEdicao.MOVER;
    public static AglomeradoBlocos gridBlocos = new AglomeradoBlocos(14, 10, 10);
    public static Quadrilatero fundoGrid = new Quadrilatero();

    public void init(GLAutoDrawable drawable) 
    {
        //Estabelece as coordenadas do SRU (Sistema de Referencia do Universo)
        DisplayMode displayMode;
        Rectangle bounds;
        Rectangle scaledBounds;
        for (GraphicsDevice device : GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()) {
            displayMode = device.getDefaultConfiguration().getDevice().getDisplayMode();
            scaledBounds = device.getDefaultConfiguration().getBounds();
            bounds = new Rectangle(scaledBounds.x, scaledBounds.y, displayMode.getWidth(), displayMode.getHeight());
            screenWidth = Math.max(Integer.MIN_VALUE, bounds.x + bounds.width) - Math.min(Integer.MAX_VALUE, bounds.x);
            screenHeight = Math.max(Integer.MIN_VALUE, bounds.y + bounds.height) - Math.min(Integer.MAX_VALUE, bounds.y);
        }
        xMin = screenWidth/2 * -1;
        xMax = screenWidth/2;
        yMin = screenHeight/2 * -1;
        yMax = screenHeight/2;
        zMin = limiteZ * -1;
        zMax = limiteZ;
        xPontoCentral = Utils.mediana(xMin, xMax);
        yPontoCentral = Utils.mediana(yMin, yMax);
        zPontoCentral = Utils.mediana(zMin, zMax);
        
        // Configura renderizador
        drawer2D = drawable.getGL().getGL2();
        drawer2D.glEnable(GL2.GL_DEPTH_TEST);

        // Habilita iluminação
        drawer2D.glEnable(GL2.GL_COLOR_MATERIAL);
        drawer2D.glEnable(GL2.GL_LIGHTING);
        drawer2D.glEnable(GL2.GL_LIGHT0);
        drawer2D.glEnable(GL2.GL_NORMALIZE);
        drawer2D.glShadeModel(GL2.GL_SMOOTH);

        // Permite opacidade
        drawer2D.glEnable(GL4bc.GL_BLEND);
        drawer2D.glBlendFunc(GL4bc.GL_SRC_ALPHA, GL4bc.GL_ONE_MINUS_SRC_ALPHA);

        // Inicializa jogo
        jogo = new GameLoop();

        // Tira preenchimento dos blocos no grid de debug
        for(int coluna = 0; coluna < gridBlocos.qtdBlocosHorizontal; coluna++)
        {
            for(int linha = 0; linha < gridBlocos.qtdBlocosVertical; linha++)
            {
                gridBlocos.blocos.get(coluna).get(linha).elemento.preencherComponente(false);
            }
        }
        fundoGrid.redimensionarComponente(
            Bloco.larguraBloco * gridBlocos.qtdBlocosHorizontal + gridBlocos.espacamento * (gridBlocos.qtdBlocosHorizontal-1), 
            Bloco.alturaBloco * gridBlocos.qtdBlocosVertical + gridBlocos.espacamento * (gridBlocos.qtdBlocosVertical-1)
        ).moverComponente(xPontoCentral, yMax - margem - 100 - fundoGrid.altura/2, zPontoCentral);
        gridBlocos.moverAglomerado(fundoGrid.x, fundoGrid.y, fundoGrid.z);
    }

    public void display(GLAutoDrawable drawable) 
    {
        //limpa a janela com a cor especificada
        drawer2D.glClearColor(0, 0, 0, 1); // Seta cor pra quando limpar fundo
        drawer2D.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT); // Limpa fundo
        drawer2D.glLoadIdentity(); // Limpa resto
        this.limparTela(); // Apaga elementos

        if(Main.DEBUG) montarMenu(Menus.DEBUG);
        else jogo.desenharJogo();

        // Adiciona luz no cenário
        drawer2D.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, new float[]{1f, 1f, 1f, 1}, 0);
        drawer2D.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, new float[]{xPontoCentral, yPontoCentral, zMax, 1}, 0);

        // Calcula posição da câmera
        drawer2D.glTranslatef(posicaoCamera[0], posicaoCamera[1], posicaoCamera[2]);
        drawer2D.glRotatef(rotacaoCamera[1], 1, 0, 0);
        drawer2D.glRotatef(rotacaoCamera[0], 0, 1, 0);
        drawer2D.glRotatef(rotacaoCamera[2], 0, 0, 1);
        drawer2D.glScalef(escalaCamera, escalaCamera, escalaCamera);
        
        // Desenha elementos adicionados baseado no estado do jogo
        this.desenharTela();

        // Executa as alterações no OpenGL
        drawer2D.glFlush();
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) 
    {    
        // Evita a divisão por zero
        if(height == 0) height = 1;
        
        // Seta o viewport para abranger a janela inteira
        drawer2D.glViewport(0, 0, width, height);
        
        // Ativa a matriz de projeção
        drawer2D.glMatrixMode(GL2.GL_PROJECTION);      
        drawer2D.glLoadIdentity(); // lê a matriz identidade
        
        // Projeção ortogonal
        // true:   aspect >= 1 configura a altura de -1 para 1 : com largura maior
        // false:  aspect < 1 configura a largura de -1 para 1 : com altura maior
        drawer2D.glOrtho(xMin, xMax, yMin, yMax, zMin, zMax);
                
        // Ativa a matriz de modelagem
        drawer2D.glMatrixMode(GL2.GL_MODELVIEW);
        drawer2D.glLoadIdentity(); // lê a matriz identidade
        System.out.println("Reshape: " + width + ", " + height);
    }    
       
    public void dispose(GLAutoDrawable drawable) {}

    private void desenharTela()
    {
        for(int index = elementosTela.size() - 1; index >= 0; index--)
        {    
            ComponenteBase componente = elementosTela.get(index);
            
            if(componente.x >= xMin && componente.x <= xMax && componente.y >= yMin && componente.y <= yMax && componente.z >= zMin && componente.z <= zMax)
            componente.desenharComponente();
        }
    }
    
    private void limparTela(){ elementosTela.clear(); }

    public static void montarMenu(Menus novoMenu)
    {
        switch(novoMenu)
        {
            case INICIAL:

                // Desenha instruções na tela
                float posYAtual = yMax - margem;
                for(int i = 0; i < textoInicial.length; i++)
                {
                    Texto linhaTela = new Texto(textoInicial[i]);
                    posYAtual -= linhaTela.altura + margem;
                    linhaTela.centralizarComponente(false, true, false)
                        .moverComponente(linhaTela.x, posYAtual, linhaTela.z);
                    elementosTela.add(linhaTela);
                }

                posYAtual -= margem * 2;

                // Desenha "simulação" do jogo
                Quadrilatero campo = new Quadrilatero()
                    .redimensionarComponente(200, 200)
                    .centralizarComponente(false, false, false)
                    .preencherComponente(false);
                campo.moverComponente(campo.x, posYAtual - campo.altura / 2, campo.z);
                elementosTela.add(campo);

                Quadrilatero bastao = new Quadrilatero()
                    .redimensionarComponente(112.5f, 37.5f)
                    .centralizarComponente(false, true, false);
                bastao.moverComponente(bastao.x, posYAtual - campo.altura + 11 + bastao.altura / 2, bastao.z);
                elementosTela.add(bastao);

                Circulo bolinha = new Circulo().redimensionarComponente(20);
                bolinha.moverComponente(campo.x + campo.largura / 3, campo.y , bolinha.z);
                elementosTela.add(bolinha);

            break;

            case DEBUG:

                Texto texto = new Texto("Bem-vindo ao editor do jogo de Pong!");
                texto.moverComponente(texto.x, yMax - margem - texto.altura, texto.z)
                    .centralizarComponente(false, true, true);
                elementosTela.add(texto);

                if(modoEdicao != ModosEdicao.GRID)
                {
                    Quadrilatero quadrado = new Quadrilatero()
                        .moverComponente(0, 0, zMax - 2)
                        .trocarCor(0, 1, 0, 1f)
                        .preencherComponente(false)
                        .redimensionarComponente(500, 500)
                        .rotacionarComponente(0, 0, 45)
                        .centralizarComponente(true, true, false);
                    elementosTela.add(quadrado);

                    Octagono octagono = new Octagono()
                        .moverComponente(0, 0, zMax - 2)
                        .preencherComponente(false)
                        .trocarCor(1, 1, 0, 1)
                        .redimensionarComponente(450, 450)
                        .centralizarComponente(true, true, false);
                    elementosTela.add(octagono);

                    if(anguloHexaedroDebug < 360) anguloHexaedroDebug++;
                    else anguloHexaedroDebug = 0;
                    Hexaedro hexaedro = new Hexaedro()
                        .trocarCor(1, 0, 1, 0.2f)
                        .redimensionarComponente(200, 200, 200)
                        .rotacionarComponente(anguloHexaedroDebug, anguloHexaedroDebug, anguloHexaedroDebug)
                        .centralizarComponente(true, true, true);
                    elementosTela.add(hexaedro);

                    Esfera esfera = new Esfera()
                        .trocarCor(1, 0, 0, 1)
                        .redimensionarComponente(75)
                        .preencherComponente(false)
                        .centralizarComponente(true, true, true)
                        .rotacionarComponente(0, 90, 0);
                    elementosTela.add(esfera);
                
                    // Adiciona ponto vermelho central
                    Quadrilatero pontoVermelho = new Quadrilatero()
                        .trocarCor(1, 0, 0, 1)
                        .redimensionarComponente(10, 10)
                        .centralizarComponente(true, true, true);
                    elementosTela.add(pontoVermelho);

                    // Adiciona modo de edição
                    Texto textoModoEdicao = new Texto("Modo de edição: "+modoEdicao);
                    textoModoEdicao.moverComponente(xMin + textoModoEdicao.largura / 2 + margem, yMin + textoModoEdicao.altura / 2 + margem, zPontoCentral);
                    elementosTela.add(textoModoEdicao);

                    // Adiciona escala
                    Texto textoEscala = new Texto("Escala: "+Math.round(escalaCamera * 100)+"%");
                    textoEscala.moverComponente(xMin + textoEscala.largura / 2 + margem, yMin + textoEscala.altura / 2 + margem + textoModoEdicao.altura, zPontoCentral);
                    elementosTela.add(textoEscala);

                    Esfera esfera1 = new Esfera()
                        .trocarCor(1, 1, 0, 1)
                        .redimensionarComponente(75)
                        .preencherComponente(false)
                        .centralizarComponente(true, true, true)
                        .rotacionarComponente(0, 90, 0)
                        .moverComponente(xMin, yMin, 0);
                    elementosTela.add(esfera1);
                    
                    Esfera esfera2 = new Esfera()
                        .trocarCor(0, 1, 1, 1)
                        .redimensionarComponente(75)
                        .preencherComponente(false)
                        .centralizarComponente(true, true, true)
                        .rotacionarComponente(0, 90, 0)
                        .moverComponente(xMin+10, yMin+10, 0);
                    elementosTela.add(esfera2);
                    
                    Texto textoColidiu = new Texto("Colidiu?: " + esfera1.largura + (esfera1.colidiuComComponente(esfera2) ? "Sim" : "Não"));
                    textoColidiu.moverComponente(xMin + textoEscala.largura + margem + textoColidiu.largura/2 + 30, yMin + textoColidiu.altura / 2 + margem + textoModoEdicao.altura, zPontoCentral);
                    elementosTela.add(textoColidiu);
                }
                else if(modoEdicao == ModosEdicao.GRID)
                {
                    Hexaedro bastaoModelo = new Bastao().elemento
                        .centralizarComponente(false, true, true);
                    bastaoModelo.moverComponente(bastaoModelo.x, Tela.yMin + Tela.margem + bastaoModelo.altura / 2, bastaoModelo.z);
                    elementosTela.add(bastaoModelo);

                    Esfera bolaModelo = new Bola().elemento
                        .centralizarComponente(false, true, true);
                        bolaModelo.moverComponente(bolaModelo.x, bastaoModelo.y + bastaoModelo.altura / 2 + bolaModelo.raio + 1, bolaModelo.z);
                    elementosTela.add(bolaModelo);
                    
                    elementosTela.add(fundoGrid);

                    // Desenha linhas do grid
                    for(int coluna = 0; coluna < gridBlocos.qtdBlocosHorizontal; coluna++)
                    {
                        for(int linha = 0; linha < gridBlocos.qtdBlocosVertical; linha++)
                        {
                            elementosTela.add(gridBlocos.blocos.get(coluna).get(linha).elemento);
                        }
                    }
                }
            break;

            case PAUSADO:

                Quadrilatero telaTransparente = new Quadrilatero()
                    .redimensionarComponente(xMax - xMin, yMax - yMin)
                    .trocarCor(0, 0, 0, 1);
                telaTransparente.moverComponente(telaTransparente.x, telaTransparente.y, zMax - 1);
                elementosTela.add(telaTransparente);

                Texto textoPausado = new Texto("Jogo pausado (Pressione P para continuar)");
                textoPausado.moverComponente(textoPausado.x, textoPausado.y, zMax);
                elementosTela.add(textoPausado);

            break;

            case PERDEU:

                Texto textoPerdeu = new Texto("Você perdeu, pressione R para recomeçar");
                textoPerdeu.moverComponente(textoPerdeu.x, textoPerdeu.y, zMax);
                elementosTela.add(textoPerdeu);

            break;
        }
    }
}
