package UD3.IMRexamen;
/**
 * @author Ignacio MR
 */
public class GenerandoUsername {
    static String getUsername(String nombre, String ap1, String ap2){
        String username = "";
        String nom;
        String apell1;
        String apell2;
        nom = nombre.substring(0, 1);
        apell1 = ap1.split(" ")[0];
        apell2 = ap2.split(" ")[0];
        if (apell1.length() > 4){
            apell1 = apell1.substring(0, 5);
        }
        if (apell2.length() > 4){
            apell2 = apell2.substring(0, 5);
        }
        username = nom + apell1 + apell2;
        String charInvalidos = "áéíóúüñ";
        String charValidos = "aeiouun";
        for (int i = 0; i < username.length(); i++) {
            for (int j = 0; j < charInvalidos.length(); j++) {
                if (username.charAt(i) == charInvalidos.charAt(j)) {
                    username.replace(charInvalidos.charAt(j), charValidos.charAt(j));
                }
            }
        }
        return username.toLowerCase();
    }
}
