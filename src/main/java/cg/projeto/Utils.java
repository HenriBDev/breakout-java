package cg.projeto;

import com.jogamp.opengl.GL2;

public class Utils {

    static void desenharQuadrado(int largura, int altura, int x, int y, int[] color){
        Cena.drawer.glColor3f(color[0], color[1], color[2]);
        Cena.drawer.glBegin(GL2.GL_QUADS);
        Cena.drawer.glVertex2d(x, y);
        Cena.drawer.glVertex2d(x + largura, y);
        Cena.drawer.glVertex2d(x + largura, y + altura);
        Cena.drawer.glVertex2d(x, y + altura);
        Cena.drawer.glEnd();
    }
}
