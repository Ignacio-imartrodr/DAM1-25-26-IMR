package UD2;
import java.util.Random;
/**
 * @author Ignacio MR
 */
public class CalculadoraHumana {
    public static boolean esPrimo(int n) {
        boolean primo = true;
        if (n == 1 || n == 0) {
            primo = false;
        } else {
            for (int i = 2; i <= Math.pow(Math.abs(n), 1 / 2.0); ++i) {
                if (n % i == 0) {
                    primo = false;
                }
            }
        }
        return primo;
    }
    public static int generarOperador(int resAnterior) {
        /*Operador: Suma=1 Resta=2 Multiplicación=3 División=4*/
        int operador = 1;
        int operadorSec;
        Random rnd = new Random();

        if (esPrimo(resAnterior) || resAnterior<=50){  /*No puede ser División*/
           operador = rnd.nextInt(2) + 1;
        }

        if(resAnterior>100){  /*No puede ser Multiplicación*/
            operadorSec = rnd.nextInt(2) + 1;
            if (operadorSec==3){
                operador=4;
            }else{
                operador=operadorSec;
            }
        }
        /*OperdorSec: Suma=1 Multiplicación=2 División=3 */
        if (resAnterior<=50){  /*No puede ser resta*/
            operadorSec = rnd.nextInt(2) + 1;
            if (operadorSec>=2){
                operador=operadorSec+1;
            }else{
                operador=operadorSec;
            }
        }

        /*OperadorSec: Resta=1 Multiplicación=2 División=3 */
        if (resAnterior>=150){  /*No puede ser Suma*/
            operadorSec = rnd.nextInt(2) + 1;
            operador=operadorSec+1;
        }
        
        return operador;
    }
}
