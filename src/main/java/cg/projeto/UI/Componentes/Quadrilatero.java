package cg.projeto.UI.Componentes;

import com.jogamp.opengl.GL2;

import cg.projeto.UI.Tela;

public class Quadrilatero extends ComponenteBase {

    public boolean preencher = true;
    public float espessuraBorda = 1;
    
    public Quadrilatero(float x, float y, float[] cor, float altura, float largura, float espessuraBorda, boolean preencher){
        this.x = x;
        this.y = y;
        this.cor = cor;
        this.altura = altura;
        this.largura = largura;
        this.espessuraBorda = espessuraBorda;
        this.preencher = preencher;
    }
    
    public Quadrilatero(float[] cor, float altura, float largura, float espessuraBorda, boolean preencher){
        this.cor = cor;
        this.altura = altura;
        this.largura = largura;
        this.espessuraBorda = espessuraBorda;
        this.preencher = preencher;
    }

    public void desenharElemento(){
        Tela.drawer.glLineWidth(this.espessuraBorda);
        Tela.drawer.glColor3f(this.cor[0], this.cor[1], this.cor[2]);
        Tela.drawer.glBegin(preencher ? GL2.GL_QUADS : GL2.GL_LINE_LOOP);
        Tela.drawer.glVertex2f(this.x, this.y);
        Tela.drawer.glVertex2f(this.x + (float)this.largura, this.y);
        Tela.drawer.glVertex2f(this.x + (float)this.largura, this.y + (float)this.altura);
        Tela.drawer.glVertex2f(this.x, this.y + (float)this.altura);
        Tela.drawer.glEnd();
        Tela.drawer.glLineWidth(1);
    }

}
