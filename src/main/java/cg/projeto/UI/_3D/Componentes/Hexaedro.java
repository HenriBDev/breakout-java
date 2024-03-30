package cg.projeto.UI._3D.Componentes;

import cg.projeto.UI.Tela;

public class Hexaedro extends ComponenteBase3D<Hexaedro> {

    public float altura = 1;
    public float largura = 1;
    public float comprimento = 1;

    public Hexaedro redimensionarComponente(float novaLargura, float novaAltura, float novoComprimento){
        this.largura = novaLargura;
        this.altura = novaAltura;
        this.comprimento = novoComprimento;
        return this;
    }

    public void desenharComponente(){

        Tela.drawer2D.glColor4f(this.cor[0], this.cor[1], this.cor[2], this.cor[3]);
        
        Tela.drawer2D.glPushMatrix();
        
        Tela.drawer2D.glTranslatef(this.x, this.y, this.z);
        
        Tela.drawer2D.glRotatef(this.rotacao[1], 1, 0, 0);
        Tela.drawer2D.glRotatef(this.rotacao[0], 0, 1, 0);
        Tela.drawer2D.glRotatef(this.rotacao[2], 0, 0, 1);
        
        Tela.drawer2D.glScalef(this.largura, this.altura, this.comprimento);
        
        if(this.preencher) Tela.drawer3D.glutSolidCube(1);
        else Tela.drawer3D.glutWireCube(1);

        Tela.drawer2D.glPopMatrix();

    }

}
