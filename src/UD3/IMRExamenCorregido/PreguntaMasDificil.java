package UD3.IMRExamenCorregido;
/**
 * @author Ignacio MR
 */
public class PreguntaMasDificil {
    public static void main(String[] args) {
        double[][] alumnoPregunta = new double[][]{
            {4,10,0,8},
            {8,2,9.5,3},
            {2,1,0,0},
            {9,6,0,0},
            {10,3,10,9.5}};
        System.out.println(mediaPreguntaMasDificil(alumnoPregunta));
    }
    static double mediaPreguntaMasDificil(double[][] notas){
        if (notas == null || notas.length == 0 || notas[0].length == 0){ 
            return -1;
        }
        double mediaMinima = 11;
        for (int i = 0; i < notas.length && i < notas[i].length; i++) {
            double mediaPregunta = 0;
            for (int j = 0; j < notas.length; j++) {
                mediaPregunta += notas[j][i];
            }
            mediaPregunta /= notas.length;
            if (mediaPregunta < mediaMinima){
                mediaMinima = mediaPregunta;
            }
        }
        return mediaMinima;
    }
}
