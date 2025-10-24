package UD2;

/**
 *  E0407.Escribir una función a la que se le pase un número entero y devuelva el número de 
 * divisores primos que tiene.
 * 
 * @author Ignacio MR
 */
public class E0407_CantidadPrimos {
    public static boolean esPrimo(int n) {
        boolean primo = true;
        if (n <= 1) 
            return false;

        for (int i = 2; i <= Math.pow(n, 1 / 2.0); ++i) {
            if (n % i == 0) {
                primo = false;
            }
        }
        
        return primo;
    }
    public static int cantPrimo(int n) {
        int cant = 0;
        for (int i = 2; i <= Math.abs(n); ++i) {
            if (n % i == 0 && esPrimo(i)) {
                ++cant;
            }
        }
        return cant;
    }
    public static void main(String[] args) {
        for (int i=0; i<=100; ++i){
            System.out.println(i +" tiene "+ cantPrimo(i) + " divisores primos");
        }
    }
}
