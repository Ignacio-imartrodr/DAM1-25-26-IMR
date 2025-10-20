package UD1;
import java.util.Scanner;
/** 
 * @Author Ignacio MR
 */
public class LeerNumeroEntero {
    public static void main(String[] args) throws Exception {

        //Variables
        double num;
        boolean esEntero;

        //Recibimos datos
        Scanner sc = new Scanner(System.in);
        System.out.println("Dame un número: ");
        num = sc.nextDouble();
        sc.close();

        //Operamos datos
        esEntero = num % 1 == 0;

        //Mostramos resultados
        System.out.println(esEntero ? "Ese número es entero" : "Ese número NO es entero");
    }
}
