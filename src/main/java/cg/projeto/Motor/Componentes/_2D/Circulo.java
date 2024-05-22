package cg.projeto.Motor.Componentes._2D;

import com.jogamp.opengl.GL2;

import cg.projeto.Motor.Tela;
import cg.projeto.Motor.Componentes.ComponenteBase;

public class Circulo extends ComponenteBase<Circulo> {
    
    public float raio = 1;
    public boolean preencher = true;
    public float espessuraBorda = 1;

    public Circulo redimensionarComponente(float novoRaio){
        this.raio = novoRaio;
        return this;
    }

    public Circulo preencherComponente(boolean preencher){
        this.preencher = preencher;
        return this;
    }

    public Circulo mudarEspessura(float novaEspessura){
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

        Tela.drawer2D.glBegin(preencher ? GL2.GL_POLYGON : GL2.GL_LINE_LOOP);
        for(float i = 0; i < 2 * Math.PI; i += 0.01){
            Tela.drawer2D.glVertex3f((float)(this.raio * Math.cos(i)), (float)(this.raio * Math.sin(i)), 0);
        }
        Tela.drawer2D.glEnd();

        Tela.drawer2D.glPopMatrix();

        Tela.drawer2D.glLineWidth(1);
    }

}
