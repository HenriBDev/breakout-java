package cg.projeto.Jogo.Objetos._2D;

import cg.projeto.Motor.Componentes._3D.Hexaedro;

public class Bastao {
    
    public Hexaedro componente = new Hexaedro();
    public float velocidadeMovimento = 10;

    public Bastao(){
        this.componente.redimensionarComponente(200, 50, 50);
    }

}
