package cg.projeto.UI.Componentes;

import com.jogamp.opengl.GL2;

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
        Tela.drawer.glLineWidth(this.espessuraBorda);
        Tela.drawer.glColor3f(this.cor[0], this.cor[1], this.cor[2]);
        Tela.drawer.glBegin(preencher ? GL2.GL_POLYGON : GL2.GL_LINE_LOOP);
        Tela.drawer.glVertex2f(this.x + tercoDaLargura, this.y);
        Tela.drawer.glVertex2f(this.x, this.y + tercoDaAltura);
        Tela.drawer.glVertex2f(this.x, this.y + tercoDaAltura * 2);
        Tela.drawer.glVertex2f(this.x + tercoDaLargura, this.y + tercoDaAltura * 3);
        Tela.drawer.glVertex2f(this.x + tercoDaLargura * 2, this.y + tercoDaAltura * 3);
        Tela.drawer.glVertex2f(this.x + tercoDaLargura * 3, this.y + tercoDaAltura * 2);
        Tela.drawer.glVertex2f(this.x + tercoDaLargura * 3, this.y + tercoDaAltura);
        Tela.drawer.glVertex2f(this.x + tercoDaLargura * 2, this.y);
        Tela.drawer.glEnd();
        Tela.drawer.glLineWidth(1);
    }
    
}
