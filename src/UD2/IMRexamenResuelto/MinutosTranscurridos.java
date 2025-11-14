package UD2.IMRexamenResuelto;
/**
 * @author Ignacio MR
*/
public class MinutosTranscurridos {
    static int minutosTranscurridos(int horaA, int minutoA, int horaB, int minutoB) {
        if (((horaA < 0 || horaA > 23)||(horaB < 0 || horaB > 23))||((minutoA < 0 ||minutoA > 59)||(minutoB < 0 || minutoB > 59))) {
            return -1;
        }
        int difMin;
        int difHMin;
        if (horaA >= horaB) {
            difHMin = (horaA - horaB)*60;
            difMin = 60-((60-minutoA)+ minutoB);
        } else {
            difHMin = (horaB - horaA)*60;
            difMin = 60-((60-minutoB)+ minutoA);
        }
        return difHMin + difMin;
    }

    public static void main(String[] args) {
        final int minutoA = 13;
        final int horaA = 20;
        final int minutoB = 10;
        final int horaB = 10;
        int diferenciaEnMinutos = minutosTranscurridos(horaA, minutoA, horaB, minutoB);
        System.out.printf(diferenciaEnMinutos != -1 ? "Hay una diferencia de "+ diferenciaEnMinutos +" minutos entre las dos horas" : "Error en la entrada de datos");
    }
}
