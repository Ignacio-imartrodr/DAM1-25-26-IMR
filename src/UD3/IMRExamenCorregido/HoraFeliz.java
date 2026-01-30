package UD3.IMRExamenCorregido;

import java.time.LocalTime;
import java.util.Random;
/**
 * @author Ignacio MR
 */
public class HoraFeliz {
    public static void main(String[] args) {
        int[] horaFeliz = generarHoraFeliz();
        LocalTime horaLocal = LocalTime.now();
        int horaSistema = horaLocal.getHour();
        int minutosSistema = horaLocal.getMinute();
        //antes horaSist < horaFeliz[0] o horaSist==horaFeliz[0] y minutosSistema < horaFeliz[1]
        //durante
        //despues horaSistema == horaFeliz[0]+1 y minutosSistema > horaFeliz[1] o horaSistema > horaFeliz[0]+1

        if (horaSistema < horaFeliz[0] || (horaSistema==horaFeliz[0] && minutosSistema < horaFeliz[1])){
            System.out.printf("La hora feliz es a las %02d:%02d %n", horaFeliz[0], horaFeliz[1]);
            int minRestantes;
            int horasRestantes = horaFeliz[0]-horaSistema;
            if (minutosSistema < horaFeliz[1]) {
                minRestantes = horaFeliz[1]-minutosSistema;
            } else {
                minRestantes = (60+horaFeliz[1])- minutosSistema; 
                if (minRestantes == 60){
                    minRestantes = 0;
                    horasRestantes++;
                }
            }
            System.out.printf("Aún faltan %02d horas y %02d minutos", horasRestantes, minRestantes);
        } else if(( (horaSistema == horaFeliz[0]+1 && minutosSistema > horaFeliz[1]) || horaSistema > horaFeliz[0]+1)){
            System.out.printf("La hora feliz ya ha terminado, fué a las %02d:%02d, más suerte la proxima vez!", horaFeliz[0], horaFeliz[1]);
        } else {
            System.out.printf("Bienvenido a la hora feliz! Comenzó a las %02d:%02d", horaFeliz[0], horaFeliz[1]);
        }

    }
    static Random rnd = new Random();
    static int[] generarHoraFeliz(){
        int hora = rnd.nextInt(24);
        int minutos = rnd.nextInt(60);
        int[] horaFeliz = new int[] {hora, minutos};
        return horaFeliz;
    }
}
