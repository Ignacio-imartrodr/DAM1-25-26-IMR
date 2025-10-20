package UD1;
import java.util.Scanner;
/** 
 * E0106_Media. Crea una aplicación que calcule la media aritmética de tres notas enteras. Ten en cuenta que la media puede tener decimales.
 */
/** 
 * @Author Ignacio MR
 */
public class E0106_Media {
    public static void main(String[] args) throws Exception {

        //Declaro variables
        Byte nota1;
        Byte nota2;
        Byte nota3;
        double media;

        //Pido datos
        Scanner sc = new Scanner(System.in);
        System.out.print("Cual es tu primera nota? ");
        nota1 = sc.nextByte();
        System.out.print("Cual es tu segunda nota? ");
        nota2 = sc.nextByte();
        System.out.print("Cual es tu tercera nota? ");
        nota3 = sc.nextByte();
        sc.close();

        //Opero datos
        media = (nota1 + nota2 + nota3) / 3;

        //Mostrar resultados
        System.out.println("Tu media aritmetica es: " + media);
    }
}