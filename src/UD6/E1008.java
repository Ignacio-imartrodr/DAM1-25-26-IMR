package UD6;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * @author Ignacio MR
 * 
 * Escribir un programa que duplique el contenido de un fichero cuyo nombre se pide
 * al usuario. El fichero copia tendrá el mismo nombre con el prefijo “copia_de_".
 * 
 * Utiliza FileChooser de JavaFX para que el usuario seleccione el fichero.
 */
public class E1008 extends Application {
    private String ruta = null;
    private String nomArchOrig = null;

    Label lbl;
    Label res;

    @Override
    public void start(Stage primaryStage) throws Exception {
        lbl = new Label("Escoge el archivo a duplicar");

        Button selc = new Button("Seleccionar archivo");
        selc.setOnAction(e -> escogerRuta(primaryStage));

        Button copy = new Button("Duplicar archivo");
        selc.setOnAction(o -> pulsarBtnDuplicar());

        res = new Label();

        VBox root = new VBox(lbl, selc, copy, res);
        Scene scene = new Scene(root, 500, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void pulsarBtnDuplicar() {
        if (duplicarArchivo()) {
            res.setText("Archivo copiado correctamente");
        } else {
            res.setText("Error copiando el archivo");
        }
    }
    private boolean duplicarArchivo() {
        if (ruta == null) {
            lbl.setText("Seleccione el archivo a duplicar primero");
            return false;
        }
        try (
            BufferedReader in = new BufferedReader(new FileReader(ruta));
            BufferedWriter writer = new BufferedWriter(new FileWriter("copia_de_" + nomArchOrig));
                ){
            writer.write(in.readAllAsString());
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    private void escogerRuta() {
        Stage stage = new Stage(); //TODO estoo hay que revisarlo
        FileChooser elector = new FileChooser();
        stage.show();
        File archivo = elector.showOpenDialog(stage);
        ruta = archivo.getAbsolutePath();
        String[] partes = ruta.split("[\\/]");
        nomArchOrig = partes[partes.length - 1];
        lbl.setText("Archivo escogido: " + nomArchOrig);

    }
    public static void main(String[] args) {
        launch(args);
    }
}
