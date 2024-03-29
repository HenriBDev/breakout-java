package cg.projeto.UI._3D.Componentes;

import cg.projeto.UI.Tela;

public class Esfera extends ComponenteBase3D<Esfera> {

    public float raio = 1;

    public Esfera redimensionarComponente(float raio){
        this.raio = raio;
        return this;
    }

    public void desenharComponente(){

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
