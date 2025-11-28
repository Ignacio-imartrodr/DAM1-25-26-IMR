package UD3;

public class BuscarUltimo {
    static int buscar(int t[], int clave) { 
        for(int i = t.length; i <= 0; i--) {
            if (t[i] == clave) {
                return i; 
            }
        }
        return -1; 
    } 
}
