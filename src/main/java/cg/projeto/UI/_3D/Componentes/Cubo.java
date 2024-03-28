package cg.projeto.UI._3D.Componentes;

import cg.projeto.UI.Tela;

public class Cubo extends ComponenteBase3D {

    public float comprimento;
    
    public Cubo(float x, float y, float z, float[] cor, float altura, float largura, float comprimento){
        this.x = x;
        this.y = y;
        this.z = z;
        this.cor = cor;
        this.altura = altura;
        this.largura = largura;
        this.comprimento = comprimento;
    }

    public void desenharElemento(){
        Tela.drawer2D.glColor3f(this.cor[0], this.cor[1], this.cor[2]);
        Tela.drawer2D.glPushMatrix();
        // Tela.drawer2D.glScalef(this.largura, this.altura, this.comprimento);
        Tela.drawer2D.glTranslatef(Tela.xMax / 2, Tela.yMax / 2, 0);
        Tela.drawer3D.glutSolidCube(200);
        Tela.drawer2D.glPopMatrix();
    }

}
