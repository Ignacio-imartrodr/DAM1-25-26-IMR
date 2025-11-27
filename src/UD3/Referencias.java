package UD3;
/**
 * @author Ignacio MR
*/

public class Referencias {
    public static void main(String[] args) {
        int[] tablaEnteros = new int[10];
        int[] ej1=tablaEnteros;
        int[] ej2=tablaEnteros;
        int[] ej3=tablaEnteros;
        int[] ej4=tablaEnteros;
        int[] ej5=tablaEnteros;
        System.out.println(tablaEnteros);
        System.out.println(ej1);
        System.out.println(ej2);
        System.out.println(ej3);
        System.out.println(ej4);
        System.out.println(ej5);
    }
}
