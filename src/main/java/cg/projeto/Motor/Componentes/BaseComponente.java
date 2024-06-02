package cg.projeto.Motor.Componentes;

import cg.projeto.Resolucao;

public abstract class BaseComponente<tipoComponente> {
    
    public float x = 0;
    public float y = 0;
    public float z = 0;
    public float largura = 0;
    public float altura = 0;
    public float[] cor = new float[]{1, 1, 1, 1};
    public float[] rotacao = new float[]{0, 0, 0};

    public abstract void desenharComponente();

    public tipoComponente moverComponente(float novoX, float novoY, float novoZ){
        this.x = novoX;
        this.y = novoY;
        this.z = novoZ;
        return (tipoComponente) this;
    }

    public tipoComponente rotacionarComponente(float anguloX, float anguloY, float anguloZ){
        this.rotacao = new float[]{anguloX, anguloY, anguloZ};
        return (tipoComponente) this;
    }

    public tipoComponente centralizarComponente(boolean vertical, boolean horizontal, boolean eixoZ){
        return this.moverComponente(
            horizontal ? Resolucao.SRUxCentral : this.x, 
            vertical ? Resolucao.SRUyCentral : this.y,
            eixoZ ? Resolucao.SRUzCentral : this.z
        );
    }

    public boolean colidiuComComponenteHorizontalmente(BaseComponente componenteAColidir)
    {
        float pontaDireitaComponenteAColidir = componenteAColidir.x + componenteAColidir.largura/2;
        float pontaEsquerdaComponenteAColidir = componenteAColidir.x - componenteAColidir.largura/2;
        float pontaDireitaComponenteAtual = this.x + this.largura/2;
        float pontaEsquerdaComponenteAtual = this.x - this.largura/2;

        return (
            pontaDireitaComponenteAtual >= pontaEsquerdaComponenteAColidir &&
            pontaEsquerdaComponenteAtual <= pontaDireitaComponenteAColidir
        ) || (
            pontaEsquerdaComponenteAtual <= pontaDireitaComponenteAColidir &&
            pontaDireitaComponenteAtual >= pontaEsquerdaComponenteAColidir
        );
    }
    
    public boolean colidiuComComponenteVerticalmente(BaseComponente componenteAColidir)
    {
        float pontaCimaComponenteAColidir = componenteAColidir.y + componenteAColidir.altura/2;
        float pontaBaixoComponenteAColidir = componenteAColidir.y - componenteAColidir.altura/2;
        float pontaCimaComponenteAtual = this.y + this.altura/2;
        float pontaBaixoComponenteAtual = this.y - this.altura/2;

        return (
            pontaCimaComponenteAtual >= pontaBaixoComponenteAColidir &&
            pontaBaixoComponenteAtual <= pontaCimaComponenteAColidir
        ) || (
            pontaBaixoComponenteAtual <= pontaCimaComponenteAColidir &&
            pontaCimaComponenteAtual >= pontaBaixoComponenteAColidir
        );
    }

    public boolean colidiuComComponente(BaseComponente componenteAColidir)
    {
        return colidiuComComponenteVerticalmente(componenteAColidir) && colidiuComComponenteHorizontalmente(componenteAColidir);
    }
}
