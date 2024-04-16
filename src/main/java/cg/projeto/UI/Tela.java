package cg.projeto.UI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL4bc;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.gl2.GLUT;

import cg.projeto.Main;
import cg.projeto.Utils;
import cg.projeto.Debug.ModoEdicao;
import cg.projeto.Game.Jogo;
import cg.projeto.Game.Estados.EstadosBola;
import cg.projeto.Game.Estados.EstadosJogo;
import cg.projeto.UI._2D.Componentes.Texto;
import cg.projeto.UI._2D.Componentes.Circulo;
import cg.projeto.UI._2D.Componentes.Octagono;
import cg.projeto.UI._2D.Componentes.Quadrilatero;
import cg.projeto.UI._3D.Componentes.Esfera;
import cg.projeto.UI._3D.Componentes.Hexaedro;

public class Tela implements GLEventListener
{    
    // Elementos UI
    public static float
        limiteZ = 1000, margem = 25, escalaCamera = 1,
        xMin, xMax, xPontoCentral,
        yMin, yMax, yPontoCentral,
        zMin, zMax, zPontoCentral,
        anguloHexaedroDebug = 0,
        espacamentoTexto = 25,
        screenWidth = 0, screenHeight = 0;
    public static float[]
        rotacaoCamera = {0, 0, 0},
        posicaoCamera = {0, 0, 0};
    public String[] textoInicial = {
        "Bem-vindo ao jogo de pong!",
        "Use A D ou < > para movimentar-se",
        "Ou se preferir pode utilizar seu mouse",
        "",
        "Seu objetivo é acumular 500 pontos para avançar",
        "para a próxima fase. Rebata a esfera e ganhe pontos",
        "por cada rebatida. Mas cuidado com a pegadinha do malandro 👀...",
        "(A bolinha fica mais rápida)",
        "",
        "Pressione enter para continuar."
    };
    private final List<ComponenteBase> elementosTela = new ArrayList<ComponenteBase>(); 
    private int camadaMenu, camadaHUD;

    // Conteúdo do jogo
    public static Jogo jogo = new Jogo();
    private Texto textoPontuacao, textoVidas;

    // Renderizadores
    public static GL2 drawer2D;
    public static GLUT drawer3D = new GLUT();
    public static TextRenderer textRenderer = new TextRenderer(new Font(Fonte.FAMILY, Fonte.STYLE, Fonte.SIZE));

    // Debug
    public static ModoEdicao modoEdicao = ModoEdicao.MOVER;
    
    public void init(GLAutoDrawable drawable) 
    {
        //Estabelece as coordenadas do SRU (Sistema de Referencia do Universo)
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (float) screenSize.getWidth();
        screenHeight = (float) screenSize.getHeight();

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
        jogo = new Jogo();
        textoPontuacao = new Texto("Pontuação: 0");
        textoVidas = new Texto("Vidas: 5");
        camadaMenu = (int)zMax;
        camadaHUD = camadaMenu - 1;
    }

    public void display(GLAutoDrawable drawable) 
    {
        //limpa a janela com a cor especificada
        drawer2D.glClearColor(0, 0, 0, 1); // Seta cor pra quando limpar fundo
        drawer2D.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT); // Limpa fundo
        drawer2D.glLoadIdentity(); // Limpa resto
        this.limparTela(); // Apaga elementos

