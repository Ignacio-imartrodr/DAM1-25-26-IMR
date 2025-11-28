package UD3;

public class BuscarDesde {
     static int buscar(int t[], int clave, int posicion) {
        if (posicion > 0 && posicion < t.length) {
            for(int i = posicion; i < t.length; i++) {
                if (t[i] == clave) {
                    return i; 
                }
            }
        }
        return -1; 
    } 
}
