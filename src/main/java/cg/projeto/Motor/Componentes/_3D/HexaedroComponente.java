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
        int limite = 1;
        this.textura = new Textura(1);

        Renderizador.drawer2D.glMatrixMode(GL2.GL_TEXTURE);
           Renderizador.drawer2D.glLoadIdentity();
           Renderizador.drawer2D.glScalef(limite/textura.getWidth(), limite/textura.getHeight(), limite);
        Renderizador.drawer2D.glMatrixMode(GL2.GL_MODELVIEW);

        //é geração de textura automática
        this.textura.setAutomatica(true);

        //habilita os filtros
        this.textura.setFiltro(GL2.GL_LINEAR);
        this.textura.setModo(GL2.GL_DECAL);
        this.textura.setWrap(GL2.GL_REPEAT);
        this.textura.gerarTextura(Renderizador.drawer2D, fileName, indice);
        
        //
        // this.textura.setAutomatica(false);
                
        // this.textura.setFiltro(GL2.GL_LINEAR);
        // this.textura.setModo(GL2.GL_DECAL);
        // this.textura.setWrap(GL2.GL_REPEAT);

        // this.textura.gerarTextura(Renderizador.drawer2D, fileName, indice);

        return this;
    }

}
