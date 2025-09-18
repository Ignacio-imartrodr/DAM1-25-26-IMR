import java.util.Scanner;
/** 
 * @Author Ignacio MR
 */
public class E0106_Media {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Cual es tu primera nota? ");
        Byte nota1 = sc.nextByte();
        System.out.print("Cual es tu segunda nota? ");
        Byte nota2 = sc.nextByte();
        System.out.print("Cual es tu tercera nota? ");
        Byte nota3 = sc.nextByte();
        sc.close();
        double media=(nota1+nota2+nota3)/3;
        System.out.println("Tu media aritmetica es: "+media);
    }
}