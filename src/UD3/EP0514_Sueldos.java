package UD3;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * EP0514_Sueldos. El ayuntamiento de tu localidad te ha encargado una aplicación que ayude a realizar encuestas 
 * estadísticas para conocer el nivel adquisitivo de los habitantes del municipio. Para ello, tendrás que preguntar 
 * el sueldo a cada persona encuestada. A priori, no conoces el número de encuestados. Para finalizar la entrada de 
 * datos, introduce un sueldo con valor -1. 
 * 
 * Una vez terminada la entrada de datos, muestra la siguiente información: 
 * ●  Todos los sueldos introducidos ordenados de forma decreciente. 
 * ●  El sueldo máximo y mínimo. 
 * ●  La media de los sueldos. 
 * ●  Cuántos sueldos hay por encima de la media. 
 * ●  Cuántos sueldos hay por debajo de la media. 
 * 
 * @author Ignacio MR
 */
public class EP0514_Sueldos {
    static String stringArray(int[] array) {
        String arrayStr = "";
        for (int i = 0; i < array.length; i++) {
            if (i > 0 && i < array.length) {
                arrayStr += ", ";
            }
            arrayStr += array[i];
        }
        return arrayStr;
    }
    static Scanner sc = new Scanner(System.in);
    static int pedirNum() {
        int n = 0;
        try {
            n = sc.nextInt();
        } catch (InputMismatchException e) {
            sc.nextLine();
            System.out.println("Introduce unicamente valores numericos.");
        }
        return n;
    }
    public static void main(String[] args) {
        int cantSueldosMayorMed = 0;
        int cantSueldosMenorMed = 0;
        int sueldoIntrod;
        int sueldoMax = 0;
        int sueldoMin = Integer.MAX_VALUE;
        int media = 0;
        int[] sueldos = new int[0];
        do {
            System.out.print("Cual es tu sueldo actual? ('-1' para finalizar): ");
            sueldoIntrod = pedirNum(); 
            if (sueldoIntrod >= 0){
                sueldos = Arrays.copyOf(sueldos, sueldos.length + 1);
                sueldos[sueldos.length-1] = sueldoIntrod;
                media += sueldoIntrod;
                if (sueldoIntrod < sueldoMin) sueldoMin = sueldoIntrod;
                if (sueldoIntrod > sueldoMax) sueldoMax = sueldoIntrod;
            }
        } while (sueldoIntrod > 0);
        media /= sueldos.length;
        for (int i = 0; i < sueldos.length; i++) {
            if (sueldos[i]>media) cantSueldosMayorMed++;
            if (sueldos[i]<media) cantSueldosMenorMed++;
        }
        Arrays.sort(sueldos);
        
        //devuelvo sueldos decreciente
        int mitadT=sueldos.length/2;
        int aux;
        int j = sueldos.length;
        for (int i = 0; i < sueldos.length-mitadT; i++) {
            j--;
            aux = sueldos[i];
            sueldos[i] = sueldos[j];
            sueldos[j] = aux;
        }
        System.out.println("Se introdujeron los siguentes sueldos: " + stringArray(sueldos));
        System.out.println("Sueldo Maximo: " + sueldoMax + " Sueldo Minimo: " + sueldoMin);
        System.out.println("El sueldo medio es de: " + media);
        System.out.println("Cantidad de sueldos por encima de la media: " + cantSueldosMayorMed);
        System.out.println("Cantidad de sueldos por debajo de la media: " + cantSueldosMenorMed);
    }
}
