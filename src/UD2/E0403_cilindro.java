package UD2;
import java.util.Scanner;
/**
 * @author Ignacio MR
 */
public class E0403_cilindro {
    public static double area(double altura, double radio) {
        return 2*Math.PI*radio*(altura+radio);
    }
    public static double superficie(double altura, double radio) {
        return Math.PI*Math.pow(radio,2)*altura;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Altura? ");
        int alt = sc.nextInt();

        System.out.print("Radio de la base? ");
        int rad = sc.nextInt();
        sc.close();
        double sup = superficie(alt, rad);
        double area = area(alt, rad);
        System.out.printf("El área del cilindro es %.2fu y la superficie %.2fu",area,sup);
    }
}
