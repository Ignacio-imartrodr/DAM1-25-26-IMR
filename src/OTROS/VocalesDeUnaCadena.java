package OTROS;
import java.util.Scanner;
public class VocalesDeUnaCadena {
    public static boolean contieneVocal(String cad) {
        boolean vocal = false;
        for (int i = 0; i < cad.length(); i++){
            vocal = switch (cad.charAt(i)) {
            case 'a', 'e', 'i', 'o', 'u',
                 'A', 'E', 'I', 'O', 'U',
                 'á', 'é', 'í', 'ó', 'ú',
                 'Á', 'É', 'Í', 'Ó', 'Ú',
                 'ä', 'ë', 'ï', 'ö', 'ü',
                 'Ä', 'Ë', 'Ï', 'Ö', 'Ü' ->{
                yield true;}
            default->{
                yield false;}
            };
            if (vocal) {
                return vocal;
            }
        }
        return vocal;
    }
    public static void main(String[] args) {

        System.out.print("Dame una cadena de texto para analizar si tiene una vocal: ");
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        sc.close();
        System.out.print(contieneVocal(str)? "La cadena tiene vocales" : "La cadena NO tiene vocales");
        
    }
}
