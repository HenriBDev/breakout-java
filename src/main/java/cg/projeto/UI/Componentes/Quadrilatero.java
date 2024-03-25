package cg.projeto.UI.Componentes;

import com.jogamp.opengl.GL2;

import cg.projeto.UI.Tela;

public class Quadrilatero extends ComponenteBase {
    
    public Quadrilatero(float x, float y, float[] cor, float altura, float largura){
        this.x = x;
        this.y = y;
        this.cor = cor;
        this.altura = altura;
        this.largura = largura;
    }
    
    public Quadrilatero(float[] cor, float altura, float largura){
        this.x = 0;
        this.y = 0;
        this.cor = cor;
        this.altura = altura;
        this.largura = largura;
    }

    public void desenharElemento(){
        Tela.drawer.glColor3f(this.cor[0], this.cor[1], this.cor[2]);
        Tela.drawer.glBegin(GL2.GL_QUADS);
        Tela.drawer.glVertex2f(this.x, this.y);
        Tela.drawer.glVertex2f(this.x + (float)this.largura, this.y);
        Tela.drawer.glVertex2f(this.x + (float)this.largura, this.y + (float)this.altura);
        Tela.drawer.glVertex2f(this.x, this.y + (float)this.altura);
        Tela.drawer.glEnd();
    }

}
