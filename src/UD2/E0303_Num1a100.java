package UD2;
import java.util.Random;
import java.util.Scanner;

/** 
* E0303. Codificar el juego “el número secreto”, que consiste en acertar un número entre 1 y 100 
(generado aleatoriamente). Para ello se introduce por teclado una serie de números, para los que 
se indica: “mayor” o “menor”, según sea mayor o menor con respecto al número secreto. El 
proceso termina cuando el usuario acierta o cuando se rinde (introduciendo un -1). 
*/

/**
 * @Author Ignacio MR
 */
public class E0303_Num1a100 {
    public static void main(String[] args) throws Exception {

        int numSecreto;
        int res = -2;
        String mayor;
        byte intent;
        boolean numeroCorrecto;

        Random rnd = new Random();
        numSecreto = rnd.nextInt(100) + 1;

        intent = 0;
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Escribe '-1' para rendirte");
        while (!(res == numSecreto || res == -1)) {
            numeroCorrecto = false;
            do {
                try {
                    System.out.print("Acierta un numero del 1 al 100: ");
                    res = sc.nextInt();
                    System.out.println();
                    numeroCorrecto = true;
                } catch (Exception e) {
                    String entradaErronea = sc.nextLine();
                    System.out.println("La entrada ("+ entradaErronea +") no es un número entero");
                }
            } while (!numeroCorrecto);
            
            if (res != numSecreto && res != -1) {
                mayor = res > numSecreto ? "menor" : "mayor";
                System.out.println("El número es " + mayor + " al número dado");
                ++intent;
            }
        }
        sc.close();
        System.out.println(res != numSecreto ? "Has perdido tras "+ intent +" intentos, el número era: " + numSecreto : "Has ganado tras " + intent + " intentos");
    }
}
