package cg.projeto.UI._2D.Componentes;

import com.jogamp.opengl.GL2;

import cg.projeto.UI.ComponenteBase;
import cg.projeto.UI.Tela;

public class Octagono extends ComponenteBase<Octagono> {

    public float altura = 1;
    public float largura = 1;
    public boolean preencher = true;
    public float espessuraBorda = 1;

    public Octagono redimensionarComponente(float novaLargura, float novaAltura){
        this.largura = novaLargura;
        this.altura = novaAltura;
        return this;
    }

    public Octagono preencherComponente(boolean preencher){
        this.preencher = preencher;
        return this;
    }

    public Octagono mudarEspessura(float novaEspessura){
        this.espessuraBorda = novaEspessura;
        return this;
    }

    public void desenharComponente(){

        float tercoDaLargura = this.largura / 3, tercoDaAltura = this.altura / 3;

        Tela.drawer2D.glLineWidth(this.espessuraBorda);

        Tela.drawer2D.glColor4f(this.cor[0], this.cor[1], this.cor[2], this.cor[3]);

        Tela.drawer2D.glPushMatrix();

        Tela.drawer2D.glTranslatef(this.x, this.y, this.z);

        Tela.drawer2D.glRotatef(this.rotacao[1], 1, 0, 0);
        Tela.drawer2D.glRotatef(this.rotacao[0], 0, 1, 0);
        Tela.drawer2D.glRotatef(this.rotacao[2], 0, 0, 1);

        Tela.drawer2D.glBegin(preencher ? GL2.GL_POLYGON : GL2.GL_LINE_LOOP);
        Tela.drawer2D.glVertex3f(0 - this.largura / 2 + tercoDaLargura, 0 - this.altura / 2, 0);
        Tela.drawer2D.glVertex3f(0 - this.largura / 2, 0 - this.altura / 2 + tercoDaAltura, 0);
        Tela.drawer2D.glVertex3f(0 - this.largura / 2, 0 - this.altura / 2 + tercoDaAltura * 2, 0);
        Tela.drawer2D.glVertex3f(0 - this.largura / 2 + tercoDaLargura, 0 - this.altura / 2 + tercoDaAltura * 3, 0);
        Tela.drawer2D.glVertex3f(0 - this.largura / 2 + tercoDaLargura * 2, 0 - this.altura / 2 + tercoDaAltura * 3, 0);
        Tela.drawer2D.glVertex3f(0 - this.largura / 2 + tercoDaLargura * 3, 0 - this.altura / 2 + tercoDaAltura * 2, 0);
        Tela.drawer2D.glVertex3f(0 - this.largura / 2 + tercoDaLargura * 3, 0 - this.altura / 2 + tercoDaAltura, 0);
        Tela.drawer2D.glVertex3f(0 - this.largura / 2 + tercoDaLargura * 2, 0 - this.altura / 2, 0);
        Tela.drawer2D.glEnd();

        Tela.drawer2D.glPopMatrix();

        Tela.drawer2D.glLineWidth(1);
    }
    
}
