package cg.projeto.UI._3D.Componentes;

import cg.projeto.UI.ComponenteBase;

public abstract class ComponenteBase3D<tipoComponente> extends ComponenteBase<tipoComponente> {
    
    public boolean preencher = true;

    public tipoComponente preencherComponente(boolean preencher){
        this.preencher = preencher;
        return (tipoComponente) this;
    }

}
