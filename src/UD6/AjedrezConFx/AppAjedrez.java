package UD6.AjedrezConFx;

import UD6.AjedrezConFx.Control.ControlTablero;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AppAjedrez  extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane gP = ControlTablero.inizializarTablero();
        
        Scene scene = new Scene(gP);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
