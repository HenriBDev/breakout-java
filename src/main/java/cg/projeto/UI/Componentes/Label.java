package cg.projeto.UI.Componentes;

import cg.projeto.UI.Tela;

public class Label extends ComponenteBase {
    
    public String conteudo;
    
    public Label(float x, float y, float[] cor, String conteudo){
        this.x = x;
        this.y = y;
        this.cor = cor;
        this.conteudo = conteudo;
        this.largura = calcularLarguraConteudo();
        this.altura = calcularAlturaConteudo();
    }
    
    public Label(float[] cor, String conteudo){
        this.x = 0;
        this.y = 0;
        this.cor = cor;
        this.conteudo = conteudo;
        this.largura = calcularLarguraConteudo();
        this.altura = calcularAlturaConteudo();
    }

    public void desenharElemento(){
        Tela.textRenderer.beginRendering((int)Tela.xMax, (int)Tela.yMax);
        Tela.textRenderer.setColor(this.cor[0], this.cor[1], this.cor[2], 1);
        Tela.textRenderer.draw(this.conteudo, (int)this.x, (int)this.y);
        Tela.textRenderer.endRendering();
    }

    private float calcularLarguraConteudo() { return (float) Tela.textRenderer.getBounds(this.conteudo).getMaxX() - (float) Tela.textRenderer.getBounds(this.conteudo).getX(); }
    private float calcularAlturaConteudo() { return (float) Tela.textRenderer.getBounds(this.conteudo).getMaxY() - (float) Tela.textRenderer.getBounds(this.conteudo).getY(); }

}
