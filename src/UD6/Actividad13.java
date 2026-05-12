package UD6;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * @author Ignacio MR
 *         Actividad 13. Ejercicio con GridPane:
 *         Crea una aplicación que contenga un GridPane con varios botones. Los
 *         botones deben estar distribuidos en el GridPane de manera que formen 
 *         una cuadrícula. Al hacer clic en cada botón, se debe mostrar un
 *         mensaje en la consola que indique qué botón ha sido
 *         pulsado y en qué posición se encuentra dentro del GridPane.
 */
public class Actividad13 extends Application {
    byte[][] mapa = new byte[][]{
        {0, 1, 0, 1, 0, 1, 0, 1},
        {1, 0, 1, 0, 1, 0, 1, 0},
        {0, 1, 0, 1, 0, 1, 0, 1},
        {1, 0, 1, 0, 1, 0, 1, 0},
        {0, 1, 0, 1, 0, 1, 0, 1},
        {1, 0, 1, 0, 1, 0, 1, 0},
        {0, 1, 0, 1, 0, 1, 0, 1},
        {1, 0, 1, 0, 1, 0, 1, 0}
    };
    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane gP = new GridPane();
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                Label lbl = new Label();

                if (mapa[i][j] == 1) {
                    lbl.setStyle("-fx-background-color: black;");
                }

                lbl.setPrefSize(50, 50);
                gP.add(lbl, i, j);
            }
        }
        Scene scene = new Scene(gP);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
