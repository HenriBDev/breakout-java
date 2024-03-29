package cg.projeto.UI._3D.Componentes;

import cg.projeto.Main;
import cg.projeto.UI.Tela;

public class Esfera extends ComponenteBase3D {

    public float raio = 1;
    public float largura = 1;
    public float comprimento = 1;
    
    public Esfera(
        float x, float y, float z, float[] cor, float raio, 
        float[] rotacao, boolean preencher
    ){
        this.x = x;
        this.y = y;
        this.z = z;
        this.cor = cor;
        this.raio = raio;
        this.rotacao = rotacao;
        this.preencher = preencher;
    }
    
    public Esfera(
        float[] cor, float raio, 
        float[] rotacao, boolean preencher
    ){
        this.cor = cor;
        this.raio = raio;
        this.rotacao = rotacao;
        this.preencher = preencher;
    }

    public void desenharElemento(){

        Tela.drawer2D.glColor3f(this.cor[0], this.cor[1], this.cor[2]);

        Tela.drawer2D.glPushMatrix();

        Tela.drawer2D.glTranslatef(this.x, this.y, Tela.zMax * -1 + this.z);

        Tela.drawer2D.glRotatef(rotacao[1], 1, 0, 0);
        Tela.drawer2D.glRotatef(rotacao[0], 0, 1, 0);
        Tela.drawer2D.glRotatef(rotacao[2], 0, 0, 1);

        if(this.preencher) Tela.drawer3D.glutSolidSphere(this.raio, 30, 30);
        else Tela.drawer3D.glutWireSphere(this.raio, 30, 30);

        Tela.drawer2D.glPopMatrix();

    }

}
