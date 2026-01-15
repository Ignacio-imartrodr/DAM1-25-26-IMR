package UD4;

public class E0607_ContarPalabras {
    static int contarOcurenciasExactas (String texto, String palabra){
        int comienzo = texto.indexOf(palabra);
        int cont = 0;
        while (comienzo != -1) {
            texto = texto.substring(comienzo + palabra.length());
            comienzo = texto.indexOf(palabra);
            cont++;
        }
        return cont;
    }
    public static void main(String[] args) {
        String texto = " no sabes lo mucho que no soporto que me nieguen que pueda usar el ordenador";
        String palabra = "no";
        System.out.println(contarOcurenciasExactas(texto, palabra));//2
        palabra = "que";
        System.out.println(contarOcurenciasExactas(texto, palabra));//3
        palabra = "mucho";
        System.out.println(contarOcurenciasExactas(texto, palabra));//1
        
    }
}
