package cg.projeto.Jogo.Objetos._2D.Obstaculos;

import cg.projeto.Motor.Componentes._3D.Hexaedro;

public class Bloco {
    
    public Hexaedro elemento = new Hexaedro();
    public static float alturaBloco = 25, larguraBloco = 75, comprimentoBloco = 10;

    public Bloco(){
        elemento.redimensionarComponente(larguraBloco, alturaBloco, comprimentoBloco);
    }

}
