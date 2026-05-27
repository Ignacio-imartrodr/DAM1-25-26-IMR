package UD6.Examen.Entregado;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
//import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * @author Ignacio Martínez Rodriguez
 * 
 */
public class Codec extends Application{
    static final String CODEC = "src\\ud6\\examen\\imartrodr\\codec.txt";
    static final String ORIGEN = "src\\ud6\\examen\\imartrodr\\ficheroOrigen.txt";
    static final String DESTINO1 = "src\\ud6\\examen\\imartrodr\\ficheroCodificado.txt";
    static final String DESTINO2 = "src\\ud6\\examen\\imartrodr\\ficheroDecodificado.txt";


    private static List<String> leerTxt(String rutaObjetivo){
        try (BufferedReader in = new BufferedReader(new FileReader(rutaObjetivo))) {
            List<String> txt =  new ArrayList<>(in.readAllLines());
            return txt;
        } catch (Exception e) {
            System.err.println("Hubo un error leyendo el archivo");
            return null;
        }
    }

    private static boolean writeInTxt(String rutaObjetivo, String texto){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaObjetivo))) {
            writer.write(texto);
            writer.flush();
            return true;
        } catch (Exception e) {
            System.err.println("Hubo un error escribiendo en el archivo");
            return false;
        }
    }

    private static String codificar(String texto, List<String> codigo){
        if (codigo.size() != 2 || codigo.get(0).length() != codigo.get(1).length()) {
            System.err.println("formato del código invalido");
            return null;
        }
        // Mi codificador sustituye mayúsculas y minusculas manteniendolas en el resultado
        String alfabeto = codigo.get(0);
        String cambio = codigo.get(1);
        String textCodif = "";
        String textCodifOld = "";
        for (int i = 0; i < texto.length(); i++) {
            for (int j = 0; j < alfabeto.length(); j++) {
                String letraAlf = alfabeto.substring(j, (j + 1));
                String letraTxt = texto.substring(i, (i + 1));

                if (letraTxt.equals(letraAlf.toLowerCase())) {
                
                    textCodif += String.valueOf(cambio.charAt(j)).toLowerCase();

                } else if (letraTxt.equals(letraAlf.toUpperCase())) {
                
                    textCodif += String.valueOf(cambio.charAt(j)).toUpperCase();
                }
                if ( j == (alfabeto.length() - 1) && textCodif.equals(textCodifOld)) {
                    textCodif += letraTxt;
                    textCodifOld = new String(textCodif);
                }
            }
        }

        return textCodif;
    }

    public static void codecMain(String rutaCodigo, String rutaOrigen, String rutaDestino, boolean invertir){
        List<String> codigo = leerTxt(rutaCodigo);
        List<String> texto = leerTxt(rutaOrigen);

        String txtCodificado = "";
        for (int i = 0; i < texto.size(); i++) {
            String linea = invertir ? codificar(texto.get(i), codigo) : codificar(texto.get(i), codigo.reversed());
            if (i != texto.size() - 1) {
                linea += "\n";
            }

            txtCodificado += linea;
        }
        
        if (writeInTxt(rutaDestino, txtCodificado)) {
            System.out.println("Ejecución correcta");   
        }

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //FileChooser selec = new FileChooser();
        Button filButton = new Button("Seleccionar archivo");

        Button btn = new Button("Ejecutar");
        CheckBox decod = new CheckBox("Decodificar");

        btn.setOnAction(e -> codecMain(CODEC, decod.isSelected() ? DESTINO1 : ORIGEN, decod.isSelected() ? DESTINO2 : DESTINO1, !decod.isSelected()));

        VBox root = new VBox(filButton, btn, decod);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
