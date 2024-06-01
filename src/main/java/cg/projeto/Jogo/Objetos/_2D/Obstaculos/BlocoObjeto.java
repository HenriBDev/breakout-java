package cg.projeto.Jogo.Objetos._2D.Obstaculos;

import cg.projeto.Motor.Componentes._3D.HexaedroComponente;

public class BlocoObjeto {
    
    public HexaedroComponente componente = new HexaedroComponente();
    public static float alturaBloco = 25, larguraBloco = 75, comprimentoBloco = 10;

    public BlocoObjeto(){
        componente.redimensionarComponente(larguraBloco, alturaBloco, comprimentoBloco);
    }

}
