import java.util.Scanner;

/** 
* 
*/

/** 
* @Author Ignacio MR
*/
public class AdivinaElNumero {
      public static void main(String[] args) throws Exception {

            long num;
            int res;
            String mayor;

            num = Math.round(Math.random()*100);
            System.out.print("Acierta un numero del 0 al 100: ");
            Scanner sc = new Scanner(System.in);
            res = sc.nextInt();
            
            
            while (res != num) {
                  mayor = res>num ? "menor" : "mayor";
                  System.out.println("El número es " + mayor + " al número dado");
                  System.out.println("Fallaste");
                  System.out.print("Acierta un numero del 0 al 100: ");
                  res = sc.nextInt();

            }
            sc.close();
            System.out.println("Acertaste");



      }
}