package UD2;
import java.util.Scanner;

/** 
 * E0214. Crear una aplicación que solicite al usuario una fecha (día, mes, año) y muestre la fecha 
correspondiente al día siguiente.  
 */
/**
 * @Author Ignacio MR
 */
public class E0214_DiaDespues {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Que año es?: ");
        int anho = sc.nextInt();

        System.out.print("Que mes es?: ");
        int mes = sc.nextInt();
        while (mes < 1 || mes > 12) {
            System.out.println("Ese mes no es posible");
            System.out.print("Que mes es?: ");
            mes = sc.nextInt();
        }

        System.out.print("Que día es?: ");
        int dia = sc.nextInt();

        int nDia = switch (mes) {
            case 1, 3, 5, 7, 8, 10, 12 -> {
                while (dia < 1 || dia > 31) {
                    System.out.println("Ese dia no es posible");
                    System.out.print("Que día es?: ");
                    dia = sc.nextInt();
                }
                yield nDia = (dia + 1) > 31 ? 1 : dia + 1;

            }
            case 4, 6, 9, 11 -> {
                while (dia < 1 || dia > 30) {
                    System.out.println("Ese dia no es posible");
                    System.out.print("Que día es?: ");
                    dia = sc.nextInt();
                }
                yield nDia = (dia + 1) > 30 ? 1 : dia + 1;
            }
            case 2 -> {
                if (anho % 400 == 0 || anho % 4 == 0 && anho % 100 != 0) {
                    while (dia < 1 || dia > 29) {
                        System.out.println("Ese dia no es posible");
                        System.out.print("Que día es?: ");
                        dia = sc.nextInt();
                    }
                    yield nDia = (dia + 1) > 29 ? 1 : dia + 1;
                } else {
                    while (dia < 1 || dia > 28) {
                        System.out.println("Ese dia no es posible");
                        System.out.print("Que día es?: ");
                        dia = sc.nextInt();
                    }
                    yield nDia = (dia + 1) > 28 ? 1 : dia + 1;
                }
            }
            default -> {
                yield nDia = -1;
            }
        };
        sc.close();
        int nMes = nDia == 1 ? mes + 1 : mes;
        int nAnho = (nDia == 1 && nMes == 1) ? mes + 1 : mes;
        System.out.printf("La fecha de mañana será %d/%d/%d", nDia, nMes, nAnho);
    }
}
