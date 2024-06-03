package cg.projeto.Motor.Componentes._2D;

import com.jogamp.opengl.GL2;

import cg.projeto.Motor.Renderizador;
import cg.projeto.Motor.Componentes.BaseComponente;

public class CirculoComponente extends BaseComponente<CirculoComponente> {
    
    public boolean preencher = true;
    public float espessuraBorda = 1;

    public CirculoComponente redimensionarComponente(float largura, float altura){
        this.largura = largura;
        this.altura = altura;
        return this;
    }

    public CirculoComponente preencherComponente(boolean preencher){
        this.preencher = preencher;
        return this;
    }

    public CirculoComponente mudarEspessura(float novaEspessura){
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

        Renderizador.drawer2D.glScalef(this.largura, this.altura, 1);

        Renderizador.drawer2D.glBegin(preencher ? GL2.GL_POLYGON : GL2.GL_LINE_LOOP);
        for(float i = 0; i < 2 * Math.PI; i += 0.01)
        {
            Renderizador.drawer2D.glVertex3f((float) Math.cos(i), (float) Math.sin(i), 0);
        }
        Renderizador.drawer2D.glEnd();

        Renderizador.drawer2D.glPopMatrix();

        Renderizador.drawer2D.glLineWidth(1);
    }

    public CirculoComponente trocarCor(float r, float g, float b, float a){
        this.cor = new float[]{r, g, b, a};
        return this;
    }

}