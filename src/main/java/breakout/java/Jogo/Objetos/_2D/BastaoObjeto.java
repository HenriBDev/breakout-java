package breakout.java.Jogo.Objetos._2D;

import breakout.java.Motor.Componentes._3D.HexaedroComponente;

public class BastaoObjeto {
    
    public HexaedroComponente componente = new HexaedroComponente();
    public float velocidadeMovimento = 10;

    public BastaoObjeto(){
        this.componente.redimensionarComponente(200, 50, 50);
    }

}
