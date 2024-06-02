package cg.projeto.Motor.Componentes._2D;

import com.jogamp.opengl.GL2;

import cg.projeto.Motor.Renderizador;
import cg.projeto.Motor.Componentes.BaseComponente;

public class QuadrilateroComponente extends BaseComponente<QuadrilateroComponente> {

    public boolean preencher = true;
    public float espessuraBorda = 1;

    public QuadrilateroComponente redimensionarComponente(float novaLargura, float novaAltura){
        this.largura = novaLargura;
        this.altura = novaAltura;
        return this;
    }

    public QuadrilateroComponente preencherComponente(boolean preencher){
        this.preencher = preencher;
        return this;
    }
    
    public QuadrilateroComponente mudarEspessura(float novaEspessura){
        this.espessuraBorda = novaEspessura;
        return this;
    }

    public void desenharComponente(){

        Renderizador.drawer2D.glLineWidth(this.espessuraBorda);

        Renderizador.drawer2D.glColor4f(this.cor[0], this.cor[1], this.cor[2], this.cor[3]);

        Renderizador.drawer2D.glPushMatrix();

        Renderizador.drawer2D.glTranslatef(this.x, this.y, this.z);

        Renderizador.drawer2D.glRotatef(this.rotacao[1], 1, 0, 0);
        Renderizador.drawer2D.glRotatef(this.rotacao[0], 0, 1, 0);
        Renderizador.drawer2D.glRotatef(this.rotacao[2], 0, 0, 1);

        Renderizador.drawer2D.glBegin(preencher ? GL2.GL_QUADS : GL2.GL_LINE_LOOP);
        Renderizador.drawer2D.glVertex3f(0 - this.largura / 2, 0 - this.altura / 2, 0);
        Renderizador.drawer2D.glVertex3f(this.largura / 2, 0 - this.altura / 2, 0);
        Renderizador.drawer2D.glVertex3f(this.largura / 2, this.altura / 2, 0);
        Renderizador.drawer2D.glVertex3f(0 - this.largura / 2, this.altura / 2, 0);
        Renderizador.drawer2D.glEnd();

        Renderizador.drawer2D.glPopMatrix();

        Renderizador.drawer2D.glLineWidth(1);

    }

    public QuadrilateroComponente trocarCor(float r, float g, float b, float a){
        this.cor = new float[]{r, g, b, a};
        return this;
    }

}
