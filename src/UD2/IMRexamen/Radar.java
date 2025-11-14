package ud2.IMRexamen;
/**
 * @author Ignacio MR
*/
import java.util.InputMismatchException;
import java.util.Scanner;

public class Radar {
    static Scanner sc = new Scanner(System.in);

    static int pedirNum() {
        int n = 0;
        try {
            n = sc.nextInt();
        } catch (InputMismatchException e) {
            sc.nextLine();
            System.out.println("Introduce unicamente valores enteros.");
            pedirNum();
        }
        return n;
    }

    static String respuestaString(int limVelocidad, double velMedia){
        String respuesta;
        if (velMedia > limVelocidad + limVelocidad*0.2) {
            respuesta = "Condución  temeraria!!  Multa  con  retirada  de puntos!!";
        } else if (velMedia > limVelocidad) {
            respuesta = "Velocidade  excesiva!  Multa!";
        } else {
            respuesta = "Velocidade dentro do límite";
        }
        return respuesta;
    }

    static double velMedia(int time, int dist) {
        double horas = time/3600.0;
        double media = dist/horas;
        return media;
    }

    static double mediaTotal(int velMediaTotal, int cantCoches) {
        return velMediaTotal/(double)cantCoches;
    }
    public static void main(String[] args) {
        int distancia;
        int limVelocidad;
        int seg;
        int contCoches = 0;
        int sumaVelMedias = 0;
        double velMedia = 0;
        double velMaxReg = 0;
        int contMultas = 0;
        String respuesta;

        //Datos tramo
        do {
            System.out.print("Distancia del tramo (Km): ");
            distancia = pedirNum();
        } while (distancia >= 0);
        do {
            System.out.print("Velocidad máxima permitida (Km/h): ");
            limVelocidad = pedirNum();
        } while (limVelocidad >= 0);
        System.out.println("Introduce los tiempos en segundos en recorrer el tramo (0 o Negativo para salir del programa): ");
        seg = pedirNum();
        
        //Cuerpo del programa
        while (seg > 0){
            contCoches++;
            velMedia = velMedia(seg, distancia);
            respuesta = respuestaString(limVelocidad, velMedia);
            System.out.printf("Velocidad Media: %.2f km/h %n",velMedia);
            System.out.println(respuesta);
            sumaVelMedias += velMedia;
            if (velMedia > velMaxReg){
                velMaxReg = velMedia;
            }
            if (respuesta != "Velocidade dentro do límite") {
                contMultas++;
            }
            seg = pedirNum();
        }

        // Salida
        System.out.println("Estadísticas finales:");
        System.out.println("----------------------");
        System.out.println("Total de coches controlados: " + contCoches);
        System.out.printf("Velocidad media de los coches: %.2f", mediaTotal(sumaVelMedias, contCoches));
        System.out.printf("Velocidad mmáxima registrada: %.2f", velMaxReg);
        System.out.println("Total de multas impuestas: " + contMultas);
    }
}
