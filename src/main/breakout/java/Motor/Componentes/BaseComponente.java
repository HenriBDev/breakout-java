package cg.projeto.Motor.Componentes;

import cg.projeto.Resolucao;
import cg.projeto.Motor.Renderizador;

public abstract class BaseComponente<tipoComponente> {
    
    public float x = 0;
    public float y = 0;
    public float z = 0;
    public float largura = 0;
    public float altura = 0;
    public float profundidade = 0;
    public float[] cor = new float[]{1, 1, 1, 1};
    public float[] rotacao = new float[]{0, 0, 0};
    public boolean preencher = true;
    public float espessuraBorda = 1;

    public abstract void desenharComponente();

    public void montarComponente()
    {
        Renderizador.drawer2D.glLineWidth(this.espessuraBorda);

        Renderizador.drawer2D.glColor4f(this.cor[0], this.cor[1], this.cor[2], this.cor[3]);
        
        Renderizador.drawer2D.glPushMatrix();
        
        Renderizador.drawer2D.glTranslatef(this.x, this.y, this.z);
        
        Renderizador.drawer2D.glRotatef(this.rotacao[1], 1, 0, 0);
        Renderizador.drawer2D.glRotatef(this.rotacao[0], 0, 1, 0);
        Renderizador.drawer2D.glRotatef(this.rotacao[2], 0, 0, 1);

        Renderizador.drawer2D.glScalef(this.largura, this.altura, this.profundidade);

        desenharComponente();

        Renderizador.drawer2D.glPopMatrix();

        Renderizador.drawer2D.glLineWidth(1);
    }

    public tipoComponente moverComponente(float novoX, float novoY, float novoZ)
    {
        this.x = novoX;
        this.y = novoY;
        this.z = novoZ;
        return (tipoComponente) this;
    }

    public tipoComponente rotacionarComponente(float anguloX, float anguloY, float anguloZ)
    {
        this.rotacao = new float[]{anguloX, anguloY, anguloZ};
        return (tipoComponente) this;
    }

    public tipoComponente centralizarComponente(boolean vertical, boolean horizontal, boolean eixoZ)
    {
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

    public tipoComponente trocarCor(float r, float g, float b, float a)
    {
        this.cor = new float[]{r, g, b, a};
        return (tipoComponente) this;
    }

    public tipoComponente preencherComponente(boolean preencher){
        this.preencher = preencher;
        return (tipoComponente) this;
    }
}
