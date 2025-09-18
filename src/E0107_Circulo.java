import java.util.Scanner;
/** 
 * @Author Ignacio MR
 */
public class E0107_Circulo {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Cual es radio del circulo? ");
        double radio = sc.nextDouble();
        sc.close();
        double perimetro = 2*3.1416*radio;
        double area = 2*3.1416*radio*radio;
        System.out.println("Ese circulo tiene un perimetro:"+perimetro+" u");
        System.out.println("Y un area:"+area+" u^2");
    }  
}