        if(Main.DEBUG) {
            montarMenu(Menus.DEBUG);
        }
        else{
            // Verifica estado atual do jogo
            switch(jogo.estado){

                case INICIAL:

                    // Desenha instruções na tela
                    float posYAtual = yMax - margem;
                    for(int i = 0; i < textoInicial.length; i++){
                        Texto linhaTela = new Texto(textoInicial[i]);
                        posYAtual -= linhaTela.altura + margem;
                        linhaTela.centralizarComponente(false, true, false)
                            .moverComponente(linhaTela.x, posYAtual, linhaTela.z);
                        this.elementosTela.add(linhaTela);
                    }

                    posYAtual -= margem * 2;

                    // Desenha "simulação" do jogo
                    Quadrilatero campo = new Quadrilatero()
                        .redimensionarComponente(200, 200)
                        .centralizarComponente(false, false, false)
                        .preencherComponente(false);
                    campo.moverComponente(campo.x, posYAtual - campo.altura / 2, campo.z);
                    this.elementosTela.add(campo);

                    Quadrilatero bastao = new Quadrilatero()
                        .redimensionarComponente(112.5f, 37.5f)
                        .centralizarComponente(false, true, false);
                    bastao.moverComponente(bastao.x, posYAtual - campo.altura + 11 + bastao.altura / 2, bastao.z);
                    this.elementosTela.add(bastao);

                    Circulo bolinha = new Circulo().redimensionarComponente(20);
                    bolinha.moverComponente(campo.x + campo.largura / 3, campo.y , bolinha.z);
                    this.elementosTela.add(bolinha);

                break;

                case JOGANDO:

                    this.textoPontuacao.conteudo = "Pontuação: " + jogo.pontuacao;
                    this.textoPontuacao.moverComponente(xMin + margem + textoPontuacao.largura/2, yMax - margem - textoPontuacao.altura/2, zMax);
                    this.elementosTela.add(textoPontuacao);
                    
                    this.textoVidas.conteudo = "Vidas: " + jogo.vidas;
                    this.textoVidas.moverComponente(xMax - margem - textoVidas.largura/2, yMax - margem - textoVidas.altura/2, zMax);
                    this.elementosTela.add(textoVidas);
                    
                    if(jogo.bola.estado == EstadosBola.MOVENDO){
                        float novaPosicaoBolaX = jogo.bola.elemento.x + jogo.bola.direcaoMovimentacaoX * jogo.bola.velocidadeMovimento * jogo.bola.anguloX;
                        float novaPosicaoBolaY = jogo.bola.elemento.y + jogo.bola.direcaoMovimentacaoY * jogo.bola.velocidadeMovimento;
                        if(novaPosicaoBolaX + jogo.bola.elemento.raio >= xMax || novaPosicaoBolaX - jogo.bola.elemento.raio <= xMin){
                            jogo.bola.inverterDirecaoMovimentacaoX();
                        }
                        if(novaPosicaoBolaY + jogo.bola.elemento.raio >= yMax){
                            jogo.bola.direcaoMovimentacaoY = -1;
                        }
                        if(novaPosicaoBolaY <= jogo.bastao.elemento.y + jogo.bastao.elemento.altura/2 + jogo.bola.elemento.raio){
                            if(((novaPosicaoBolaX + jogo.bola.elemento.raio <= jogo.bastao.elemento.x + jogo.bastao.elemento.largura/2 &&
                            novaPosicaoBolaX + jogo.bola.elemento.raio >= jogo.bastao.elemento.x - jogo.bastao.elemento.largura/2) ||
                            (novaPosicaoBolaX - jogo.bola.elemento.raio >= jogo.bastao.elemento.x - jogo.bastao.elemento.largura/2 &&
                            novaPosicaoBolaX - jogo.bola.elemento.raio <= jogo.bastao.elemento.x + jogo.bastao.elemento.largura/2))){
                                jogo.bola.aumentarVelocidade(1);
                                jogo.aumentarPontuacao(20);
                                if(jogo.bola.elemento.x > jogo.bastao.elemento.x){
                                    jogo.bola.anguloX = jogo.bastao.elemento.largura/2 / 100 * (jogo.bola.elemento.x - jogo.bastao.elemento.x) / 100; 
                                    jogo.bola.direcaoMovimentacaoX = 1;
                                }
                                if(jogo.bola.elemento.x < jogo.bastao.elemento.x){
                                    jogo.bola.anguloX = jogo.bastao.elemento.largura/2 / 100 * (jogo.bastao.elemento.x - jogo.bola.elemento.x) / 100; 
                                    jogo.bola.direcaoMovimentacaoX = -1;
                                }
                                if(jogo.bola.elemento.x == jogo.bastao.elemento.x){
                                    jogo.bola.direcaoMovimentacaoX = new Random().nextBoolean() ? 1 : -1;
                                }
                                jogo.bola.direcaoMovimentacaoY = 1;
                            }
                        }
                        jogo.bola.elemento.moverComponente(
                            novaPosicaoBolaX, 
                            novaPosicaoBolaY,  
                            jogo.bola.elemento.z
                        );
                        if(novaPosicaoBolaY < jogo.bastao.elemento.y){
                            jogo.resetarPosicoes();
                            jogo.vidas--;
                        }
                    }
                    this.elementosTela.add(jogo.bastao.elemento);
                    this.elementosTela.add(jogo.bola.elemento);
                    if(jogo.vidas == 0) jogo.estado = EstadosJogo.PERDEU;

                break;

                case PERDEU:



                break;
            }
        }

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
    
