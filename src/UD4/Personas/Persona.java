package UD4.Personas;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Persona {
    String nombre;
    String apellido1;
    String apellido2;
    LocalDate fechaNacimiento;
    
    private static Scanner sc = new Scanner(System.in,"Windows-1252");
    public void pedirNombreCompleto(){
        System.out.print("Nombre: ");
        nombre = pedirPorTeclado();
        System.out.print("Primer apellido: ");
        apellido1 = pedirPorTeclado();
        System.out.print("Segundo apellido: ");
        apellido2 = pedirPorTeclado();
    }
    public void pedirFechaNacimiento(){
        System.out.print("Fecha de nacimiento (dd/mm/aaaa): ");
        fechaNacimiento = pedirFecha();
    }
    private String pedirPorTeclado(){
        String var;
        boolean sonNumeros = true;
        try {
            var = sc.nextLine();
            for (int i = 0; i < var.length() && sonNumeros; i++) {
                if (!Character.isDigit(var.charAt(i)) || var.charAt(i) != '/') {
                    sonNumeros = false;
                }
            }
            if (!sonNumeros) {
                String charCorrectos = "áéíóúüñ";
                String charIncorrectos = " ¡¢£¤"; //Para ISO-8859-1
                String charIncorrectos1 = " ‚¡¢£�¤";//Para Windows-1252
                String[] StringsIncorrectos = new String[] {charIncorrectos, charIncorrectos1};
                for (int i = 0; i < var.length(); i++) {
                    for (int j = 0; j < StringsIncorrectos.length; j++) {
                        for (int k = 0; k < StringsIncorrectos[j].length(); k++) {
                            var = var.replace(StringsIncorrectos[j].charAt(k), charCorrectos.charAt(k));
                        }
                    }
                }    
            }
        } catch (Exception e) {
            sc.nextLine();
            System.out.println("Error, vuelve a intentar");
            return pedirPorTeclado();
        }
        return var;
    }
    private LocalDate pedirFecha(){
        LocalDate fecha;
        String var;
        try {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            var = sc.nextLine();
            fecha = LocalDate.parse(var, formato);
        } catch (Exception e) {
            sc.reset();
            System.out.println("Formato erróneo, vuelve a intentar");
            return pedirFecha();
        }
        return fecha;
    }
}
