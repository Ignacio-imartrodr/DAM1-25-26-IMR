package UD4.rol;

/** 
 * @author Ignacio MR
 * 
 * Crea un programa de consola que permita al usuario generar 
 * y editar personajes de diferentes modos y guardarlos
 * en disco en un fichero de texto en formato JSON. 
 */

public class AppCombateSingular {
    public static Personaje[] cargarPersonajesDeJson(String rutaFile){
        Personaje[] personajesJson;
        personajesJson = Util.readFileToStringArray(rutaFile);
    }
    public static void main(String[] args) {
        AppCreaPersonaje.main(args);
        String[] atributosPersonaje;
        Personaje[] PersonajesCreados;
        System.out.print("quieres cargar los personajes de un \".json\"? (s/n): ");
        if (Util.confirmarSN()) {
            /*System.out.print("Ruta del fichero: ");
            String rutaFichero = Util.pedirPorTeclado(false); */
            String rutaFichero = "src\\UD4\\rol\\PersonajesGuardados.json";
            cargarPersonajesDeJson(rutaFichero);
        }
        Util.readFileToStringArray(null);
    }
}
