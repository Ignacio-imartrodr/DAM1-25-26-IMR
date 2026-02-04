package UD4.rol;

/** 
 * @author Ignacio MR
 * 
 * Crea un programa de consola que permita al usuario generar 
 * y editar personajes de diferentes modos y guardarlos
 * en disco en un fichero de texto en formato JSON. 
 */

public class AppCreaPersonaje {
    public static void GuardarPersonaje(Personaje personaje, String rutaFichero){
        Util.writeStringToFile(personaje.toJSONString(), rutaFichero);
    }
    public static void main(String[] args) {
        String respuesta;
        boolean seguir = true;
        while (seguir) {
            System.out.print("¿Quieres crear un nuevo personaje? (s/n): ");
            respuesta = Util.pedirPorTeclado(false);
            while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n")) {
                System.out.println("Responde unicamente con \"s\" o \"n\"");
                respuesta = Util.pedirPorTeclado(false);
            }
            if (respuesta.equalsIgnoreCase("n")) {
                seguir = false;
            } else {
                Personaje personaje = new Personaje();
                System.out.println("Razas disponibles:");
                personaje.getRazasStats();
                personaje.crearPersonaje();
                personaje.mostrar();
                System.out.print("¿Quieres guardar el personaje en un fichero? (s/n): ");
                respuesta = Util.pedirPorTeclado(false);
                while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n")) {
                    System.out.println("Responde unicamente con \"s\" o \"n\"");
                    respuesta = Util.pedirPorTeclado(false);
                }
                if (respuesta.equalsIgnoreCase("s")) {
                    /*System.out.print("Introduce la ruta del fichero donde se guardará el personaje: ");
                    String rutaFichero = Util.pedirPorTeclado(false);*/
                    String rutaFichero = "src\\UD4\\rol\\personajesGuardados.json";
                    GuardarPersonaje(personaje, rutaFichero);
                    System.out.println("Personaje guardado en " + rutaFichero);
                }
            }
        }
    }
}
