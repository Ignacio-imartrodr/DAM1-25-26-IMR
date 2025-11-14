package OTROS;

public class DiferenciaDeHorasExamen {
    static int diferenciaDeMinutos(int horaA, int horaB, int minutoA, int minutoB) {
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
        int diferenciaEnMinutos = diferenciaDeMinutos(0,22,21,50);
        System.out.printf(diferenciaEnMinutos != -1 ? "Hay una diferencia de "+ diferenciaEnMinutos +" minutos entre las dos horas" : "Error en la entrada de datos");
    }
}
