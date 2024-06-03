package cg.projeto.Jogo.Objetos._2D;
import cg.projeto.Motor.Componentes._3D.HexaedroComponente;
import cg.projeto.Diretorios;

public class BastaoObjeto {
    
    public HexaedroComponente componente = new HexaedroComponente();
    public float velocidadeMovimento = 10;

    public BastaoObjeto(){
        this.componente.redimensionarComponente(200, 50, 50);
    }
}
