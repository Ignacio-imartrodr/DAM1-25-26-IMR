import java.util.Scanner;
/** 
 * @Author Ignacio MR
 */
public class LeerNumeroEntero {
    public static void main(String[] args) throws Exception {

        //Variables
        float num;
        boolean esEntero;

        //Recibimos datos
        Scanner sc = new Scanner(System.in);
        System.out.println("Dame un número: ");
        num = sc.nextInt(); //NO LEE NUMEROS DECIMALES
        sc.close();

        //Operamos datos
        esEntero = num % 1 == 0;

        //Mostramos resultados
        if (esEntero == true) {
            System.out.println("Ese número es entero");
        } else {
            System.out.println("Ese número NO es entero");
        }

    }
}