    private void limparTela(){ this.elementosTela.clear(); }
    
    private void montarMenuDebug(){
        
        
    }

    private void montarMenu(Menus novoMenu){
        switch(novoMenu){
            case DEBUG:
                Texto texto = new Texto("Bem-vindo ao editor do jogo de Pong!");
                texto.moverComponente(texto.x, yMax - margem - texto.altura, texto.z)
                    .centralizarComponente(false, true, true);
                
                Quadrilatero quadrado = new Quadrilatero()
                    .moverComponente(0, 0, zMax - 2)
                    .trocarCor(0, 1, 0, 1f)
                    .preencherComponente(false)
                    .redimensionarComponente(500, 500)
                    .rotacionarComponente(0, 0, 45)
                    .centralizarComponente(true, true, false);
                
                Octagono octagono = new Octagono()
                    .moverComponente(0, 0, zMax - 2)
                    .preencherComponente(false)
                    .trocarCor(1, 1, 0, 1)
                    .redimensionarComponente(450, 450)
                    .centralizarComponente(true, true, false);
            
                if(anguloHexaedroDebug < 360) anguloHexaedroDebug++;
                else anguloHexaedroDebug = 0;
                Hexaedro hexaedro = new Hexaedro()
                    .trocarCor(1, 0, 1, 0.2f)
                    .redimensionarComponente(200, 200, 200)
                    .rotacionarComponente(anguloHexaedroDebug, anguloHexaedroDebug, anguloHexaedroDebug)
                    .centralizarComponente(true, true, true);
            
                Esfera esfera = new Esfera()
                    .trocarCor(1, 0, 0, 1)
                    .redimensionarComponente(75)
                    .preencherComponente(false)
                    .centralizarComponente(true, true, true)
                    .rotacionarComponente(0, 90, 0);
            
                // Adiciona ponto vermelho central
                Quadrilatero pontoVermelho = new Quadrilatero()
                    .trocarCor(1, 0, 0, 1)
                    .redimensionarComponente(10, 10)
                    .centralizarComponente(true, true, true);
                    
                // Adiciona modo de edição
                Texto textoModoEdicao = new Texto("Modo de edição: "+modoEdicao);
                textoModoEdicao.moverComponente(xMin + textoModoEdicao.largura / 2 + margem, yMin + textoModoEdicao.altura / 2 + margem, zPontoCentral);
                
                // Adiciona escala
                Texto textoEscala = new Texto("Escala: "+Math.round(escalaCamera * 100)+"%");
                textoEscala.moverComponente(xMin + textoEscala.largura / 2 + margem, yMin + textoEscala.altura / 2 + margem + textoModoEdicao.altura, zPontoCentral);
                    
                elementosTela.add(texto);
                elementosTela.add(textoModoEdicao);
                elementosTela.add(textoEscala);
                elementosTela.add(pontoVermelho);
                elementosTela.add(quadrado);
                elementosTela.add(octagono);
                elementosTela.add(hexaedro);
                elementosTela.add(esfera);
            break;
        }
    }
}
