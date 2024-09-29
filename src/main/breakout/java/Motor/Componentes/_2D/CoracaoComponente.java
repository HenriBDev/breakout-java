package cg.projeto.Motor.Componentes._2D;

import com.jogamp.opengl.GL2;

import cg.projeto.Motor.Renderizador;

public class CoracaoComponente extends BaseComponente2D<CoracaoComponente> 
{
    public void desenharComponente()
    {
        Renderizador.drawer2D.glBegin(preencher ? GL2.GL_POLYGON : GL2.GL_LINE_LOOP);
        for(double i = 1.25 * Math.PI; i > 0; i -= 0.01)
        {
            Renderizador.drawer2D.glVertex3f(
                (float) ((Math.cos(i) * .5) - .5), 
                (float) ((Math.sin(i) * .5) + .25), 
                0
            );
        }
        for(float i = 0; i < 1.25 * Math.PI; i += 0.01)
        {
            Renderizador.drawer2D.glVertex3f(
                (float) ((Math.cos(i) * -.5) + .5), 
                (float) ((Math.sin(i) * .5) + .25), 
                0
            );
        }
        Renderizador.drawer2D.glVertex3f(0, -1, 0);
        Renderizador.drawer2D.glEnd();
    }
}
