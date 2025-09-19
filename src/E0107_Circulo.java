import java.util.Scanner;
/** 
 * E0107_Circulo. Aplicación que calcule el perímetro y el área de un círculo a partir del valor del radio introducido por teclado. El radio puede contener decimales.
 */
/** 
 * @Author Ignacio MR
 */
public class E0107_Circulo {
    public static void main(String[] args) throws Exception {
        //Declaro variables
        double radio;
        double perimetro;
        double area;

        //Recogemos los datos
        Scanner sc = new Scanner(System.in);
        System.out.print("Cual es radio del circulo? ");
        radio = sc.nextDouble();
        sc.close();

        //Operamos los datos
        perimetro = 2 * Math.PI * radio;
        area = Math.PI * Math.pow(radio,2);

        //Mostrar resultados
        System.out.println("Ese círculo tiene un perímetro:" + perimetro + " u");
        System.out.println("Y un área:" + area + " u^2");
    }  
}
