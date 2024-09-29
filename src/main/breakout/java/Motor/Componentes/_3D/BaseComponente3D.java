package cg.projeto.Motor.Componentes._3D;

import cg.projeto.Motor.Componentes.BaseComponente;

public abstract class BaseComponente3D<tipoComponente> extends BaseComponente<tipoComponente> 
{
    public tipoComponente redimensionarComponente(float novaLargura, float novaAltura, float novaProfundidade)
    {
        this.largura = novaLargura;
        this.altura = novaAltura;
        this.profundidade = novaProfundidade;
        return (tipoComponente) this;
    }
}
