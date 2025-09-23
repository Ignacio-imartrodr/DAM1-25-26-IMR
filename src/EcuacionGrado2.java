import java.util.Scanner;
/** 
 * EcuacionGrado2. La fórmula para calcular las raíces de una ecuación de segundo grado 
 */

/** 
 * @Author Ignacio MR
 */
public class EcuacionGrado2 {
    public static void main(String[] args) throws Exception {

        //Abrimo Variables
        double a;
        double b;
        double c;

        //Recogemos datos
        Scanner sc = new Scanner(System.in);
        System.out.print("Dame el número 'a': ");
        a = sc.nextDouble();
        System.out.print("Dame el número 'b': ");
        b = sc.nextDouble();
        System.out.print("Dame el número 'c': ");
        c = sc.nextDouble();
        sc.close();
        
        //Operamos datos
        double x1 = (-b + Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a);
        double x2 = (-b - Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a);

        //Mostramos resultados
        if (Double.isNaN(x1) && Double.isNaN(x2)) {
            System.out.print("La ecuación no tiene solución real");
        } else {
            if (Double.isNaN(x1) || Double.isNaN(x2)) {
                System.out.print("El resultado de la ecuación cuadratica es: " + (Double.isNaN(x1) ? x2 : x1));
            } else {
                System.out.print("El resultado de la ecuación cuadratica son: " + x1 + " y " + x2);
            }
        }
    }
}
