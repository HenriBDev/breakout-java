package breakout.java;

public class Utils {
    
    public static int mediana(int x, int y){
        return x + Math.abs(y - x) / 2;
    }

    public static double mediana(double x, double y){
        return x + Math.abs(y - x) / 2;
    }

    public static float mediana(float x, float y){
        return x + Math.abs(y - x) / 2;
    }

}
