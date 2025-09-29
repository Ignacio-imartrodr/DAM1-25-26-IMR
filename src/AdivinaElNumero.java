import java.util.Scanner;

/** 
* Acierta un numero con 10 intentos y pistas de si es mayor o menor
*/

/** 
* @Author Ignacio MR
*/
public class AdivinaElNumero {
      public static void main(String[] args) throws Exception {

            long num;
            int res = -1;
            String mayor;
            byte cont;

            num = Math.round(Math.random()*100);
            cont = 10;
            Scanner sc = new Scanner(System.in);

            while (cont > 0) {
                  System.out.print("Tienes " +cont+ " intentos. ");
                  System.out.print("Acierta un numero del 0 al 100: ");
                  res = sc.nextInt();
                  if (res != num) {
                        System.out.println("Fallaste");
                        mayor = res >num ? "menor" : "mayor";
                        System.out.println("El número es " + mayor + " al número dado");
                        --cont;
                  }else{
                        System.out.println("Acertaste");
                        break;
                  }
            }
            sc.close();
            if (res != num) {
                  System.out.println("Has perdido, el número era: " + num);
            }else{
                  System.out.println("Has ganado con " + cont + " intentos restantes");
            }
      }
}