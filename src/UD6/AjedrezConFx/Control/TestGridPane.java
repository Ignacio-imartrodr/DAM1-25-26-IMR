package UD6.AjedrezConFx.Control;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TestGridPane extends Application{
    private static final byte[][] TABLERO_EN_BLANCO = new byte[][]{
        {0, 1, 0, 1, 0, 1, 0, 1},
        {1, 0, 1, 0, 1, 0, 1, 0},
        {0, 1, 0, 1, 0, 1, 0, 1},
        {1, 0, 1, 0, 1, 0, 1, 0},
        {0, 1, 0, 1, 0, 1, 0, 1},
        {1, 0, 1, 0, 1, 0, 1, 0},
        {0, 1, 0, 1, 0, 1, 0, 1},
        {1, 0, 1, 0, 1, 0, 1, 0}
    };
    public static GridPane tableroEnBlanco(){
        GridPane gP = new GridPane();
        for (int i = 0, k=0; i < TestGridPane.TABLERO_EN_BLANCO.length; i++) {
            for (int j = 0; j < TestGridPane.TABLERO_EN_BLANCO[i].length; j++) {

                Label casilla = new Label(String.valueOf(k));
                casilla.setTextFill(Color.GREEN);
                k++;

                if (TABLERO_EN_BLANCO[i][j] == 1) {
                    casilla.setStyle("-fx-background-color: black;");
                }

                casilla.setPrefSize(50, 50);
                gP.add(casilla, i, j);
            }
        }
        return gP;
    }
    public static GridPane inizializarTablero(){
        GridPane gP = tableroEnBlanco();

        for (int i = 0, k = 0; i < gP.getColumnCount(); i++) {
            for (int j = 0; j < gP.getRowCount(); j++, k++) {
                
                System.out.println(gP.getChildren().get(k).toString());
                
            }
        }
        return gP;
    }
    public static void main(String[] args) {
        launch(args);
        inizializarTablero();
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane gP = inizializarTablero();

        Scene scene = new Scene(gP);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
