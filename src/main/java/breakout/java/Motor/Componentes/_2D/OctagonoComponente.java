package breakout.java.Motor.Componentes._2D;

import com.jogamp.opengl.GL2;

import breakout.java.Motor.Renderizador;

public class OctagonoComponente extends BaseComponente2D<OctagonoComponente> 
{
    public void desenharComponente()
    {
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
    }
}
