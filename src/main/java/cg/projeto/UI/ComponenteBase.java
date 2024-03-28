package cg.projeto.UI;

public abstract class ComponenteBase {
    
    public float x = 0;
    public float y = 0;
    public float[] cor = new float[]{1, 1, 1};
    public float altura = 1;
    public float largura = 1;

    public abstract void desenharElemento();

    public void centralizarComponente(boolean vertical, boolean horizontal){
        if(horizontal) this.x = Tela.xMax / 2;
        if(vertical) this.y = Tela.yMax / 2;
    }

}
