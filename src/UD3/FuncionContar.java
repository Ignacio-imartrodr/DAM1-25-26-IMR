package UD3;

/**
 * @author Ignacio MR
 */

public class FuncionContar {
    static int contar(int t[], int clave){
        int cant = 0;
        for (int i = 0; i < t.length; i++) {
            if (t[i]==clave) {
                cant++;
            }
        }
        return cant;
    }
}
