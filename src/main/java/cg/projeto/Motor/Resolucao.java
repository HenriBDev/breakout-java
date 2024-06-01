package cg.projeto.Motor;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import cg.projeto.Utils;

public class Resolucao {

    public static float 
        larguraTela, alturaTela, 
        SRUxMin, SRUxMax, SRUxCentral,
        SRUyMin, SRUyMax, SRUyCentral,
        SRUzMin, SRUzMax, SRUzCentral;

    public static void calcularResolucaoTela(){
        DisplayMode displayMode;
        Rectangle bounds;
        Rectangle scaledBounds;
        for (GraphicsDevice device : GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()) {
            displayMode = device.getDefaultConfiguration().getDevice().getDisplayMode();
            scaledBounds = device.getDefaultConfiguration().getBounds();
            bounds = new Rectangle(scaledBounds.x, scaledBounds.y, displayMode.getWidth(), displayMode.getHeight());
            larguraTela = Math.max(Integer.MIN_VALUE, bounds.x + bounds.width) - Math.min(Integer.MAX_VALUE, bounds.x);
            alturaTela = Math.max(Integer.MIN_VALUE, bounds.y + bounds.height) - Math.min(Integer.MAX_VALUE, bounds.y);
        }
        SRUxMin = Resolucao.larguraTela/2 * -1;
        SRUxMax = Resolucao.larguraTela/2;
        SRUyMin = Resolucao.alturaTela/2 * -1;
        SRUyMax = Resolucao.alturaTela/2;
        SRUzMin = 1000 * -1;
        SRUzMax = 1000;
        SRUxCentral = Utils.mediana(SRUxMin, SRUxMax);
        SRUyCentral = Utils.mediana(SRUyMin, SRUyMax);
        SRUzCentral = Utils.mediana(SRUzMin, SRUzMax);
    }
    
}
