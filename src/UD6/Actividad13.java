package UD6;

import javafx.application.Application;
import javafx.scene.Scene;
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

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane gP = new GridPane();
        Scene scene = new Scene(gP);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
