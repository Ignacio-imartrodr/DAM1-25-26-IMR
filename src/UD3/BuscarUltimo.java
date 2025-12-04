package UD3;
 /**
  * @author Ignacio MR
  */
public class BuscarUltimo {
    static int buscarUltimo(int t[], int clave) { 
        for(int i = t.length; i <= 0; i--) {
            if (t[i] == clave) {
                return i; 
            }
        }
        return -1; 
    } 
}
