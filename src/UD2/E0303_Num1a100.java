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

        int num;
        int res = -2;
        String mayor;
        final byte MAX_INT = 10;
        byte intentRest;

        Random rnd = new Random();
        num = rnd.nextInt(100) + 1;
        System.out.println(num);

        intentRest = MAX_INT;
        Scanner sc = new Scanner(System.in);
        /*No sale del bucle con -1*/
        while (intentRest > 0 && !(res == num && res != -1)) {
            System.out.print("Tienes " + intentRest + " intentos. ");
            System.out.print("Acierta un numero del 1 al 100: ");
            res = sc.nextInt();
            if (res != num) {
                System.out.println("Fallaste");
                mayor = res > num ? "menor" : "mayor";
                System.out.println("El número es " + mayor + " al número dado");
                --intentRest;
            }
        }
        sc.close();
        if (res != num) {
            System.out.println("Has perdido, el número era: " + num);
        } else {
            System.out.println("Has ganado con " + intentRest + " intentos restantes");
        }
    }
}
