package UD6.Empresa;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppEmpresas extends Application {
    static List<Empresa> empresas = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Empresas");
        Parent root = FXMLLoader.load(AppEmpresas.class.getResource("EmpresasVista.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        empresas.add(new Empresa(1, "IES Chan do Monte", "http://ieschandomonte.edu.es/ies/"));
        launch();
    }
    
}
