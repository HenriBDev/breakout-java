package breakout.java.Motor.Componentes._3D;

import breakout.java.Motor.Renderizador;

public class HexaedroComponente extends BaseComponente3D<HexaedroComponente> {

    public void desenharComponente()
    {
        if(this.preencher) Renderizador.drawer3D.glutSolidCube(1);
        else Renderizador.drawer3D.glutWireCube(1);
    }
}
