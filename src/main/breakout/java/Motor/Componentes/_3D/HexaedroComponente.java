package cg.projeto.Motor.Componentes._3D;

import cg.projeto.Motor.Renderizador;

public class HexaedroComponente extends BaseComponente3D<HexaedroComponente> {

    public void desenharComponente()
    {
        if(this.preencher) Renderizador.drawer3D.glutSolidCube(1);
        else Renderizador.drawer3D.glutWireCube(1);
    }
}
