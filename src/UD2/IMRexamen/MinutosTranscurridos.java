package UD2.IMRexamen;
/**
 * @author Ignacio MR
*/
public class MinutosTranscurridos {
    static int minutosTranscurridos(int horaA, int minutoA, int horaB, int minutoB) {
        int minTrans;
        if (((horaA > 23 || horaA < 0) || (horaB > 23 || horaB < 0))) {
            return -1;
        } else if ((minutoA > 59 || minutoA < 0) || (minutoB > 59 || minutoB < 0)) {
            return -1;
        }
        int difH;
        int difMin;

        if (horaA > horaB) {
            difH = horaA - horaB;
        } else {
            difH = horaB - horaA;
        }
        if (minutoA > minutoB) {
            difMin = horaA - horaB;
        } else {
            difMin = horaB - horaA;
        }
        minTrans = difMin + difH * 60;
        return minTrans;
    }

    public static void main(String[] args) {
        final int minutoA = 13;
        final int horaA = 20;
        final int minutoB = 10;
        final int horaB = 10;
        System.out.println(minutosTranscurridos(horaA, minutoA, horaB, minutoB));
    }
}
