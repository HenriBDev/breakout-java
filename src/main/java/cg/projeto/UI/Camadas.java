package cg.projeto.UI;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cg.projeto.UI.Componentes.ComponenteBase;
import cg.projeto.UI.Componentes.Label;
import cg.projeto.UI.Componentes.Quadrilatero;

public class Camadas {

    public static List<Label> TEXTO = new ArrayList<Label>();
    public static List<Quadrilatero> MAIN = new ArrayList<Quadrilatero>();

    public void desenharCamadas(){
        
        for (Field camada : Camadas.class.getDeclaredFields()){

            ArrayList<ComponenteBase> objElementosCamada = null;

            try{ objElementosCamada = (ArrayList<ComponenteBase>) camada.get(this);}
            catch (IllegalAccessException e) {}

            Iterator<ComponenteBase> iterador = objElementosCamada.iterator();

            while(iterador.hasNext()){
                iterador.next().desenharElemento();
            }

        }

    }

    public void limparCamadas(){

        for (Field camada : Camadas.class.getDeclaredFields()){

            ArrayList<ComponenteBase> objElementosCamada = null;

            try{ objElementosCamada = (ArrayList<ComponenteBase>) camada.get(this);}
            catch (IllegalAccessException e) {}

            if (objElementosCamada.size() > 0) objElementosCamada.clear();
        }

    }

}
