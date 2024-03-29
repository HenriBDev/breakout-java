package cg.projeto.UI;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.gl2.GLUT;

import cg.projeto.Game.Jogo;
import cg.projeto.UI._2D.Componentes.Texto;
import cg.projeto.UI._2D.Componentes.Octagono;
import cg.projeto.UI._2D.Componentes.Quadrilatero;
import cg.projeto.UI._3D.Componentes.Esfera;
import cg.projeto.UI._3D.Componentes.Hexaedro;

public class Tela implements GLEventListener{    

    // Elementos UI
    public static float limiteSRU = 1000, xMin, xMax, yMin, yMax, zMin, zMax, margem = 20;
    private final List<ComponenteBase> elementosTela = new ArrayList<ComponenteBase>(); 

    // Conteúdo do jogo
    public final Jogo jogo = new Jogo();

    // Renderizadores
    public static GL2 drawer2D;
    public static GLUT drawer3D = new GLUT();
    public static final TextRenderer textRenderer = new TextRenderer(new Font(Fonte.FAMILY, Fonte.STYLE, Fonte.SIZE));
    
    public void init(GLAutoDrawable drawable) {

        //Estabelece as coordenadas do SRU (Sistema de Referencia do Universo)
        xMin = yMin = zMin = 0;
        xMax = limiteSRU;
        yMax = limiteSRU;
        zMax = limiteSRU;
        
        // Configura renderizador
        drawer2D = drawable.getGL().getGL2();
        drawer2D.glEnable(GL2.GL_DEPTH_TEST);
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
        Quadrilatero pontoVermelho = new Quadrilatero(new float[]{1, 0, 0}, 10, 10, new float[]{0, 0, 0}, 1, true);
        pontoVermelho.centralizarComponente(true, true, true);
        elementosTela.add(pontoVermelho);
        
        // Adiciona borda do jogo
        elementosTela.add(new Quadrilatero(xMax / 2, yMax / 2, zMax, new float[]{1, 1, 1}, limiteSRU, limiteSRU, new float[]{0, 0, 0}, 10, false));

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

        Texto texto = new Texto("Bem-vindo ao jogo de Pong!", new float[]{1, 1, 1}, new float[]{0, 0, 0});
        texto.y = yMax - margem - texto.altura;
        texto.centralizarComponente(false, true, true);
        
        Quadrilatero quadrado = new Quadrilatero(0, 0, 100, new float[]{0, 1, 0}, 150, 150, new float[]{0, 0, 45}, 1, true);
        quadrado.centralizarComponente(true, true, false);
        
        Octagono octagono = new Octagono(new float[]{1, 1, 0}, 200, 200, new float[]{0, 0, 0}, 1, false);
        octagono.centralizarComponente(true, true, true);

        Hexaedro hexaedro = new Hexaedro(new float[]{1, 0, 1}, 200, 200, 200, new float[]{45, 45, 0}, false);
        hexaedro.centralizarComponente(true, true, true);

        Esfera esfera = new Esfera(new float[]{1, 0, 0}, 75, new float[]{45, 45, 0}, false);
        esfera.centralizarComponente(true, true, true);

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
                componente.desenharElemento();
        }
    }
    
    private void limparTela(){ this.elementosTela.clear(); }

}
