package cg.projeto.UI._3D.Componentes;

import cg.projeto.Main;
import cg.projeto.UI.Tela;

public class Hexaedro extends ComponenteBase3D {

    public float altura = 1;
    public float largura = 1;
    public float comprimento = 1;
    
    public Hexaedro(
        float x, float y, float z, float[] cor, 
        float altura, float largura, float comprimento, 
        float[] rotacao, boolean preencher
    ){
        this.x = x;
        this.y = y;
        this.z = z;
        this.cor = cor;
        this.altura = altura;
        this.largura = largura;
        this.comprimento = comprimento;
        this.rotacao = rotacao;
        this.preencher = preencher;
    }
    
    public Hexaedro(
        float[] cor, 
        float altura, float largura, float comprimento, 
        float[] rotacao, boolean preencher
    ){
        this.cor = cor;
        this.altura = altura;
        this.largura = largura;
        this.comprimento = comprimento;
        this.rotacao = rotacao;
        this.preencher = preencher;
    }

    public void desenharElemento(){

        Tela.drawer2D.glColor3f(this.cor[0], this.cor[1], this.cor[2]);

        Tela.drawer2D.glPushMatrix();

        Tela.drawer2D.glTranslatef(this.x, this.y, Tela.zMax * -1 + this.z);

        Tela.drawer2D.glRotatef(this.rotacao[1], 1, 0, 0);
        Tela.drawer2D.glRotatef(this.rotacao[0], 0, 1, 0);
        Tela.drawer2D.glRotatef(this.rotacao[2], 0, 0, 1);

        Tela.drawer2D.glScalef(this.largura, this.altura, this.comprimento);

        if(this.preencher) Tela.drawer3D.glutSolidCube(1);
        else Tela.drawer3D.glutWireCube(1);

        Tela.drawer2D.glPopMatrix();

    }

}
