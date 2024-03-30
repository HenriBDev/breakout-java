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
            horizontal ? Tela.xMin + Math.abs(Tela.xMax - Tela.xMin) / 2 : this.x, 
            vertical ? Tela.yMin + Math.abs(Tela.yMax - Tela.yMin) / 2 : this.y,
            eixoZ ? Tela.zMin + Math.abs(Tela.zMax - Tela.zMin) / 2 : this.z
        );
    }

}
