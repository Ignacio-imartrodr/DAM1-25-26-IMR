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

        LocalDate finVacaciones = LocalDate.of(anhoHoy+1, 1, 18);
        System.out.println(finVacaciones);
        int dias = ((int) hoy.until(inicioVacaciones).getDays());
        boolean enVacaciones = (int) hoy.until(finVacaciones).getDays() > dias-((int) hoy.until(finVacaciones).getDays()) && dias <= 0;
        if (enVacaciones) {
            System.out.println("¡Estamos de vacaciones!");
        } else {
            System.out.println("Aún no estamos de vacaciones de Navidad");
            
            System.out.println(dias != 1 ?"Faltan... ¡" + (dias+1) + " dias!" : "¡Falta " + (dias+1) + " día!");
        }
    }
}
