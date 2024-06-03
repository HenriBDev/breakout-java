package cg.projeto.Motor.Componentes._3D;

import cg.projeto.Motor.Componentes.BaseComponente;

public abstract class BaseComponente3D<tipoComponente> extends BaseComponente<tipoComponente> {
    
    public float profundidade = 1;
    public boolean preencher = true;

    public tipoComponente preencherComponente(boolean preencher){
        this.preencher = preencher;
        return (tipoComponente) this;
    }

    public tipoComponente trocarCor(float r, float g, float b, float a){
        this.cor = new float[]{r, g, b, a};
        return (tipoComponente) this;
    }

}
