import java.util.Scanner;
/** 
 * EP0114_Segundos. Convertir un número de horas, minutos y segundos introducidos por teclado en un número total de segundos.
 */

/** 
 * @Author Ignacio MR
 */
public class EP0114_Segundos{
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
          System.out.print("Dame un número de minutos: ");
          min = sc.nextInt();
          System.out.print("Dame un número de horas: ");
          h = sc.nextInt();
          sc.close();

          //Operamos datos
          segs = seg + h * 3600 + min * 60;

          //Mostramos resultados
          System.out.print(h + " horas, " + min + " minutos y " + seg + " segundos son " + segs + " segundos.");
     }
}