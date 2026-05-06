package UD6;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * 
 * @author Ignacio MR
 * 
 * Conversor. Crea una aplicación que permita convertir cantidades de un sistema
 * de
 * medida a otro. Por ejemplo, de euros a dólares (1 € = 1,13 $), de libras a
 * kilos (1 libra =
 * 0.4535924 kilos), de millas a kilómetros (1 milla = 1.609344 kilómetros),
 * etc.
 * Experimenta con nuevos controles para ampliar el programa y que permita
 * convertir en
 * ambos sentidos o cambiar de un tipo de conversión a otra.
 */
public class Conversor extends Application {
    final double LIB_TO_KG = 0.4535924;
    final double EUR_TO_DOLAR = 1.13;
    final double MILLA_TO_KM = 0.4535924;
    double conversor = MILLA_TO_KM;

    final Label TITULO = new Label("CONVERSOR DE UNIDADES");
    ChoiceBox<String> conversChoiceBox;
    CheckBox swapCheckBox;
    TextField num1;
    Button btn;
    Label resultado;

    @Override
    public void start(Stage primaryStage) throws Exception {
        final int WIDTH = 200;
        final int HIGHT = 200;

        TITULO.setMinWidth(WIDTH);
        TITULO.setAlignment(Pos.CENTER_LEFT);

        conversChoiceBox = new ChoiceBox<>();
        conversChoiceBox.getItems().addAll("Libra - Kg", "Euro - Dolar", "Milla - Km");
        conversChoiceBox.setValue("Milla - Km");
        conversChoiceBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> escogerConversor(newValue));

        swapCheckBox = new CheckBox("Alternar conversión");

        num1 = new TextField();
        num1.setPromptText("Inserta un número");
        num1.onKeyTypedProperty().set(e -> checkNum(num1));

        btn = new Button("Convertír");
        btn.setOnAction(e -> convertir());

        resultado = new Label();

        VBox root = new VBox(TITULO, conversChoiceBox, swapCheckBox, num1, btn, resultado);
        Scene scene = new Scene(root, WIDTH, HIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void checkNum(TextField tF) {
        boolean esDouble;
        String text = tF.getText();
        if (!text.isEmpty()) {
            try {
                Double.parseDouble(text);
                esDouble = true;
            } catch (NumberFormatException e) {
                esDouble = false;
            }
            if (!esDouble) {
                tF.deletePreviousChar();
            }
        }
    }

    private void escogerConversor(String newValue) {
        switch (newValue) {
            case "Libra - Kg":
                conversor = LIB_TO_KG;
                break;

            case "Euro - Dolar":
                conversor = EUR_TO_DOLAR;
                break;

            case "Milla - Km":
                conversor = MILLA_TO_KM;
                break;

            default:
                break;
        }
    }

    private void convertir() {
        Double res = Double.parseDouble(num1.getText());
        if (swapCheckBox.isSelected()) {
            res /= conversor;
        } else {
            res *= conversor;
        }
        resultado.setText("Resultado: " + res);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
