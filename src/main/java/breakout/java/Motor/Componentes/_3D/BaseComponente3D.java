package breakout.java.Motor.Componentes._3D;

import breakout.java.Motor.Componentes.BaseComponente;

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
