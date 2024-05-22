package cg.projeto.Motor.Componentes._2D;

import cg.projeto.Motor.Tela;
import cg.projeto.Motor.Componentes.ComponenteBase;

public class Texto extends ComponenteBase<Texto> {
    
    public String conteudo = "";

    public Texto(String conteudo){ 
        this.conteudo = conteudo;
        this.largura = calcularLarguraConteudo();
        this.altura = calcularAlturaConteudo();
    }

    public void desenharComponente(){

        Tela.drawer2D.glPushMatrix();

        Tela.drawer2D.glTranslatef(this.x, this.y, this.z);

        Tela.drawer2D.glRotatef(this.rotacao[1], 1, 0, 0);
        Tela.drawer2D.glRotatef(this.rotacao[0], 0, 1, 0);
        Tela.drawer2D.glRotatef(this.rotacao[2], 0, 0, 1);

        Tela.textRenderer.begin3DRendering();
        Tela.textRenderer.setColor(this.cor[0], this.cor[1], this.cor[2], this.cor[3]);
        Tela.textRenderer.draw3D(this.conteudo, 0 - this.largura / 2, 0 - this.altura / 2, 0, 1);
        Tela.textRenderer.end3DRendering();

        Tela.drawer2D.glPopMatrix();
    }

    private float calcularLarguraConteudo() { return (float) Tela.textRenderer.getBounds(this.conteudo).getMaxX() - (float) Tela.textRenderer.getBounds(this.conteudo).getX(); }
    private float calcularAlturaConteudo() { return (float) Tela.textRenderer.getBounds(this.conteudo).getMaxY() - (float) Tela.textRenderer.getBounds(this.conteudo).getY(); }

}
