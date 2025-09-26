import java.util.Scanner;
/** 
 * EP0115_Horas. Convertir un número de segundos introducido por teclado en horas, minutos y segundos.
 */

/** 
 * @Author Ignacio MR
 */
public class EP0115_Horas {
     public static void main(String[] args) throws Exception {

           //Abrimos Variables
           int seg;
           int h;
           int min;
           int segs;

           //Recogemos datos
           Scanner sc = new Scanner(System.in);
           System.out.print("Dame un número de segundos: ");
           seg = sc.nextInt();
           sc.close();

           //Operamos datos
           h = seg / 3600;
           min = (seg % 3600) / 60;
           segs = (seg % 3600) % 60;

           //Mostramos resultados
           System.out.print(seg + " segundos son " + h + " horas, " + min + " minutos y " + segs + " segundos.");
     }
}
