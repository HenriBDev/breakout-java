package cg.projeto.Motor.Componentes._2D;

import cg.projeto.Motor.Renderizador;
import cg.projeto.Motor.Componentes.BaseComponente;

public class TextoComponente extends BaseComponente<TextoComponente> {
    
    public String conteudo = "";

    public TextoComponente(String conteudo){ setConteudo(conteudo); }

    public void desenharComponente(){

        Renderizador.drawer2D.glPushMatrix();

        Renderizador.drawer2D.glTranslatef(this.x, this.y, this.z);

        Renderizador.drawer2D.glRotatef(this.rotacao[1], 1, 0, 0);
        Renderizador.drawer2D.glRotatef(this.rotacao[0], 0, 1, 0);
        Renderizador.drawer2D.glRotatef(this.rotacao[2], 0, 0, 1);

        Renderizador.textRenderer.begin3DRendering();
        Renderizador.textRenderer.setColor(this.cor[0], this.cor[1], this.cor[2], this.cor[3]);
        Renderizador.textRenderer.draw3D(this.conteudo, 0 - this.largura / 2, 0 - this.altura / 2, 0, 1);
        Renderizador.textRenderer.end3DRendering();

        Renderizador.drawer2D.glPopMatrix();
    }

    private void calcularNovasDimensoes() 
    { 
        largura = (float) Renderizador.textRenderer.getBounds(this.conteudo).getMaxX() - (float) Renderizador.textRenderer.getBounds(this.conteudo).getX(); 
        altura = (float) Renderizador.textRenderer.getBounds(this.conteudo).getMaxY() - (float) Renderizador.textRenderer.getBounds(this.conteudo).getY(); 
    }

    public TextoComponente trocarCor(float r, float g, float b, float a){
        this.cor = new float[]{r, g, b, a};
        return this;
    }

    public TextoComponente setConteudo(String novoConteudo)
    {
        this.conteudo = novoConteudo;
        calcularNovasDimensoes();
        return this;
    }

}
