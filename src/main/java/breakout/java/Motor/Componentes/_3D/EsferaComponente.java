package breakout.java.Motor.Componentes._3D;

import breakout.java.Motor.Renderizador;

public class EsferaComponente extends BaseComponente3D<EsferaComponente> 
{
    public void desenharComponente()
    {
        if(this.preencher) Renderizador.drawer3D.glutSolidSphere(1, 30, 30);
        else Renderizador.drawer3D.glutWireSphere(1, 30, 30);
    }

}
