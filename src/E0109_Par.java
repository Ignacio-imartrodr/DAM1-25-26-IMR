import java.util.Scanner;
/** 
 * Ejercicio EO109.java: Recibe un numero entero y responde si es par o no mediante un boolean (verdadero o falso)
 */

/** 
 * @Author Ignacio MR
 */
public class E0109_Par {
    public static void main(String[] args) throws Exception {

        int num;
        boolean par;

        Scanner sc = new Scanner(System.in);
        System.out.print("Dame un número: ");
        num = sc.nextInt();
        sc.close();
        
        par = num/2==(num/2)-(num%2);
        
        if (par==true) {
            System.out.println("Ese número es par");
        } else {
            System.out.println("Ese número NO es par");
        }
     }
}
