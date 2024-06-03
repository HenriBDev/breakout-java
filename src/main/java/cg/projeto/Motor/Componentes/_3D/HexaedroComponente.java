package cg.projeto.Motor.Componentes._3D;
import com.jogamp.opengl.GL2;
import cg.projeto.Motor.Renderizador;
import cg.projeto.Motor.Textura;

public class HexaedroComponente extends BaseComponente3D<HexaedroComponente> {

    public float profundidade = 1;

    public HexaedroComponente redimensionarComponente(float novaLargura, float novaAltura, float novaProfundidade){
        this.largura = novaLargura;
        this.altura = novaAltura;
        this.profundidade = novaProfundidade;
        return this;
    }

    public void desenharComponente(){

        Renderizador.drawer2D.glColor4f(this.cor[0], this.cor[1], this.cor[2], this.cor[3]);
        
        Renderizador.drawer2D.glPushMatrix();
        
        Renderizador.drawer2D.glTranslatef(this.x, this.y, this.z);
        
        Renderizador.drawer2D.glRotatef(this.rotacao[1], 1, 0, 0);
        Renderizador.drawer2D.glRotatef(this.rotacao[0], 0, 1, 0);
        Renderizador.drawer2D.glRotatef(this.rotacao[2], 0, 0, 1);
        
        Renderizador.drawer2D.glScalef(this.largura, this.altura, this.profundidade);
        
        if(this.preencher) Renderizador.drawer3D.glutSolidCube(1);
        else Renderizador.drawer3D.glutWireCube(1);

        Renderizador.drawer2D.glPopMatrix();

    }

    public HexaedroComponente trocarCor(float r, float g, float b, float a){
        this.cor = new float[]{r, g, b, a};
        return this;
    }

    public HexaedroComponente alterarTextura(String fileName, int indice)
    {   
        float limite = 1;
        this.textura = new Textura(1);

        Renderizador.drawer2D.glPushMatrix();
        Renderizador.drawer2D.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        Renderizador.drawer2D.glLoadIdentity();

        //é geração de textura automática
        this.textura.setAutomatica(true);



        //habilita os filtros
        this.textura.setFiltro(GL2.GL_LINEAR);
        this.textura.setModo(GL2.GL_DECAL);
        this.textura.setWrap(GL2.GL_REPEAT);
        Renderizador.drawer2D.glEnable(GL2.GL_TEXTURE_2D);
        this.textura.gerarTextura(Renderizador.drawer2D, fileName, indice);

        Renderizador.drawer2D.glBegin(GL2.GL_QUADS);
        // Top-Left
        Renderizador.drawer2D.glTexCoord2f(0.0f, 1.0f);
        Renderizador.drawer2D.glVertex3f(-0.5f, 0.5f, 0.0f);
        // Top-Right
        Renderizador.drawer2D.glTexCoord2f(1.0f, 1.0f);
        Renderizador.drawer2D.glVertex3f(0.5f, 0.5f, 0.0f);
        // Bottom-Right
        Renderizador.drawer2D.glTexCoord2f(1.0f, 0.0f);
        Renderizador.drawer2D.glVertex3f(0.5f, -0.5f, 0.0f);
        // Bottom-Left
        Renderizador.drawer2D.glTexCoord2f(0.0f, 0.0f);
        Renderizador.drawer2D.glVertex3f(-0.5f, -0.5f, 0.0f);
        Renderizador.drawer2D.glEnd();

        Renderizador.drawer2D.glPopMatrix();
        Renderizador.drawer2D.glFlush();
        
        //
        // this.textura.setAutomatica(false);
                
        // this.textura.setFiltro(GL2.GL_LINEAR);
        // this.textura.setModo(GL2.GL_DECAL);
        // this.textura.setWrap(GL2.GL_REPEAT);

        // this.textura.gerarTextura(Renderizador.drawer2D, fileName, indice);

        return this;
    }

}
