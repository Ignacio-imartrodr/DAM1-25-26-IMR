package UD4.rol;

/** 
 * @author Ignacio MR
 * 
 * Crea un programa de consola que permita al usuario generar 
 * y editar personajes de diferentes modos y guardarlos
 * en disco en un fichero de texto en formato JSON. 
 */

public class AppCreaPersonaje {
    public static void guardarPersonaje(Personaje personaje){
        System.out.print("¿Quieres guardar el personaje en un fichero .json? (s/n): ");
        if (Util.confirmarSN()) {
            /*System.out.print("Ruta del fichero: ");
            String rutaFichero = Util.pedirPorTeclado(false); */
            String rutaFichero = "src\\UD4\\rol\\PersonajesGuardados.json";
            Util.writeStringToFile(personaje.toJSONString(), rutaFichero);
            System.out.println("Personaje guardado en " + rutaFichero);
        }
    }
    public static void main(String[] args) {
        boolean seguir = true;
        while (seguir) {
            System.out.print("¿Quieres crear un nuevo personaje? (s/n): ");
            if (!Util.confirmarSN()) {
                System.out.println();
                seguir = false;
            } else {
                Personaje personaje = new Personaje();
                System.out.println("\nRazas disponibles:\n\n" + Personaje.getRazasStats());
                personaje.crearPersonaje();
                personaje.mostrar();
                guardarPersonaje(personaje);
            }
        }

    }
}
