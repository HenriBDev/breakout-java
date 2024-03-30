package cg.projeto.UI;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL4bc;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.gl2.GLUT;

import cg.projeto.Utils;
import cg.projeto.Debug.ModoEdicao;
import cg.projeto.Game.Jogo;
import cg.projeto.UI._2D.Componentes.Texto;
import cg.projeto.UI._2D.Componentes.Octagono;
import cg.projeto.UI._2D.Componentes.Quadrilatero;
import cg.projeto.UI._3D.Componentes.Esfera;
import cg.projeto.UI._3D.Componentes.Hexaedro;

public class Tela implements GLEventListener{    

    // Elementos UI
    public static float
        limiteSRU = 1000, margem = 20,
        xMin, xMax, xPontoCentral,
        yMin, yMax, yPontoCentral,
        zMin, zMax, zPontoCentral;
    public static float[]
        escalaCamera = {1, 1, 1},
        rotacaoCamera = {0, 0, 0},
        posicaoCamera = {0, 0, 0};
    private final List<ComponenteBase> elementosTela = new ArrayList<ComponenteBase>(); 

    // Conteúdo do jogo
    public final Jogo jogo = new Jogo();

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
        // Permite opacidade
        drawer2D.glEnable(GL4bc.GL_BLEND);
        drawer2D.glBlendFunc(GL4bc.GL_SRC_ALPHA, GL4bc.GL_ONE_MINUS_SRC_ALPHA);
    }

    public void display(GLAutoDrawable drawable) {

        //limpa a janela com a cor especificada
        drawer2D.glClearColor(0, 0, 0, 1); // Seta cor pra quando limpar fundo
        drawer2D.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT); // Limpa fundo
        drawer2D.glLoadIdentity(); // Limpa resto
        this.limparTela(); // Apaga elementos

        // Verifica estado atual do jogo
        switch(jogo.estado){
            case INICIAL:
                montarMenu();
            break;
        }
            
        // Adiciona ponto vermelho central (DEBUG)
        Quadrilatero pontoVermelho = new Quadrilatero()
            .trocarCor(1, 0, 0, 1)
            .redimensionarComponente(20, 20)
            .centralizarComponente(true, true, true);
        elementosTela.add(pontoVermelho);

        // Adiciona modo de edição
        Texto texto = new Texto("Modo de edição: "+modoEdicao);
        texto.moverComponente(xMin + texto.largura / 2 + margem, yMin + texto.altura / 2 + margem, zPontoCentral);
        elementosTela.add(texto);

        // Calcula posição da câmera (DEBUG)
        drawer2D.glTranslatef(posicaoCamera[0], posicaoCamera[1], posicaoCamera[2]);
        drawer2D.glRotatef(rotacaoCamera[1], 1, 0, 0);
        drawer2D.glRotatef(rotacaoCamera[0], 0, 1, 0);
        drawer2D.glRotatef(rotacaoCamera[2], 0, 0, 1);

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

    private void montarMenu(){

        Texto texto = new Texto("Bem-vindo ao jogo de Pong!");
        texto.moverComponente(texto.x, yMax - margem - texto.altura, texto.z)
            .centralizarComponente(false, true, true);
        
        Quadrilatero quadrado = new Quadrilatero()
            .trocarCor(0, 1, 0, 0.5f)
            .redimensionarComponente(300, 300)
            .rotacionarComponente(0, 0, 45)
            .centralizarComponente(true, true, true);
        
        Octagono octagono = new Octagono()
            .trocarCor(1, 1, 0, 1)
            .redimensionarComponente(200, 200)
            .centralizarComponente(true, true, true);

        Hexaedro hexaedro = new Hexaedro()
            .trocarCor(1, 0, 1, 1)
            .redimensionarComponente(400, 400, 400)
            .preencherComponente(false)
            .rotacionarComponente(45, 45, 0)
            .moverComponente(xPontoCentral, yPontoCentral - 100, zPontoCentral);

        Esfera esfera = new Esfera()
            .trocarCor(1, 0, 0, 1)
            .redimensionarComponente(150)
            .preencherComponente(false)
            .centralizarComponente(true, true, true)
            .rotacionarComponente(0, 90, 0);

        elementosTela.add(texto);
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
