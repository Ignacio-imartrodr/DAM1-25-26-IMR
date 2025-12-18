package Extras;

import java.util.Arrays;
/**
 * @author Ignacio MR
 */
public class Moda {
    static int contador(int t[], int clave){
        int cant = 0;
        for (int i = 0; i < t.length; i++) {
            if (t[i]==clave) cant++;
        }
        return cant;
    }
    static int buscar(int t[], int clave) {
        for (int i = 0; i < t.length; i++) {
            if (t[i]==clave) {
                return i;
            }
        }
        return -1;
    }
    static int[] tNumsDif(int t[]){
        int[] tNumDif = t.clone();
        int posRep;
        for (int i = 0; i < tNumDif.length; i++) {
            posRep = buscar(tNumDif, tNumDif[i]);
            while (posRep !=i && posRep != -1 && tNumDif[i]==tNumDif[posRep]) {
                tNumDif[i] = tNumDif[tNumDif.length-1];
                tNumDif = Arrays.copyOfRange(tNumDif,0, tNumDif.length-1);
                i--;
            }
        }
        return tNumDif;
    }
    static int PosNumMayor(int[] t){
        int posMayor = 0;
        for (int i = 0; i < t.length-1; i++) {
            if(t[i+1] > t[i] && t[i+1] > t[0]) posMayor=i+1;
        }
        boolean SinRepetidos = t.length != tNumsDif(t).length ? false : true; 
        return SinRepetidos ? posMayor : -1;
    }
    static int moda(int[] t){
        int[] tNumsDif = tNumsDif(t);
        int[] modas=new int[tNumsDif.length];
        for (int i = 0; i < modas.length; i++) {
            modas[i]=contador(t, tNumsDif[i]);
        }
        return PosNumMayor(modas) == -1? -1 : tNumsDif[PosNumMayor(modas)];
    }
    public static void main(String[] args) {
        //int t[]={1, 2, 3, 2, 3, 1};//-1
        //int t[]={1, 2, 1, 2, 3, 1};//1
        int t[]={1, 2, 1, 2, 2, 3};//2
        //int t[]={1, 2, 3, 2, 3, 3};//3
        System.out.println(moda(t)!=-1 ? "El numero moda es: " + moda(t) : "No hay una moda concreta");
    }
}
