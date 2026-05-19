package UD6.AjedrezConFx.Control;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class ControlTablero {
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
        for (int i = 0; i < ControlTablero.TABLERO_EN_BLANCO.length; i++) {
            for (int j = 0; j < ControlTablero.TABLERO_EN_BLANCO[i].length; j++) {
                Label casilla = new Label();

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

        for (int i = 0; i < gP.getColumnCount(); i++) {
            for (int j = 0; j < gP.getRowCount(); j++) {
                
                gP.getChildren().forEach(null);

            }
        }
        return gP;
    }
}
