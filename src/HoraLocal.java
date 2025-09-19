import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
/** 
 * @Author Ignacio MR
 */
public class HoraLocal {
    public static void main(String[] args) throws Exception {

        //Declaro variable
        LocalTime horaActual;
        LocalDate diaActual;

        //Obtengo la hora y fecha local
        horaActual = LocalTime.now();
        diaActual = LocalDate.now();

        //Expongo resultados
        System.out.println("Hora actual: " + horaActual);
        System.out.println("Fecha actual: " + diaActual);
        System.out.println("Fecha y hora actual: " + LocalDateTime.now());

    }
}