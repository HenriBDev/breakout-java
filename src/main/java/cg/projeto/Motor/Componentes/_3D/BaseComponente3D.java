package cg.projeto.Motor.Componentes._3D;

import cg.projeto.Motor.Componentes.BaseComponente;

public abstract class BaseComponente3D<tipoComponente> extends BaseComponente<tipoComponente> {
    
    public boolean preencher = true;

    public tipoComponente preencherComponente(boolean preencher){
        this.preencher = preencher;
        return (tipoComponente) this;
    }

}
