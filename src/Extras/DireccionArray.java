package Extras;


public class DireccionArray {
    //La Dirección de un array es un String que empieza por el Tipo de varriables en el Array y luego tiene su la Ubicación numérica en la memoria
    static String extraerUbicacion(char[] t) {
        String direccion = t.toString();
        String resultado = "";
        for (int i = 3; i < direccion.length(); i++) {
            resultado += direccion.charAt(i);
        }
        return resultado;        
    }
    static String extraerTipo(char[] t) {   
        String direccion = t.toString();
        String resultado = "";
        for (int i = 0; i < 2; i++) {
            resultado += direccion.charAt(i);
        }
        return resultado;
    }
    public static void main(String[] args) {
        char[] string = {'t','i','p'};
        System.out.println(extraerUbicacion(string));
        System.out.println(extraerTipo(string));
    }
}
