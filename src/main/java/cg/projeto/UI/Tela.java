package cg.projeto.UI;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

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
import cg.projeto.UI._2D.Componentes.Texto;
import cg.projeto.UI._2D.Componentes.Circulo;
import cg.projeto.UI._2D.Componentes.Octagono;
import cg.projeto.UI._2D.Componentes.Quadrilatero;
import cg.projeto.UI._3D.Componentes.Esfera;
import cg.projeto.UI._3D.Componentes.Hexaedro;

public class Tela implements GLEventListener{    

    // Elementos UI
    public static float
        limiteSRU = 1000, margem = 50, escalaCamera = 1,
        xMin, xMax, xPontoCentral,
        yMin, yMax, yPontoCentral,
        zMin, zMax, zPontoCentral,
        anguloHexaedroDebug = 0,
        espacamentoTexto = 50;
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

    // Conteúdo do jogo
    public static Jogo jogo = new Jogo();

    // Renderizadores
    public static GL2 drawer2D;
    public static GLUT drawer3D = new GLUT();
    public static TextRenderer textRenderer = new TextRenderer(new Font(Fonte.FAMILY, Fonte.STYLE, Fonte.SIZE));

    // Debug
    public static ModoEdicao modoEdicao = ModoEdicao.MOVER;
    
    public void init(GLAutoDrawable drawable) {

        //Estabelece as coordenadas do SRU (Sistema de Referencia do Universo)
        xMax = yMax = zMax = limiteSRU;
        xMin = yMin = zMin = limiteSRU * -1;
        xPontoCentral = yPontoCentral = zPontoCentral = Utils.mediana(limiteSRU * -1, limiteSRU);
        
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

        // Instancia jogo
        jogo = new Jogo();
    }

    public void display(GLAutoDrawable drawable) {

        //limpa a janela com a cor especificada
        drawer2D.glClearColor(0, 0, 0, 1); // Seta cor pra quando limpar fundo
        drawer2D.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT); // Limpa fundo
        drawer2D.glLoadIdentity(); // Limpa resto
        this.limparTela(); // Apaga elementos

        if(Main.DEBUG) montarMenuDebug();
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
                        .redimensionarComponente(750, 750)
                        .centralizarComponente(false, false, false)
                        .preencherComponente(false);
                    campo.moverComponente(campo.x, posYAtual - campo.altura / 2, campo.z);
                    this.elementosTela.add(campo);

                    Quadrilatero bastao = new Quadrilatero()
                        .redimensionarComponente(225, 75)
                        .centralizarComponente(false, true, false);
                    bastao.moverComponente(bastao.x, posYAtual - campo.altura + 11 + bastao.altura / 2, bastao.z);
                    this.elementosTela.add(bastao);

                    Circulo bolinha = new Circulo().redimensionarComponente(50);
                    bolinha.moverComponente(campo.x + campo.largura / 3, campo.y , bolinha.z);
                    this.elementosTela.add(bolinha);

                break;

                case JOGANDO:
                    
                    System.out.println(jogo.bastao.elemento.x + " " + jogo.bastao.elemento.y);
                    this.elementosTela.add(jogo.bastao.elemento);
                    this.elementosTela.add(jogo.bola.elemento);

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

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        
        // Evita a divisão por zero
        if(height == 0) height = 1;
        
        // Calcula a proporção da janela (aspect ratio) da nova janela
        float aspect = (float) width / height;
        
        // Seta o viewport para abranger a janela inteira
        drawer2D.glViewport(0, 0, width, height);
                
        // Ativa a matriz de projeção
        drawer2D.glMatrixMode(GL2.GL_PROJECTION);      
        drawer2D.glLoadIdentity(); // lê a matriz identidade
        
        // Projeção ortogonal
        // true:   aspect >= 1 configura a altura de -1 para 1 : com largura maior
        // false:  aspect < 1 configura a largura de -1 para 1 : com altura maior
        if(width >= height)            
            drawer2D.glOrtho(xMin * aspect, xMax * aspect, yMin, yMax, zMin, zMax);
        else        
            drawer2D.glOrtho(xMin, xMax, yMin / aspect, yMax / aspect, zMin, zMax);
                
        // Ativa a matriz de modelagem
        drawer2D.glMatrixMode(GL2.GL_MODELVIEW);
        drawer2D.glLoadIdentity(); // lê a matriz identidade
        System.out.println("Reshape: " + width + ", " + height);
    }    
       
    public void dispose(GLAutoDrawable drawable) {}

    private void montarMenuDebug(){

        Texto texto = new Texto("Bem-vindo ao editor do jogo de Pong!");
        texto.moverComponente(texto.x, yMax - margem - texto.altura, texto.z)
            .centralizarComponente(false, true, true);
        
        Quadrilatero quadrado = new Quadrilatero()
            .moverComponente(0, 0, zMax - 2)
            .trocarCor(0, 1, 0, 1f)
            .preencherComponente(false)
            .redimensionarComponente(1000, 1000)
            .rotacionarComponente(0, 0, 45)
            .centralizarComponente(true, true, false);
        
        Octagono octagono = new Octagono()
            .moverComponente(0, 0, zMax - 2)
            .preencherComponente(false)
            .trocarCor(1, 1, 0, 1)
            .redimensionarComponente(900, 900)
            .centralizarComponente(true, true, false);

        if(anguloHexaedroDebug < 360) anguloHexaedroDebug++;
        else anguloHexaedroDebug = 0;
        Hexaedro hexaedro = new Hexaedro()
            .trocarCor(1, 0, 1, 0.2f)
            .redimensionarComponente(400, 400, 400)
            .rotacionarComponente(anguloHexaedroDebug, anguloHexaedroDebug, anguloHexaedroDebug)
            .centralizarComponente(true, true, true);

        Esfera esfera = new Esfera()
            .trocarCor(1, 0, 0, 1)
            .redimensionarComponente(150)
            .preencherComponente(false)
            .centralizarComponente(true, true, true)
            .rotacionarComponente(0, 90, 0);

        // Adiciona ponto vermelho central
        Quadrilatero pontoVermelho = new Quadrilatero()
            .trocarCor(1, 0, 0, 1)
            .redimensionarComponente(20, 20)
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
        
    }

    private void desenharTela(){
        for(int index = elementosTela.size() - 1; index >= 0; index--){

            ComponenteBase componente = elementosTela.get(index);

            if(componente.x >= xMin && componente.x <= xMax && componente.y >= yMin && componente.y <= yMax && componente.z >= zMin && componente.z <= zMax)
                componente.desenharComponente();
        }
    }
    
    private void limparTela(){ this.elementosTela.clear(); }

}
