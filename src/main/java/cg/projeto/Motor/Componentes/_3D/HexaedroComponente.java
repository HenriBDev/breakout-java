package cg.projeto.Motor.Componentes._3D;

import cg.projeto.Motor.Renderizador;

public class HexaedroComponente extends BaseComponente3D<HexaedroComponente> {

    public void desenharComponente()
    {
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
}
