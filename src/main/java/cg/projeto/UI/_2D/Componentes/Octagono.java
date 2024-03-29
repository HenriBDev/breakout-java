package cg.projeto.UI._2D.Componentes;

import com.jogamp.opengl.GL2;

import cg.projeto.UI.Tela;

public class Octagono extends ComponenteBase2D<Octagono> {

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

        Tela.drawer2D.glColor3f(this.cor[0], this.cor[1], this.cor[2]);

        Tela.drawer2D.glPushMatrix();

        Tela.drawer2D.glTranslatef(this.x, this.y, Tela.zMax * -1 + this.z);

        Tela.drawer2D.glRotatef(this.rotacao[1], 1, 0, 0);
        Tela.drawer2D.glRotatef(this.rotacao[0], 0, 1, 0);
        Tela.drawer2D.glRotatef(this.rotacao[2], 0, 0, 1);

        Tela.drawer2D.glBegin(preencher ? GL2.GL_POLYGON : GL2.GL_LINE_LOOP);
        Tela.drawer2D.glVertex3f(Tela.xMin - this.largura / 2 + tercoDaLargura, Tela.yMin - this.altura / 2, Tela.zMin);
        Tela.drawer2D.glVertex3f(Tela.xMin - this.largura / 2, Tela.yMin - this.altura / 2 + tercoDaAltura, Tela.zMin);
        Tela.drawer2D.glVertex3f(Tela.xMin - this.largura / 2, Tela.yMin - this.altura / 2 + tercoDaAltura * 2, Tela.zMin);
        Tela.drawer2D.glVertex3f(Tela.xMin - this.largura / 2 + tercoDaLargura, Tela.yMin - this.altura / 2 + tercoDaAltura * 3, Tela.zMin);
        Tela.drawer2D.glVertex3f(Tela.xMin - this.largura / 2 + tercoDaLargura * 2, Tela.yMin - this.altura / 2 + tercoDaAltura * 3, Tela.zMin);
        Tela.drawer2D.glVertex3f(Tela.xMin - this.largura / 2 + tercoDaLargura * 3, Tela.yMin - this.altura / 2 + tercoDaAltura * 2, Tela.zMin);
        Tela.drawer2D.glVertex3f(Tela.xMin - this.largura / 2 + tercoDaLargura * 3, Tela.yMin - this.altura / 2 + tercoDaAltura, Tela.zMin);
        Tela.drawer2D.glVertex3f(Tela.xMin - this.largura / 2 + tercoDaLargura * 2, Tela.yMin - this.altura / 2, Tela.zMin);
        Tela.drawer2D.glEnd();

        Tela.drawer2D.glPopMatrix();

        Tela.drawer2D.glLineWidth(1);
    }
    
}
