package cg.projeto.Jogo.Telas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cg.projeto.Motor.Resolucao;
import cg.projeto.Motor.Componentes._2D.CirculoComponente;
import cg.projeto.Motor.Componentes._2D.QuadrilateroComponente;
import cg.projeto.Motor.Componentes._2D.TextoComponente;

public class InicialTela extends BaseTela {

    public float margemTela = 25;

    public static final List<String> textoInicial = Arrays.asList(
        "Bem-vindo ao jogo de pong!",
        "A D ou <- -> ou MOUSE (Movimentação).",
        "P (Pause)", 
        "R (Recomeçar jogo)",
        "Seu objetivo é acumular 500 pontos para avançar",
        "para a próxima fase. Rebata a esfera e ganhe pontos.",
        "Mas cuidado com a pegadinha do malandro 👀... (A bolinha fica mais rápida)",
        "",
        "Pressione enter para começar."
    );

    public static List<TextoComponente> componentesTextoInicial = new ArrayList<TextoComponente>();

    QuadrilateroComponente campo = new QuadrilateroComponente()
        .redimensionarComponente(200, 200)
        .centralizarComponente(false, false, false)
        .preencherComponente(false);

    QuadrilateroComponente bastao = new QuadrilateroComponente()    
        .redimensionarComponente(112.5f, 37.5f)
        .centralizarComponente(false, true, false);

    CirculoComponente bolinha = new CirculoComponente().redimensionarComponente(20);
    
    public InicialTela()
    {
        // Desenha instruções na tela
        float posYAtual = Resolucao.SRUyMax - margemTela;
        for(String linhaTexto : textoInicial)
        {
            TextoComponente linhaTela = new TextoComponente(linhaTexto);
            posYAtual -= linhaTela.altura + margemTela;
            linhaTela.centralizarComponente(false, true, false)
                .moverComponente(linhaTela.x, posYAtual, linhaTela.z);
            componentesTextoInicial.add(linhaTela);
        }
        componentes.addAll(componentesTextoInicial);

        posYAtual -= margemTela * 2;

        campo.moverComponente(campo.x, posYAtual - campo.altura / 2, campo.z);
        componentes.add(campo);

        bastao.moverComponente(bastao.x, posYAtual - campo.altura + 11 + bastao.altura / 2, bastao.z);
        componentes.add(bastao);

        bolinha.moverComponente(campo.x + campo.largura / 3, campo.y , bolinha.z);
        componentes.add(bolinha);
    }

}
