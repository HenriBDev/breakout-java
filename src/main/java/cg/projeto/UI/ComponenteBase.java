package cg.projeto.UI;

public abstract class ComponenteBase<tipoComponente> {
    
    public float x = 0;
    public float y = 0;
    public float z = 0;
    public float[][] vertices;
    public float[] cor = new float[]{1, 1, 1, 1};
    public float[] rotacao = new float[]{0, 0, 0};

    public abstract void desenharComponente();

    // protected abstract tipoComponente atualizarVertices();

    public tipoComponente moverComponente(float novoX, float novoY, float novoZ){
        this.x = novoX;
        this.y = novoY;
        this.z = novoZ;
        // this.atualizarVertices();
        return (tipoComponente) this;
    }

    public tipoComponente rotacionarComponente(float anguloX, float anguloY, float anguloZ){
        this.rotacao = new float[]{anguloX, anguloY, anguloZ};
        // this.atualizarVertices();
        return (tipoComponente) this;
    }

    public tipoComponente trocarCor(float r, float g, float b, float a){
        this.cor = new float[]{r, g, b, a};
        return (tipoComponente) this;
    }

    public tipoComponente centralizarComponente(boolean vertical, boolean horizontal, boolean eixoZ){
        return this.moverComponente(
            horizontal ? Tela.xPontoCentral : this.x, 
            vertical ? Tela.yPontoCentral : this.y,
            eixoZ ? Tela.zPontoCentral : this.z
        );
    }

    public boolean colisaoComponente(componenteBase componenteAColidir)
    {
        float posicaoComponenteAtualX = this.elementoX;
        float posicaoComponenteAtualY = this.elementoY;

        float pontaDireitaComponenteAtual = posicaoComponenteAtualX + this.largura/2;
        float pontaEsquerdaComponenteAtual = posicaoComponenteAtualX + this.largura/2;
        float pontaCimaComponenteAtual = posicaoComponenteAtualY + this.largura/2;
        float pontaBaixoComponenteAtual = posicaoComponenteAtualY - this.largura/2;

        float posicaoComponenteAColidirX = componenteAColidir.elementoX;
        float inicioPosicaoX = componenteAColidir.largura/2 - posicaoComponenteAColidirX;
        float fimPosicaoX = componenteAColidir.largura/2 + posicaoComponenteAColidirX;

        float posicaoComponenteAColidirY = componenteAColidir.elementoY;
        float topoPosicaoComponenteAColidir = componenteAColidir.altura/2 + posicaoComponenteAColidirY;
        float inicioPosicaoY = componenteAColidir.largura/2 + posicaoComponenteAColidirY;
        float fimPosicaoY = componenteAColidir.largura/2 - posicaoComponenteAColidirY;

        if(((posicaoComponenteAtualX >= inicioPosicaoX || posicaoComponenteAtualX <= fimPosicaoX) && // Colisão em cima e em baixo
        posicaoComponenteAtualY == topoPosicaoComponenteAColidir) ||
        (pontaDireitaComponenteAtual == inicioPosicaoX || pontaDireitaComponenteAtual == fimPosicaoX) && // Colisão nas laterais
        (posicaoComponenteAtualY >= inicioPosicaoY || posicaoComponenteAtualY <= fimPosicaoY))
        {
            return true;
        }

        return false;
    }
}
