package breakout.java.Motor.Componentes._2D;

import com.jogamp.opengl.GL2;

import breakout.java.Motor.Renderizador;

public class QuadrilateroComponente extends BaseComponente2D<QuadrilateroComponente> 
{
    public void desenharComponente()
    {
        Renderizador.drawer2D.glBegin(preencher ? GL2.GL_QUADS : GL2.GL_LINE_LOOP);
        Renderizador.drawer2D.glVertex3f(-0.5f, -0.5f, 0);
        Renderizador.drawer2D.glVertex3f(0.5f, -0.5f, 0);
        Renderizador.drawer2D.glVertex3f(0.5f, 0.5f, 0);
        Renderizador.drawer2D.glVertex3f(-0.5f, 0.5f, 0);
        Renderizador.drawer2D.glEnd();
    }
}
