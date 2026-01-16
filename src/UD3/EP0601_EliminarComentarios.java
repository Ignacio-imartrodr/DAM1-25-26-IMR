package UD3;

import java.util.Scanner;

public class EP0601_EliminarComentarios {
    static Scanner sc = new Scanner(System.in);
    static String pedirCodigo(){
        String cadena;
        try {
            cadena = sc.nextLine();
        } catch (Exception e) {
            System.err.println("Error, vuelve a intentarlo: ");
            sc.nextLine();
            return pedirCodigo();
        }
        return cadena;
    }
    static String eliminarComentarios(String cadena){
        String cadSinComent = "";
        final String CAD_COMIENZO_COMENTARIO = "/*";
        final String CAD_FIN_COMENTARIO = "*/";
        int apertura = cadena.indexOf(CAD_COMIENZO_COMENTARIO);
        int cierre = 0;
        while (apertura != -1) {
            cadSinComent += cadena.substring(cierre, apertura);
            cierre = cadena.indexOf(CAD_FIN_COMENTARIO) + 2;
            cadena = cadena.substring(cierre);
            cierre = cadena.indexOf(CAD_FIN_COMENTARIO) + 2;
            apertura = cadena.indexOf(CAD_COMIENZO_COMENTARIO);
        }
        return cadSinComent;
    }
    public static void main(String[] args) {
        String cadena;
        System.err.println("Escribe el codigo con comentarios:");
        cadena = pedirCodigo();
        String cadSinComent = eliminarComentarios(cadena);
        System.out.println(cadSinComent);
        //Cadena de prueba: 0/*3*/6/*9*/c 
    }
}
