package Extras.SamuExamen;

import java.time.LocalTime;
import java.util.Random;

/**
 * @author Samuel
 */
public class HoraFeliz {
    public static void main(String[] args) {
        Random rd = new Random();
        int hora = rd.nextInt(24);
        int minuto = rd.nextInt(60);
        int segundosHoraFeliz = 60 * 60;
        System.out.printf("La hora feliz es %02d:%02d %n", hora, minuto);
        int comienzoHoraFeliz = hora * 3600 + minuto * 60;
        int finalHoraFeliz = comienzoHoraFeliz + segundosHoraFeliz;
        LocalTime horaActual = LocalTime.now();
        int segundosActual = horaActual.toSecondOfDay(); // convierte la hora a segundos
        int minutosFaltantes = (comienzoHoraFeliz - segundosActual) / 60;
        if (segundosActual < comienzoHoraFeliz) {
            System.out.println("Aún no ha empezado la hora feliz, faltan " + minutosFaltantes + " minutos");
        } else if (segundosActual > finalHoraFeliz) {
            System.out.println("Ya ha pasado la hora feliz");
        } else {
            System.out.println("Estamos en la hora feliz");
        }
    }
}
