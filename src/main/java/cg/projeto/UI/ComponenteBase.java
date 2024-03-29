package cg.projeto.UI;

public abstract class ComponenteBase {
    
    public float x = 0;
    public float y = 0;
    public float z = 0;
    public float[] cor = new float[]{1, 1, 1};
    public float[] rotacao = new float[]{0, 0, 0};

    public abstract void desenharElemento();

    public void centralizarComponente(boolean vertical, boolean horizontal, boolean eixoZ){
        if(horizontal) this.x = Tela.xMax / 2;
        if(vertical) this.y = Tela.yMax / 2;
        if(eixoZ) this.z = Tela.zMax / 2;
    }

}
