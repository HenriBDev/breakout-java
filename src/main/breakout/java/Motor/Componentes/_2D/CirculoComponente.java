package cg.projeto.Motor.Componentes._2D;

import com.jogamp.opengl.GL2;

import cg.projeto.Motor.Renderizador;

public class CirculoComponente extends BaseComponente2D<CirculoComponente> 
{
    public void desenharComponente()
    {
        Renderizador.drawer2D.glBegin(preencher ? GL2.GL_POLYGON : GL2.GL_LINE_LOOP);
        for(float i = 0; i < 2 * Math.PI; i += 0.01)
        {
            Renderizador.drawer2D.glVertex3f((float) Math.cos(i), (float) Math.sin(i), 0);
        }
        Renderizador.drawer2D.glEnd();
    }
}
