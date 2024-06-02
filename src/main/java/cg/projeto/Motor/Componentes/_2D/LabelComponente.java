package cg.projeto.Motor.Componentes._2D;

import cg.projeto.Motor.Componentes.BaseComponente;

public class LabelComponente extends BaseComponente<LabelComponente> {
    
    public float[] corTexto = new float[]{1, 1, 1, 1};
    public float[] corBorda = new float[]{1, 1, 1, 1};
    public float[] corFundo = new float[]{0, 0, 0, 1};

    public float espessuraBorda = 1, espessuraPreenchimento = 5;

    public TextoComponente textoComponente;
    public QuadrilateroComponente
        fundo = new QuadrilateroComponente()
            .trocarCor(corFundo[0], corFundo[1], corFundo[2], corFundo[3]),
        bordaCima = new QuadrilateroComponente()
            .redimensionarComponente(fundo.largura + espessuraBorda*2, espessuraBorda),
        bordaDireita = new QuadrilateroComponente()
            .redimensionarComponente(espessuraBorda, fundo.altura + espessuraBorda*2),
        bordaBaixo = new QuadrilateroComponente()
            .redimensionarComponente(fundo.largura + espessuraBorda*2, espessuraBorda),
        bordaEsquerda = new QuadrilateroComponente()
            .redimensionarComponente(espessuraBorda, fundo.altura + espessuraBorda*2);

    public boolean bordaHabilitada = false;

    public LabelComponente(String textoConteudo)
    {
        textoComponente = new TextoComponente(textoConteudo)
            .trocarCor(corTexto[0], corTexto[1], corTexto[2], corTexto[3]);

        alterarEspessuraPreenchimento(espessuraPreenchimento);
    }

    public void desenharComponente()
    {
        textoComponente.desenharComponente();
        fundo.desenharComponente();

        if(bordaHabilitada)
        {
            bordaCima.desenharComponente();
            bordaDireita.desenharComponente();
            bordaBaixo.desenharComponente();
            bordaEsquerda.desenharComponente();
        }
    }

    @Override
    public LabelComponente moverComponente(float novoX, float novoY, float novoZ)
    {
        this.x = novoX;
        this.y = novoY;
        this.z = novoZ;

        textoComponente.moverComponente(novoX, novoY, novoZ + 1);
        fundo.moverComponente(novoX, novoY, novoZ);

        ajustarPosicaoBorda();

        return this;
    }

    @Override
    public LabelComponente rotacionarComponente(float anguloX, float anguloY, float anguloZ)
    {
        this.rotacao = new float[]{anguloX, anguloY, anguloZ};

        textoComponente.rotacionarComponente(anguloX, anguloY, anguloZ);
        fundo.rotacionarComponente(anguloX, anguloY, anguloZ);
        bordaCima.rotacionarComponente(anguloX, anguloY, anguloZ);
        bordaBaixo.rotacionarComponente(anguloX, anguloY, anguloZ);
        bordaDireita.rotacionarComponente(anguloX, anguloY, anguloZ);
        bordaEsquerda.rotacionarComponente(anguloX, anguloY, anguloZ);

        return this;
    }

    public LabelComponente alterarEspessuraPreenchimento(float novoPreenchimento)
    {
        espessuraPreenchimento = novoPreenchimento;

        fundo.redimensionarComponente(textoComponente.largura + espessuraPreenchimento * 2, textoComponente.altura + espessuraPreenchimento * 2);

        ajustarDimensoesBorda();
        ajustarPosicaoBorda();
        calcularNovasDimensoes();

        return this;
    }

    public LabelComponente alterarEspessuraBorda(float novaEspessura)
    {
        espessuraBorda = novaEspessura;

        ajustarDimensoesBorda();
        ajustarPosicaoBorda();
        calcularNovasDimensoes();

        return this;
    }

    private void ajustarPosicaoBorda()
    {
        bordaCima.moverComponente(x, y - fundo.altura/2 - espessuraBorda/2, z);
        bordaBaixo.moverComponente(x, y + fundo.altura/2 + espessuraBorda/2, z);
        bordaDireita.moverComponente(x - fundo.largura/2 - espessuraBorda/2, y, z);
        bordaEsquerda.moverComponente(x + fundo.largura/2 + espessuraBorda/2, y, z);
    }
    
    private void ajustarDimensoesBorda()
    {
        bordaCima.redimensionarComponente(fundo.largura + espessuraBorda*2, espessuraBorda);
        bordaDireita.redimensionarComponente(espessuraBorda, fundo.altura + espessuraBorda*2);
        bordaBaixo.redimensionarComponente(fundo.largura + espessuraBorda*2, espessuraBorda);
        bordaEsquerda.redimensionarComponente(espessuraBorda, fundo.altura + espessuraBorda*2);
    }

    private void calcularNovasDimensoes()
    {
        largura = textoComponente.largura + espessuraPreenchimento + (bordaHabilitada ? espessuraBorda : 0);
        altura = textoComponente.altura + espessuraPreenchimento + (bordaHabilitada ? espessuraBorda : 0);
    }

    public LabelComponente setTextoConteudo(String novoConteudo)
    {
        textoComponente.setConteudo(novoConteudo);
        calcularNovasDimensoes();
        return this;
    }

}
