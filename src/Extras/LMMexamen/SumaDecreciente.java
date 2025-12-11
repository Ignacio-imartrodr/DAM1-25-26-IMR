package Extras.LMMexamen;
import java.util.Scanner;
/**
 * @author Lunna Mendonça Miranda
 */
public class SumaDecreciente {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println(sumaDecreciente(4578));
        System.out.println(sumaDecreciente(5083));
        System.out.println(sumaDecreciente(999));
    }
    public static int sumaDecreciente(int numero) {
        int suma = 0;
        try {
            if (numero > 0) {
                while (numero > 0) {
                    numero =  numero % ((contarNumeros(numero) - 1) * 10);
                    suma += numero; 
                }
            } else {
                suma = -1;
            }
        } catch (Exception e) {
            suma = -1;
        }
        return suma;
    }
    public static int contarNumeros(int numero) {
        int cantidad = 0;
        while (numero > 0) {
            numero = numero / 10;
            cantidad++;
        }
        return cantidad;
    }
}
