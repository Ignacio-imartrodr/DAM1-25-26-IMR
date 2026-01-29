package Extras.SamuExamen;

/**
 * @author Samuel
 */
public class GenerandoUsername {
    static String getUsername(String nombre, String ap1, String ap2) {
        int cantidadAp1 = 4;
        int cantidadAp2 = 4;
        nombre = nombre.stripLeading();
        ap1 = ap1.stripLeading();
        ap2 = ap2.stripLeading();

        if (ap1.length() < 4) {
            cantidadAp1 = ap1.length();
        }
        if (ap2.length() < 4) {
            cantidadAp2 = ap2.length();
        }
        if (ap1.contains(" ")) {
            cantidadAp1 = ap1.indexOf(" ");
        }
        if (ap2.contains(" ")) {
            cantidadAp2 = ap2.indexOf(" ");
        }

        String usuario = nombre.substring(0, 1) + ap1.substring(0, cantidadAp1) + ap2.substring(0, cantidadAp2);
        usuario = usuario.toLowerCase();
        usuario = usuario.replace("á", "a");
        usuario = usuario.replace("é", "e");
        usuario = usuario.replace("í", "i");
        usuario = usuario.replace("ó", "o");
        usuario = usuario.replace("ú", "u");
        usuario = usuario.replace("ü", "u");
        usuario = usuario.replace("ñ", "n");

        return usuario;
    }

    public static void main(String[] args) {
        System.out.println(getUsername("María", "Souto", "Souto"));
        System.out.println(getUsername("Óscar", "Graña", "Müller"));
        System.out.println(getUsername("Ángel", "Cos", "de la Torre"));
    }
}
