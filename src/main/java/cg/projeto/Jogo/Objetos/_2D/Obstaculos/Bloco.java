package cg.projeto.Jogo.Objetos._2D.Obstaculos;

import cg.projeto.Motor.Componentes._3D.Hexaedro;

public class Bloco {
    
    public Hexaedro componente = new Hexaedro();
    public static float alturaBloco = 25, larguraBloco = 75, comprimentoBloco = 10;

    public Bloco(){
        componente.redimensionarComponente(larguraBloco, alturaBloco, comprimentoBloco);
    }

}
