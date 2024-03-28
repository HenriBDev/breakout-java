package cg.projeto.UI._3D.Componentes;

import cg.projeto.UI.ComponenteBase;
import cg.projeto.UI.Tela;

public abstract class ComponenteBase3D extends ComponenteBase {
    
    public float x = 0;
    public float y = 0;
    public float z = 0;
    public float[] cor = new float[]{1, 1, 1};
    public float altura = 1;
    public float largura = 1;

    public abstract void desenharElemento();

    public void centralizarComponente(boolean incluirMargem, boolean vertical, boolean horizontal){
        if(horizontal) this.x = (Tela.xMax - (incluirMargem ? Tela.margem : 0)) / 2 - this.largura / 2;
        if(vertical) this.y = (Tela.yMax - (incluirMargem ? Tela.margem : 0)) / 2 - this.altura / 2;
    }

}
