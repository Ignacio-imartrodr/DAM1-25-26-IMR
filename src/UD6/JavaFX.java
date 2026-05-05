package UD6;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Conversor. Crea una aplicación que permita convertir cantidades de un sistema de 
medida a otro. Por ejemplo, de euros a dólares (1 € = 1,13 $), de libras a kilos (1 libra = 
0.4535924 kilos), de millas a kilómetros (1 milla = 1.609344 kilómetros), etc. 
Experimenta con nuevos controles para ampliar el programa y que permita convertir en 
ambos sentidos o cambiar de un tipo de conversión a otra. 
 */
public class JavaFX extends Application {
    final double LIB_TO_KG = 0.4535924;
    final double EUR_TO_DOLAR = 1.13;
    final double MILLA_TO_KM = 0.4535924;
    Button btn = new Button("Convertír");
    ChoiceBox<String> conversChoiceBox = new ChoiceBox<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        conversChoiceBox
        btn.setOnAction(e -> {(convertir())});

        VBox root = new VBox(btn);
        Scene scene = new Scene(root);
    }

    private void convertir(){

    }
    public static void main(String[] args) {
        launch();
    }
}
