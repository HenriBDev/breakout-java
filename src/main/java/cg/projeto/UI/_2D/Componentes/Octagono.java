package cg.projeto.UI._2D.Componentes;

import com.jogamp.opengl.GL2;

import cg.projeto.UI.ComponenteBase;
import cg.projeto.UI.Tela;

public class Octagono extends ComponenteBase {

    public boolean preencher = true;
    public float espessuraBorda = 1;
    
    public Octagono(float x, float y, float[] cor, float altura, float largura, float espessuraBorda, boolean preencher){
        this.x = x;
        this.y = y;
        this.cor = cor;
        this.altura = altura;
        this.largura = largura;
        this.espessuraBorda = espessuraBorda;
        this.preencher = preencher;
    }
    
    public Octagono(float[] cor, float altura, float largura, float espessuraBorda, boolean preencher){
        this.cor = cor;
        this.altura = altura;
        this.largura = largura;
        this.espessuraBorda = espessuraBorda;
        this.preencher = preencher;
    }

    public void desenharElemento(){
        float tercoDaLargura = this.largura / 3, tercoDaAltura = this.altura / 3;
        Tela.drawer2D.glLineWidth(this.espessuraBorda);
        Tela.drawer2D.glColor3f(this.cor[0], this.cor[1], this.cor[2]);
        Tela.drawer2D.glBegin(preencher ? GL2.GL_POLYGON : GL2.GL_LINE_LOOP);
        Tela.drawer2D.glVertex2f(this.x - this.largura / 2 + tercoDaLargura, this.y - this.altura / 2);
        Tela.drawer2D.glVertex2f(this.x - this.largura / 2, this.y + tercoDaAltura);
        Tela.drawer2D.glVertex2f(this.x - this.largura / 2, this.y + tercoDaAltura * 2);
        Tela.drawer2D.glVertex2f(this.x - this.largura / 2 + tercoDaLargura, this.y - this.altura / 2 + tercoDaAltura * 3);
        Tela.drawer2D.glVertex2f(this.x - this.largura / 2 + tercoDaLargura * 2, this.y - this.altura / 2 + tercoDaAltura * 3);
        Tela.drawer2D.glVertex2f(this.x - this.largura / 2 + tercoDaLargura * 3, this.y - this.altura / 2 + tercoDaAltura * 2);
        Tela.drawer2D.glVertex2f(this.x - this.largura / 2 + tercoDaLargura * 3, this.y - this.altura / 2 + tercoDaAltura);
        Tela.drawer2D.glVertex2f(this.x - this.largura / 2 + tercoDaLargura * 2, this.y - this.altura / 2);
        Tela.drawer2D.glEnd();
        Tela.drawer2D.glLineWidth(1);
    }
    
}
