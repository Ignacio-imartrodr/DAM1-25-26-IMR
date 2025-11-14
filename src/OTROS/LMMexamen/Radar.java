package OTROS.LMMexamen;

import java.util.Scanner;

/**
 * @author Lunna Mendonça Miranda
 */
public class Radar {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("Introduzca la distancia en km: ");
        int distancia = sc.nextInt();

        System.out.print("Introduzca la velocidad máxima autorizada (km/h): ");
        int limiteVelocidad = sc.nextInt();

        radar(distancia, limiteVelocidad);
    }

    public static void radar(int distancia, int limiteVelocidad) {

        int segundos = 1, velocidadMedia = 0, horas = 0;

        System.out.println("Introduce los tiempos en segundos en recorrer el tramo (0, negativo): ");

        while (segundos > 0) {
            try {
                segundos = sc.nextInt();
                horas = ((segundos / 60)/ 60);
                velocidadMedia = distancia / horas;

                System.out.println("Velocidad media: " + velocidadMedia + "km/h.");

                if (velocidadMedia <= limiteVelocidad) {
                    System.out.println("Velocidade dentro do límite");
                } else if (velocidadMedia > (limiteVelocidad * 1.2)) {
                    System.out.println("“Velocidade excesiva! Multa!");
                } else {
                    System.out.println("Condución temeraria!! Multa con retirada de puntos!!");
                }

            } catch (Exception e) {
                System.out.println("Introduzca un número.");
            }
        }

    }
}
