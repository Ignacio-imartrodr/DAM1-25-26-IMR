package UD6.Examen.Corregido;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * @author Ignacio Martínez Rodriguez
 * 
 */
public class Codec extends Application{
    static String codecRuta = "src\\ud6\\examen\\imartrodr\\codec.txt";
    static String origenRuta = "src\\ud6\\examen\\imartrodr\\ficheroOrigen.txt";
    static String destinoRuta;
    TextArea original = new TextArea();
    TextArea resultado = new TextArea();


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
        //String textCodifOld = "";
        for (int i = 0; i < texto.length(); i++) {
            char letra = texto.charAt(i);
            boolean isUpperCase = Character.isUpperCase(letra);
            int pos = alfabeto.indexOf(letra);
            if (pos == -1) {
                pos = isUpperCase ? alfabeto.indexOf(Character.toLowerCase(letra)) : alfabeto.indexOf(Character.toUpperCase(letra));
                if (pos == -1) {
                    textCodif += letra;
                } else {
                    textCodif += isUpperCase ? Character.toUpperCase(cambio.charAt(pos)) : Character.toLowerCase(cambio.charAt(pos));
                }
            } else {
                textCodif += isUpperCase ? Character.toUpperCase(cambio.charAt(pos)) : Character.toLowerCase(cambio.charAt(pos));
            }
        }
        /*for (int i = 0; i < texto.length(); i++) {
            for (int j = 0; j < alfabeto.length(); j++) {
                String letraAlf = alfabeto.substring(j, (j + 1));
                String letraTxt = texto.substring(i, (i + 1));

                if (letraTxt.equals(letraAlf.toLowerCase())) {
                
                    textCodif += String.valueOf(cambio.charAt(j)).toLowerCase();

                } else if (letraTxt.equals(letraAlf.toUpperCase())) {
                
                    textCodif += String.valueOf(cambio.charAt(j)).toUpperCase();
                }
                if (j == (alfabeto.length() - 1) && textCodif.equals(textCodifOld)) {
                    textCodif += letraTxt;
                    textCodifOld = new String(textCodif);
                }
            }
        }*/

        return textCodif;
    }

    public static String codecExec(String rutaCodigo, String txtOrigen, String rutaDestino, boolean invertir){

        List<String> codigo = leerTxt(rutaCodigo);

        String txtCodificado = invertir ? codificar(txtOrigen, codigo) : codificar(txtOrigen, codigo.reversed());
        
        if (writeInTxt(rutaDestino, txtCodificado)) {
            System.out.println("Ejecución correcta");
        }

        return txtCodificado;
    }

    private static boolean validarRuta(String ruta){
        if (ruta == null) {
            return false;
        }
        File archivo = new File(ruta);
        return archivo.exists();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Label lblCodigo = new Label();
        lblCodigo.setText("Ruta al Codec:  ");

        TextField txtCodec = new TextField();
        txtCodec.setPromptText("Selecciona la ruta");
        txtCodec.setOnKeyTyped(e -> codecRuta = txtCodec.getText());

        Button codecBtn = new Button("Seleccionar codec");
        codecBtn.setOnAction(e -> {
            codecRuta = selecArchivo(primaryStage);
            txtCodec.setText(codecRuta);
        });

        HBox codecBox = new HBox(lblCodigo, txtCodec, codecBtn);
        codecBox.setPadding(new Insets(5));

        Label lblOrigin = new Label();
        lblOrigin.setText("Ruta texto origen:  ");

        TextField txtOrigen = new TextField();
        txtOrigen.setPromptText("Selecciona la ruta");
        txtOrigen.setOnKeyTyped(e -> {
            origenRuta = txtOrigen.getText();
            if (validarRuta(origenRuta)) {
                setAreaTextRuta(original, origenRuta);
            }
        });

        Button origenBtn = new Button("Seleccionar achivo de texto");
        origenBtn.setOnAction(e -> {
            origenRuta = selecArchivo(primaryStage);
            txtOrigen.setText(origenRuta);
            setAreaTextRuta(original, origenRuta);
        });

        HBox originBox = new HBox(lblOrigin, txtOrigen, origenBtn);
        originBox.setPadding(new Insets(5));

        Label lblDest = new Label();
        lblDest.setText("Ruta destino:  ");

        TextField rutaDest = new TextField();
        rutaDest.setPromptText("Selecciona una ruta para guardar el texto");
        rutaDest.setOnKeyTyped(e -> destinoRuta = rutaDest.getText());

        HBox destBox = new HBox(lblDest, rutaDest);
        destBox.setPadding(new Insets(5));

        Button filBtn = new Button("Seleccionar archivo con texto");
        filBtn.setOnAction(e -> destinoRuta = selecArchivo(primaryStage));

        Button btnExecute = new Button("Ejecutar");

        Label lblError = new Label();

        CheckBox decodCheck = new CheckBox("Decodificar");

        btnExecute.setOnAction(e -> {
            String codec = txtCodec.getText();
            String origen = original.getText();
            if(codec == null || codec.isBlank()){
                lblError.setText("Selecciona un codigo válido");
            } else if (origen == null || origen.isBlank()) {
                lblError.setText("Escoge un texto válido");
            } else {
                resultado.setText(codecExec(codecRuta, origen, destinoRuta, !decodCheck.isSelected()));
                lblError.setText("");
            }
            
        });

        HBox ejecutarBox = new HBox(btnExecute, decodCheck);
        ejecutarBox.setPadding(new Insets(5));
        HBox.setMargin(decodCheck, new Insets(4));

        VBox root = new VBox(codecBox, originBox, destBox, original, ejecutarBox, lblError, resultado);
        root.setMinSize(150, 200);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private String selecArchivo(Stage stage) {
        FileChooser elector = new FileChooser();
        return elector.showOpenDialog(stage).getAbsolutePath();
    }

    private void setAreaTextRuta(TextArea ta, String ruta){
        List<String> txt = leerTxt(ruta);
        String texto = "";
        for (String string : txt) {
            texto += string + "\n";
        } 
        if (texto.length() > 3) {
            texto = texto.substring(0, texto.length() -2);
        }
        ta.setText(texto);
    }
}
