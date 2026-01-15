package UD4;

import java.util.Arrays;

public class E0612_Anagrama {
    public static void main(String[] args) {
        System.out.println(sonAnagrama("n ul l", " n lul"));
        System.out.println(sonAnagrama("nu ll", "nlu p"));
    }
    static boolean sonAnagrama(String texto1, String texto2){
        boolean sonAnagrama = false;
        char[] letras1 = texto1.replaceAll(" ","").toLowerCase().toCharArray();
        char[] letras2 = texto2.replaceAll(" ","").toLowerCase().toCharArray();
        Arrays.sort(letras1);
        Arrays.sort(letras2);        
        if (Arrays.equals(letras1, letras2)) sonAnagrama = true;
        return sonAnagrama;
    }
}
