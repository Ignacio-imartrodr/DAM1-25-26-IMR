import java.util.Scanner;
/**
 * @author Ignacio MR
 */
public class AnhoBisiesto {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Dime un año: ");
        int anho = sc.nextInt();
        sc.close();
        boolean cuatro = anho % 4 == 0;
        boolean cien = anho % 100 == 0;
        boolean cuatrocientos = anho % 400 == 0;
        boolean bisiesto;
        if (cuatro){
            if (cien && cuatrocientos){
                bisiesto = true;
            } else {
                bisiesto = false;
            }
        } else {
            bisiesto = false;
        }
        System.out.print(bisiesto ? "Ese año es bisiesto" : "Ese año NO es bisiesto");
    }
}
