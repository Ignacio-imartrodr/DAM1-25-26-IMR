package UD3.IMRExamenCorregido;

import java.util.Arrays;

/**
 * @author Ignacio MR
 */
public class GenerandoUsername {
    public static void main(String[] args) {
        String[][] nombresCompletos = new String[][] {{"María", "Souto", "Souto"},{"Óscar", "Graña", "Müller"}, {"Ángel", "Cos", "de la Torre"},{"Ignacio","Martínez","Rodríguez"}};
        String[] usuarios = new String[0];
        for (int i = 0; i < nombresCompletos.length; i++) {
            usuarios = Arrays.copyOf(usuarios, usuarios.length + 1);
            usuarios[usuarios.length - 1] = getUsername(nombresCompletos[i][0], nombresCompletos[i][1], nombresCompletos[i][2]);  
        }
        System.out.println(Arrays.toString(usuarios));
    }
    static String getUsername(String nombre, String ap1, String ap2){
        String username = "";
        String nom;
        String apell1;
        String apell2;
        nom = nombre.substring(0, 1);
        apell1 = ap1.split(" ")[0];
        apell2 = ap2.split(" ")[0];
        if (apell1.length() > 4){
            apell1 = apell1.substring(0, 4);
        }
        if (apell2.length() > 4){
            apell2 = apell2.substring(0, 4);
        }
        username = (nom + apell1 + apell2).toLowerCase();
        String charInvalidos = "áéíóúüñ";
        String charValidos = "aeiouun";
        
        for (int i = 0; i < username.length(); i++) {
            for (int j = 0; j < charInvalidos.length(); j++) {
                username = username.replace(charInvalidos.charAt(j), charValidos.charAt(j));
            }
        }
        return username;
    }
}
