import java.util.Scanner;
/** 
 * E0109_Par. Programa que pida un número entero al usuario y que indique si es par mediante un literal booleano (true o false).
 */

/** 
 * @Author Ignacio MR
 */
public class E0109_Par {
    public static void main(String[] args) throws Exception {

        //Abrimo Variables
        int num;
        boolean par;
        
        //Recogemos datos
        Scanner sc = new Scanner(System.in);
        System.out.print("Dame un número: ");
        num = sc.nextInt();
        sc.close();
        
        //Operamos datos
        par = num / 2 == (num / 2) - (num % 2);

        //Mostramos resultados
        if (par == true) {
            System.out.println("Ese número es par");
        } else {
            System.out.println("Ese número NO es par");
        }
     }
}
