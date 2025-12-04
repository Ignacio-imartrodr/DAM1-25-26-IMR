package UD3;

public class FuncionBuscar {
    static int buscar(int t[], int clave) {
        for (int i = 0; i < t.length; i++) {
            if (t[i]==clave) {
                return i;
            }
        }
        return -1;
    }
}
