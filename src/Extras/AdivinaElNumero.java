package Extras;

import java.util.InputMismatchException;
import java.util.Scanner;
/** 
* Acierta un numero con 10 intentos y pistas de si es mayor o menor
*
* 
* @Author Ignacio MR
*/
public class AdivinaElNumero {
      static Scanner sc = new Scanner(System.in);
      static int pedirNumInt(){
            try {
                  System.out.print("Acierta un numero del 0 al 100: ");
                  return sc.nextInt();
            } catch (InputMismatchException e) {
                  System.out.println("Introduce solo números enteros");
                  sc.nextLine();
                  return pedirNumInt();
            }
      }
      public static void main(String[] args){

            long num;
            int res = -1;
            String mayor;
            final byte MAX_INT = 10;
            byte intentRest;

            num = Math.round(Math.random()*100);
            intentRest = MAX_INT;
            

            while (intentRest > 0) {
                  System.out.print("Tienes " +intentRest+ " intentos. ");
                  res = pedirNumInt();
                  if (res != num) {
                        mayor = res > num ? "menor" : "mayor";
                        System.out.println("El número es " + mayor + " al número dado");
                        --intentRest;
                  }else{
                        System.out.println("Acertaste");
                        break;
                  }
            }
            
            System.out.println(res != num ? "Has perdido, el número era: " + num : "Has ganado con " + intentRest + " intentos restantes");
      }
}