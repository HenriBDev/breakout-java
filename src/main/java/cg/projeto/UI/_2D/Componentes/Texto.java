package cg.projeto.UI._2D.Componentes;

import cg.projeto.UI.Tela;

public class Texto extends ComponenteBase2D {
    
    public String conteudo;
    
    public Texto(String conteudo, float x, float y, float z, float[] cor, float[] rotacao){
        this.conteudo = conteudo;
        this.x = x;
        this.y = y;
        this.y = z;
        this.cor = cor;
        this.rotacao = rotacao;
        this.largura = calcularLarguraConteudo();
        this.altura = calcularAlturaConteudo();
    }
    
    public Texto(String conteudo, float[] cor, float[] rotacao){
        this.conteudo = conteudo;
        this.cor = cor;
        this.rotacao = rotacao;
        this.largura = calcularLarguraConteudo();
        this.altura = calcularAlturaConteudo();
    }

    public void desenharElemento(){

        Tela.drawer2D.glPushMatrix();

        Tela.drawer2D.glTranslatef(this.x, this.y, Tela.zMax * -1 + this.z);

        Tela.drawer2D.glRotatef(this.rotacao[1], 1, 0, 0);
        Tela.drawer2D.glRotatef(this.rotacao[0], 0, 1, 0);
        Tela.drawer2D.glRotatef(this.rotacao[2], 0, 0, 1);

        Tela.textRenderer.begin3DRendering();
        Tela.textRenderer.setColor(this.cor[0], this.cor[1], this.cor[2], 1);
        Tela.textRenderer.draw3D(this.conteudo, Tela.xMin - this.largura / 2, Tela.yMin - this.altura / 2, Tela.zMin, 1);
        Tela.textRenderer.end3DRendering();

        Tela.drawer2D.glPopMatrix();
    }

    private float calcularLarguraConteudo() { return (float) Tela.textRenderer.getBounds(this.conteudo).getMaxX() - (float) Tela.textRenderer.getBounds(this.conteudo).getX(); }
    private float calcularAlturaConteudo() { return (float) Tela.textRenderer.getBounds(this.conteudo).getMaxY() - (float) Tela.textRenderer.getBounds(this.conteudo).getY(); }

}