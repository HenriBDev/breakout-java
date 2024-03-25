package cg.projeto.UI.Componentes;

import cg.projeto.UI.Tela;

public abstract class ComponenteBase {
    
    public float x;
    public float y;
    public float[] cor;
    public float altura;
    public float largura;

    public abstract void desenharElemento();

    public void centralizarComponente(boolean incluirMargem, boolean vertical, boolean horizontal){
        if(horizontal) this.x = (Tela.xMax - (incluirMargem ? Tela.margem : 0)) / 2 - this.largura / 2;
        if(vertical) this.y = (Tela.yMax - (incluirMargem ? Tela.margem : 0)) / 2 - this.altura / 2;
    }

}
