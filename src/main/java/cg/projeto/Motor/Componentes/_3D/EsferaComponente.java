package cg.projeto.Motor.Componentes._3D;
import com.jogamp.opengl.GL2;
import cg.projeto.Motor.Renderizador;
import cg.projeto.Motor.Textura;

public class EsferaComponente extends BaseComponente3D<EsferaComponente> {

    public float raio = 1;

    public EsferaComponente redimensionarComponente(float raio){
        this.raio = raio;
        this.altura = raio * 2;
        this.largura = raio * 2;
        return this;
    }

    public void desenharComponente(){

        Renderizador.drawer2D.glColor4f(this.cor[0], this.cor[1], this.cor[2], this.cor[3]);

        Renderizador.drawer2D.glPushMatrix();

        Renderizador.drawer2D.glTranslatef(this.x, this.y, this.z);

        Renderizador.drawer2D.glRotatef(this.rotacao[1], 1, 0, 0);
        Renderizador.drawer2D.glRotatef(this.rotacao[0], 0, 1, 0);
        Renderizador.drawer2D.glRotatef(this.rotacao[2], 0, 0, 1);

        if(this.preencher) Renderizador.drawer3D.glutSolidSphere(this.raio, 30, 30);
        else Renderizador.drawer3D.glutWireSphere(this.raio, 30, 30);

        Renderizador.drawer2D.glPopMatrix();

    }

    public EsferaComponente trocarCor(float r, float g, float b, float a)
    {
        this.cor = new float[]{r, g, b, a};
        return this;
    }

}
