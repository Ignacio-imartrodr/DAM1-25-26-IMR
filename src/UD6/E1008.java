package UD6;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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

        /*FileChooser elector = new FileChooser();
        elector.showOpenDialog(primaryStage);
        File archivo = elector.getInitialDirectory();*/
        
        Button selc = new Button("Seleccionar archivo");
        selc.setOnAction(e -> escogerRuta(primaryStage));

        Button copy = new Button("Duplicar archivo");
        copy.setOnAction(o -> pulsarBtnDuplicar());

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
            BufferedWriter writer = new BufferedWriter(new FileWriter("copia_de_" + nomArchOrig + ".txt"));
                ){
            writer.write(in.readAllAsString());
            in.close();
            writer.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    private void escogerRuta(Stage stage) {
        FileChooser elector = new FileChooser();
        ruta = elector.showOpenDialog(stage).getAbsolutePath();
        nomArchOrig = ruta.substring(ruta.lastIndexOf("\\") + 1, ruta.lastIndexOf("."));
        lbl.setText("Archivo escogido: " + nomArchOrig + "\nCon ruta: " + ruta);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
