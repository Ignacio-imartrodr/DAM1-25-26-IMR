package Extras;

import java.util.Scanner;
public class VocalesDeUnaCadena {
    public static int numVocal(String cad) {
        int vocal = 0;
        for (int i = 0; i < cad.length(); i++){
            vocal = switch (cad.charAt(i)) {
            case 'a', 'e', 'i', 'o', 'u',
                 'A', 'E', 'I', 'O', 'U',
                 'á', 'é', 'í', 'ó', 'ú',
                 'Á', 'É', 'Í', 'Ó', 'Ú',
                 'ä', 'ë', 'ï', 'ö', 'ü',
                 'Ä', 'Ë', 'Ï', 'Ö', 'Ü' ->{
                yield vocal+1;}
            default->{
                yield vocal+0;}
            };
        }
        return vocal;
    }
    public static void main(String[] args) {

        System.out.print("Dame una cadena de texto para analizar si tiene una vocal: ");
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        sc.close();
        int numVocal = numVocal(str);
        String plSing = numVocal==1 ? " vocal" : " vocales";
        System.out.print(numVocal>0 ? "La cadena tiene "+ numVocal + plSing : "La cadena NO tiene vocales");
        
    }
}
