package cg.projeto;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

public class Cena implements GLEventListener{    

    private float xMin, xMax, yMin, yMax, zMin, zMax;
    static GL2 drawer;
    
    public void init(GLAutoDrawable drawable) {

        //dados iniciais da cena
        drawer = drawable.getGL().getGL2();

        //Estabelece as coordenadas do SRU (Sistema de Referencia do Universo)
        xMin = yMin = zMin = -100;
        xMax = yMax = zMax = 100;

        drawer.glEnable(GL2.GL_DEPTH_TEST);
    }

    public void display(GLAutoDrawable drawable) {  

        //define a cor da janela (R, G, G, alpha)
        drawer.glClearColor(0, 0, 0, 1);    

        //limpa a janela com a cor especificada
        drawer.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        drawer.glLoadIdentity(); //lê a matriz identidade
        
        /*
            desenho da cena        
        *
        */

        drawer.glFlush();
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        
        //evita a divisão por zero
        if(height == 0) height = 1;
        
        //calcula a proporção da janela (aspect ratio) da nova janela
        float aspect = (float) width / height;
        
        //seta o viewport para abranger a janela inteira
        drawer.glViewport(0, 0, width, height);
                
        //ativa a matriz de projeção
        drawer.glMatrixMode(GL2.GL_PROJECTION);      
        drawer.glLoadIdentity(); //lê a matriz identidade
        
        //Projeção ortogonal
        //true:   aspect >= 1 configura a altura de -1 para 1 : com largura maior
        //false:  aspect < 1 configura a largura de -1 para 1 : com altura maior
        if(width >= height)            
            drawer.glOrtho(xMin * aspect, xMax * aspect, yMin, yMax, zMin, zMax);
        else        
            drawer.glOrtho(xMin, xMax, yMin / aspect, yMax / aspect, zMin, zMax);
                
        //ativa a matriz de modelagem
        drawer.glMatrixMode(GL2.GL_MODELVIEW);
        drawer.glLoadIdentity(); //lê a matriz identidade
        System.out.println("Reshape: " + width + ", " + height);
    }    
       
    public void dispose(GLAutoDrawable drawable) {}         
}
