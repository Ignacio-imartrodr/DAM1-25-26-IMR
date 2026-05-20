package UD6.AjedrezConFx.Control;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

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
    private static final char[] ORDEN_PIEZAS = new char[] {'t', 'c', 'a', 'q', 'r', 'a', 'c', 't'};

    
    public static GridPane inizializarTablero(){

        GridPane gP = new GridPane();

        for (int i = 0; i < ControlTablero.TABLERO_EN_BLANCO.length; i++) {
            for (int j = 0; j < ControlTablero.TABLERO_EN_BLANCO[i].length; j++) {
                Label casilla = new Label();
                casilla.setAlignment(Pos.CENTER);
                casilla.setFont(Font.font("Arial", FontWeight.BOLD, 24));
                casilla.setPrefSize(50, 50);

                if (TABLERO_EN_BLANCO[i][j] == 1) {
                    casilla.setStyle("-fx-background-color: #4B7399;"); // Un azul oscuro en lugar de negro puro
                } else {
                    casilla.setStyle("-fx-background-color: #EAE9D2;"); // Un color crema
                }

                // Colocar las piezas según la fila (j)
                if (j == 0) {
                    // Fila 0: Piezas negras principales
                    casilla.setText(String.valueOf(ORDEN_PIEZAS[i]));
                    casilla.setTextFill(Color.BLACK);
                } else if (j == 1) {
                    // Fila 1: Peones negros
                    casilla.setText("p");
                    casilla.setTextFill(Color.BLACK);
                } else if (j == 6) {
                    // Fila 6: Peones blancos
                    casilla.setText("P");
                    casilla.setTextFill(Color.WHITE);
                } else if (j == 7) {
                    // Fila 7: Piezas blancas principales (pasamos a mayúscula)
                    casilla.setText(String.valueOf(ORDEN_PIEZAS[i]).toUpperCase());
                    casilla.setTextFill(Color.WHITE);
                }

                gP.add(casilla, i, j);
            }
        }
        /*int colums = 8;
        for (int i = 0; i < 8; i++) {
            if (i == 2) {
                i += 4;    
            }
            int cont = 0;
            for (int j = i; cont < colums; j += 8, cont++) {
                String pieza;
                if (i < 4) {
                    pieza = String.valueOf(ORDEN_PIEZAS[i][cont]);
                } else {
                    pieza = String.valueOf(ORDEN_PIEZAS[1 - (i - 6)][cont]).toUpperCase();
                }

                ((Label) gP.getChildren().get(j)).setText(pieza);
            }
        }*/
        
        return gP;
    }
}
