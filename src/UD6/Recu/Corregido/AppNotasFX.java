package UD6.Recu.Corregido;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.util.List;

/**
 * @author Ignacio Martínez Rodríguez
 */
public class AppNotasFX extends Application{
    private ListView<String> listVProms;
    private Label lblError;

    @Override
    public void start(Stage primaryStage) {
        Label lblRuta = new Label(AppNotas.rutaNotas);
        HBox.setMargin(lblRuta, new Insets(0, 0, 0, 10));

        Button btnSelc = new Button("Seleccionar otro fichero.txt");
        btnSelc.setOnAction(e -> {
            AppNotas.rutaNotas = selecArchivo(primaryStage);
            lblRuta.setText(AppNotas.rutaNotas);
        });

        HBox boxRuta = new HBox(btnSelc, lblRuta);
        boxRuta.setPadding(new Insets(5));

        Button btnCalcular = new Button("Calcular y Guardar Promedios");
        btnCalcular.setOnAction(e -> procesarNotas());

        listVProms = new ListView<>();

        lblError = new Label();
        lblError.setVisible(false);

        VBox root = new VBox(10, boxRuta, btnCalcular, lblError, listVProms);
        root.setPadding(new Insets(5));

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void procesarNotas() {
        List<String> listP = AppNotas.getPromedios(AppNotas.rutaNotas);

        lblError.setText("");
        lblError.setVisible(true);

        if (listP == null || listP.isEmpty()) {
            lblError.setText("Error: No se encontraron datos o el archivo es inválido");
            listVProms.getItems().clear();
            return;
        }

        listVProms.getItems().clear();
        listVProms.getItems().addAll(listP);

        String txtPromeds = String.join("\n", listP);
        boolean guardado = AppNotas.writeInTxt(AppNotas.rutaPromedios, txtPromeds);

        if (guardado) {
            lblError.setVisible(false);
        } else {
            lblError.setText("Calculados en pantalla, pero falló el guardado.");
        }
    }

    private String selecArchivo(Stage stage) {
        FileChooser elector = new FileChooser();

        FileChooser.ExtensionFilter filtroTxt = new FileChooser.ExtensionFilter("Archivos de texto (*.txt)", "*.txt");
        elector.getExtensionFilters().add(filtroTxt);

        java.io.File archivo = elector.showOpenDialog(stage);
        
        if (archivo == null) {
            return AppNotas.rutaNotas;
        } else {
            return archivo.getAbsolutePath();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
