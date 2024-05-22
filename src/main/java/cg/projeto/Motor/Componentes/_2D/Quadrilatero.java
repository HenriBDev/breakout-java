package cg.projeto.Motor.Componentes._2D;

import com.jogamp.opengl.GL2;

import cg.projeto.Motor.Tela;
import cg.projeto.Motor.Componentes.ComponenteBase;

public class Quadrilatero extends ComponenteBase<Quadrilatero> {

    public boolean preencher = true;
    public float espessuraBorda = 1;

    public Quadrilatero redimensionarComponente(float novaLargura, float novaAltura){
        this.largura = novaLargura;
        this.altura = novaAltura;
        return this;
    }

    public Quadrilatero preencherComponente(boolean preencher){
        this.preencher = preencher;
        return this;
    }
    
    public Quadrilatero mudarEspessura(float novaEspessura){
        this.espessuraBorda = novaEspessura;
        return this;
    }

    public void desenharComponente(){

        Tela.drawer2D.glLineWidth(this.espessuraBorda);

        Tela.drawer2D.glColor4f(this.cor[0], this.cor[1], this.cor[2], this.cor[3]);

        Tela.drawer2D.glPushMatrix();

        Tela.drawer2D.glTranslatef(this.x, this.y, this.z);

        Tela.drawer2D.glRotatef(this.rotacao[1], 1, 0, 0);
        Tela.drawer2D.glRotatef(this.rotacao[0], 0, 1, 0);
        Tela.drawer2D.glRotatef(this.rotacao[2], 0, 0, 1);

        Tela.drawer2D.glBegin(preencher ? GL2.GL_QUADS : GL2.GL_LINE_LOOP);
        Tela.drawer2D.glVertex3f(0 - this.largura / 2, 0 - this.altura / 2, 0);
        Tela.drawer2D.glVertex3f(this.largura / 2, 0 - this.altura / 2, 0);
        Tela.drawer2D.glVertex3f(this.largura / 2, this.altura / 2, 0);
        Tela.drawer2D.glVertex3f(0 - this.largura / 2, this.altura / 2, 0);
        Tela.drawer2D.glEnd();

        Tela.drawer2D.glPopMatrix();

        Tela.drawer2D.glLineWidth(1);

    }

}
