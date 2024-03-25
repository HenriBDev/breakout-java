package cg.projeto.UI;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Dimension;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.awt.TextRenderer;

import cg.projeto.Game.Jogo;
import cg.projeto.UI.Componentes.Label;
import cg.projeto.UI.Componentes.Quadrilatero;

public class Tela implements GLEventListener{    

    // Elementos UI
    public static float xMin, xMax, yMin, yMax, zMin, zMax, margem = 20;
    private final Camadas camadas = new Camadas(); 

    // Conteúdo do jogo
    public final Jogo jogo = new Jogo();

    // Renderizadores
    public static GL2 drawer;
    public static final TextRenderer textRenderer = new TextRenderer(new Font(Fonte.FAMILY, Fonte.STYLE, Fonte.SIZE));
    
    public void init(GLAutoDrawable drawable) {

        //Estabelece as coordenadas do SRU (Sistema de Referencia do Universo)
        xMin = yMin = zMin = 0;
        xMax = 1000;
        yMax = 1000;
        // Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // xMax = (float) screenSize.getWidth();
        // yMax = (float) screenSize.getHeight();
        zMax = Camadas.class.getClass().getDeclaredFields().length - 1;
        
        // Configura renderizador
        drawer = drawable.getGL().getGL2();
        drawer.glEnable(GL2.GL_DEPTH_TEST);
    }

    public void display(GLAutoDrawable drawable) {

        //limpa a janela com a cor especificada
        drawer.glClearColor(0, 0, 0, 1); // Seta cor pra quando limpar fundo
        drawer.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT); // Limpa fundo
        drawer.glLoadIdentity(); // Limpa resto
        camadas.limparCamadas(); // Esvazia camadas

        // Verifica estado atual do jogo
        switch(jogo.estado){
            case INICIAL:
                montarMenu();
            break;
        }
        
        // Desenha camadas com elementos adicionados baseado no estado do jogo
        camadas.desenharCamadas();

        // Desenha borda do jogo
        drawer.glLineWidth(10); 
        drawer.glColor3f(1, 1, 1);
        drawer.glBegin(GL2.GL_LINE_LOOP);
        drawer.glVertex2d(xMin + 1, yMin);
        drawer.glVertex2d(xMin + 1, yMax - 1);
        drawer.glVertex2d(xMax, yMax - 1);
        drawer.glVertex2d(xMax, yMin);
        drawer.glEnd();
        drawer.glLineWidth(1); 

        // Executa as alterações no OpenGL
        drawer.glFlush();
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        
        // Evita a divisão por zero
        if(height == 0) height = 1;
        
        // Calcula a proporção da janela (aspect ratio) da nova janela
        float aspect = (float) width / height;
        
        // Seta o viewport para abranger a janela inteira
        drawer.glViewport(0, 0, width, height);
                
        // Ativa a matriz de projeção
        drawer.glMatrixMode(GL2.GL_PROJECTION);      
        drawer.glLoadIdentity(); // lê a matriz identidade
        
        // Projeção ortogonal
        // true:   aspect >= 1 configura a altura de -1 para 1 : com largura maior
        // false:  aspect < 1 configura a largura de -1 para 1 : com altura maior
        if(width >= height)            
            drawer.glOrtho(xMin * aspect, xMax * aspect, yMin, yMax, zMin, zMax);
        else        
            drawer.glOrtho(xMin, xMax, yMin / aspect, yMax / aspect, zMin, zMax);
                
        // Ativa a matriz de modelagem
        drawer.glMatrixMode(GL2.GL_MODELVIEW);
        drawer.glLoadIdentity(); // lê a matriz identidade
        System.out.println("Reshape: " + width + ", " + height);
    }    
       
    public void dispose(GLAutoDrawable drawable) {}

    private void montarMenu(){

        Label texto = new Label(new float[]{1, 1, 1}, "Bem-vindo ao jogo de Pong!");
        texto.y = yMax - margem - texto.altura;
        texto.centralizarComponente(true, false, true);
        Quadrilatero quadrado = new Quadrilatero(new float[]{1, 1, 1}, 150, 150);
        quadrado.centralizarComponente(true, true, true);

        Camadas.TEXTO.add(texto);
        Camadas.MAIN.add(quadrado);
    }

}
