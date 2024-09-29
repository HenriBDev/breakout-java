package breakout.java.Motor;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL4bc;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.gl2.GLUT;

import breakout.java.Main;
import breakout.java.Resolucao;
import breakout.java.Jogo.DebugLoop;
import breakout.java.Jogo.GameLoop;
import breakout.java.Motor.Componentes.BaseComponente;

public class Renderizador implements GLEventListener
{    
    // Elementos UI
    public static float escalaCamera = 1;
    public static float[]
        rotacaoCamera = {0, 0, 0},
        posicaoCamera = {0, 0, 0};
    
    public final static List<BaseComponente> componentesParaRenderizar = new ArrayList<BaseComponente>(); 

    // Conteúdo do jogo
    public static GameLoop jogo;
    public static DebugLoop debug;

    // Renderizadores
    public static GL2 drawer2D;
    public static final GLUT drawer3D = new GLUT();
    public static final TextRenderer textRenderer = new TextRenderer(new Font(Fonte.FAMILY, Fonte.STYLE, Fonte.SIZE));

    public void init(GLAutoDrawable drawable) 
    {
        //Estabelece as coordenadas do SRU (Sistema de Referencia do Universo)
        Resolucao.calcularResolucaoTela();
        
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
        if(Main.DEBUG) debug = new DebugLoop();
        else jogo = new GameLoop();
    }

    public void display(GLAutoDrawable drawable) 
    {
        //limpa a janela com a cor especificada
        drawer2D.glClearColor(0, 0, 0, 1); // Seta cor pra quando limpar fundo
        drawer2D.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT); // Limpa fundo
        drawer2D.glLoadIdentity(); // Limpa resto
        this.limparTela(); // Apaga elementos

        if(Main.DEBUG) componentesParaRenderizar.addAll(debug.desenharDebug());
        else componentesParaRenderizar.addAll(jogo.desenharJogo());

        // Adiciona luz no cenário
        drawer2D.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, new float[]{1f, 1f, 1f, 1}, 0);
        drawer2D.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, new float[]{Resolucao.SRUxCentral, Resolucao.SRUyCentral, Resolucao.SRUzMax, 1}, 0);

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
        drawer2D.glOrtho(Resolucao.SRUxMin, Resolucao.SRUxMax, Resolucao.SRUyMin, Resolucao.SRUyMax, Resolucao.SRUzMin, Resolucao.SRUzMax);
                
        // Ativa a matriz de modelagem
        drawer2D.glMatrixMode(GL2.GL_MODELVIEW);
        drawer2D.glLoadIdentity(); // lê a matriz identidade
        System.out.println("Reshape: " + width + ", " + height);
    }    
       
    public void dispose(GLAutoDrawable drawable) {}

    private void desenharTela()
    {
        for(int index = componentesParaRenderizar.size() - 1; index >= 0; index--)
        {    
            BaseComponente componente = componentesParaRenderizar.get(index);
            
            if(componente.x >= Resolucao.SRUxMin && componente.x <= Resolucao.SRUxMax && componente.y >= Resolucao.SRUyMin && componente.y <= Resolucao.SRUyMax && componente.z >= Resolucao.SRUzMin && componente.z <= Resolucao.SRUzMax)
            componente.montarComponente();
        }
    }
    
    private void limparTela(){ componentesParaRenderizar.clear(); }

}
