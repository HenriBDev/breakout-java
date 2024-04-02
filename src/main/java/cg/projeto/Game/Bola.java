package cg.projeto.Game;

import cg.projeto.Game.Estados.EstadosBola;
import cg.projeto.UI._3D.Componentes.Esfera;

public class Bola {
    
    public EstadosBola estado = EstadosBola.PARADA;
    public Esfera elemento = new Esfera();
    public float velocidadeMovimento = 10;

}
