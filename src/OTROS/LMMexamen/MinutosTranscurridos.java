package ud2.LMMexamen;

/**
 * @author Lunna Mendonça Miranda
 */

public class MinutosTranscurridos {

    public static void main(String[] args) {

        System.out.println(minutosTranscurridos(13,20,10,10));
        System.out.println(minutosTranscurridos(13,10,10,20));
        System.out.println(minutosTranscurridos(10,10,13,20));
        System.out.println(minutosTranscurridos(10,20,13,10));
        System.out.println(minutosTranscurridos(11,20,10,10));
        System.out.println(minutosTranscurridos(11,10,10,20));
        System.out.println(minutosTranscurridos(10,10,11,20));
        System.out.println(minutosTranscurridos(10,20,11,10));
        System.out.println(minutosTranscurridos(10,10,10,10));
        System.out.println(minutosTranscurridos(10,20,10,10));
        System.out.println(minutosTranscurridos(10,10,10,20));
        System.out.println(minutosTranscurridos(24,10,10,20));
        System.out.println(minutosTranscurridos(10,60,10,20));
        System.out.println(minutosTranscurridos(10,10,24,20) );
        System.out.println(minutosTranscurridos(10,10,10,60) );

    }
    static int minutosTranscurridos(int horaA, int minutoA, int horaB, int minutoB) {
        
        int tiempoTranscurrido = 0, minutosA = 0, minutosB = 0;

        try {
            if (horaA < 0 || horaA > 23 || horaB < 0 || horaB > 23 || minutoA < 0 || minutoA > 59 || minutoB < 0 || minutoB > 59) {
                tiempoTranscurrido = -1;
            } else {
                minutosA = horaA * 60 + minutoA;
                minutosB = horaB * 60 + minutoB;

                if (minutosA > minutosB) {
                    tiempoTranscurrido = minutosA - minutosB;
                } else if (minutosA < minutosB) {
                    tiempoTranscurrido = minutosB - minutosA;
                } else {
                    tiempoTranscurrido = 0;
                }
            }
        } catch (Exception e) {
            tiempoTranscurrido = -1;
        }
        return tiempoTranscurrido;
    }
}
