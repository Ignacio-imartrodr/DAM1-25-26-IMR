package Extras.SamuExamen;

/**
 * @author Samuel
 */
public class PreguntaMasDificilINC {
    static double mediaPreguntaMasDificil(double[][] notas) {
        if (notas == null) {
            return -1;
        }
        double media = 0;
        double[] medias = new double[notas.length];
        double masPequeño = 10;// nota máxima para tomar la primera media baja
        for (int i = 0; i < notas.length; i++) {
            media = 0;
            for (int j = 0; j < notas[i].length; j++) {
                media = media + notas[i][j];
            }
            medias[i] = media;
        }

        for (double d : medias) {
            if (masPequeño > d) {
                masPequeño = d;
            }
        }

        return masPequeño;
    }

    public static void main(String[] args) {
        double[][] notas = {
                { 4, 10, 0, 8 },
                { 8, 2, 9.5, 3 },
                { 2, 1, 0, 0 },
                { 9, 6, 0, 0 },
                { 10, 3, 10, 9.5 }
        };
        System.out.println(mediaPreguntaMasDificil(notas));
    }
}
