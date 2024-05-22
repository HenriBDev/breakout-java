package cg.projeto.Motor.Componentes._3D;

import cg.projeto.Motor.Componentes.ComponenteBase;

public abstract class ComponenteBase3D<tipoComponente> extends ComponenteBase<tipoComponente> {
    
    public boolean preencher = true;

    public tipoComponente preencherComponente(boolean preencher){
        this.preencher = preencher;
        return (tipoComponente) this;
    }

}
