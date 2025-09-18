import java.util.Scanner;
/** 
 * @Author Ignacio MR
 */
public class E0109_Par {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Dame un numero: ");
        int num = sc.nextInt();
        sc.close();
        boolean par = num/2==(num/2)-(num%2);
        if (par==true) {
            System.out.println("Ese numero es par");
        } else {
            System.out.println("Ese numero NO es par");
        }
     }
}
