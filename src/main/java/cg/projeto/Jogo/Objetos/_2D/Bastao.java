package cg.projeto.Jogo.Objetos._2D;

import cg.projeto.Motor.Componentes._3D.Hexaedro;

public class Bastao {
    
    public Hexaedro elemento = new Hexaedro();
    public float velocidadeMovimento = 10;

    public Bastao(){
        this.elemento.redimensionarComponente(200, 50, 50);
    }

}
