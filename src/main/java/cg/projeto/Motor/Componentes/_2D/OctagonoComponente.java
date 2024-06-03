package cg.projeto.Motor.Componentes._2D;

import com.jogamp.opengl.GL2;

import cg.projeto.Motor.Renderizador;

public class OctagonoComponente extends BaseComponente2D<OctagonoComponente> 
{
    public void desenharComponente()
    {
        Renderizador.drawer2D.glLineWidth(this.espessuraBorda);

        Renderizador.drawer2D.glColor4f(this.cor[0], this.cor[1], this.cor[2], this.cor[3]);

        Renderizador.drawer2D.glPushMatrix();

        Renderizador.drawer2D.glTranslatef(this.x, this.y, this.z);

        Renderizador.drawer2D.glRotatef(this.rotacao[1], 1, 0, 0);
        Renderizador.drawer2D.glRotatef(this.rotacao[0], 0, 1, 0);
        Renderizador.drawer2D.glRotatef(this.rotacao[2], 0, 0, 1);

        Renderizador.drawer2D.glScalef(this.largura, this.altura, 1);

        Renderizador.drawer2D.glBegin(preencher ? GL2.GL_POLYGON : GL2.GL_LINE_LOOP);
        Renderizador.drawer2D.glVertex3f(-1, -.5f, 0);
        Renderizador.drawer2D.glVertex3f(-1, .5f, 0);
        Renderizador.drawer2D.glVertex3f(-.5f, 1, 0);
        Renderizador.drawer2D.glVertex3f(.5f, 1, 0);
        Renderizador.drawer2D.glVertex3f(1, .5f, 0);
        Renderizador.drawer2D.glVertex3f(1, -.5f, 0);
        Renderizador.drawer2D.glVertex3f(.5f, -1, 0);
        Renderizador.drawer2D.glVertex3f(-.5f, -1, 0);
        Renderizador.drawer2D.glEnd();

        Renderizador.drawer2D.glPopMatrix();

        Renderizador.drawer2D.glLineWidth(1);
    }
}
