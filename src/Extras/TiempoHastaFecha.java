package Extras;


import java.time.LocalDate;

public class TiempoHastaFecha {
        public static void main(String[] args) {
        LocalDate hoy = LocalDate.now();
        System.out.println(hoy);

        int anhoHoy = hoy.getYear();
        System.out.println(anhoHoy);

        LocalDate inicioVacaciones = LocalDate.of(anhoHoy, 12, 19);
        System.out.println(inicioVacaciones);

        LocalDate finVacaciones = LocalDate.of(anhoHoy+1, 1, 8);
        System.out.println(finVacaciones);

        if (hoy.isAfter(inicioVacaciones) && hoy.isBefore(finVacaciones)) {
            System.out.println("Estamos de vacaciones");
        } else {
            System.out.println("Aún no estamos de vacaciones de Navidad");
            int dias = 0;// TODO Calcular
            System.out.println("Faltan... " + dias + " dias");
        }
    }
}
