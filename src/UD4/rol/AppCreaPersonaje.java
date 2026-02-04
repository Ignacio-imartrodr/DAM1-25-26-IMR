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
        String respuesta;
        System.out.print("¿Quieres guardar el personaje en un fichero? (s/n): ");
        respuesta = Util.pedirPorTeclado(false);
        while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n")) {
            System.out.println("Responde unicamente con \"s\" o \"n\"");
            respuesta = Util.pedirPorTeclado(false);
        }
        if (respuesta.equalsIgnoreCase("s")) {
            rutaFichero = "src\\UD4\\rol\\personajesGuardados.json";
            Util.writeStringToFile(personaje.toJSONString(), rutaFichero);
            System.out.println("Personaje guardado en " + rutaFichero);
        }
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
                System.out.println("Razas disponibles:" + Personaje.getRazasStats());
                personaje.crearPersonaje();
                personaje.mostrar();
                
            }
        }
    }
}
