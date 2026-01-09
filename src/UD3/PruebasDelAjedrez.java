package UD3;

public class PruebasDelAjedrez {
    static String stringMatriz(int[][] t) {
        String Str = "";
        if (t.length == 0) {
            Str = "[]";
            return Str;
        }
        for (int i = 0; i < t.length; i++) {
            if (t[i] == null || t[i].length == 0) {
                if (i == 0) {
                    Str += "[]";
                } else {
                    Str += String.format("%n%s", "[]");
                }

            } else {
                Str += String.format("%n%s", "[ ");
                for (int j = 0; j < t[i].length; j++) {
                    if (j != t[i].length - 1) {
                        Str += t[i][j] + " | ";
                    } else {
                        Str += t[i][j] + " ]";
                    }
                }
            }
        }
        return Str;
    }
    public static void main(String[] args) {
        int num = 0;
        int[][] prueba = new int[3][4];
        for (int i = 0; i < prueba.length; i++) {
            for (int j = 0; j < prueba[i].length; j++) {
                prueba[i][j]=num;
                num++;
            }
        }
        System.out.println(stringMatriz(prueba));
        System.out.println(prueba[2][3]);
        boolean Case = Character.isLowerCase('-');
        System.out.println(Case?"Mayusculas":"Minuscula");
    }
    
}
