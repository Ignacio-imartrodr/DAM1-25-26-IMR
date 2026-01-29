package UD3;

public class MatrizMagica {
    public static void main(String[] args) {
        int[][] matriz = new int[][] {{1,1,1,1}, {1,1,1,1}, {1,1,1,1}, {1,1,1,1}};
        System.out.println(esMatrizMagica(matriz) ? "Sí" : "No");
    }
    static boolean esMatrizMagica(int[][] matriz){
        boolean esMagica = true;
        double sumCol = 0;
        double sumTot = 0;
        double sumFil = 0;
        double sumDiagonal = 0;
        for (int i = 0; i < matriz.length; i++) {
            
        }
        for (int[] is : matriz) {
            for (int is2 : is) {
                sumTot += is2;
            }
        }
        for (int i = 0; i < 4; i++) {
            sumCol += matriz[i][0];
        }
        sumTot /= (matriz.length * matriz[0].length);
        sumCol /= matriz.length;
        if (sumCol != sumTot){
            esMagica = false;
        } else {
            for (int i = 0; i < matriz.length && esMagica; i++) {
                for (int j = 0; j < matriz[i].length; j++) {
                    sumFil += matriz[i][j];
                }
                sumFil /= matriz[i].length;
                if (sumFil != sumCol) {
                    esMagica = false;
                }
                sumFil = 0;
            }
            for (int i = 0; i < matriz.length && esMagica; i++) {
                for (int j = 0; j < matriz[i].length; j++) {
                    if (i == j){
                        sumDiagonal += matriz[i][j];
                    }
                }
                sumDiagonal /= matriz[i].length;
                if (sumDiagonal != sumCol) {
                    esMagica = false;
                }
                sumDiagonal = 0;
            }
            for (int i = matriz.length - 1; i >= 0 && esMagica; i++) {
                for (int j = matriz[i].length - 1; j >= 0; j++) {
                    if (i == j){
                        sumDiagonal += matriz[i][j];
                    }
                }
                sumDiagonal /= matriz[i].length;
                if (sumDiagonal != sumCol) {
                    esMagica = false;
                }
                sumDiagonal = 0;
            }
        }
        
        return esMagica;
    }
}
