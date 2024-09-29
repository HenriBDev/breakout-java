package cg.projeto.Motor.Componentes._3D;

import cg.projeto.Motor.Renderizador;

public class EsferaComponente extends BaseComponente3D<EsferaComponente> 
{
    public void desenharComponente()
    {
        if(this.preencher) Renderizador.drawer3D.glutSolidSphere(1, 30, 30);
        else Renderizador.drawer3D.glutWireSphere(1, 30, 30);
    }

}
