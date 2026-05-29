package UD6.Recu.Corregido;

import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Ignacio Martínez Rodríguez
 */
public class AppInventarioFX extends Application{

    static ListView<Producto> listP;
    static Label lblErrorList;
    static Label lblAdd;
    static TextField txtFind;
    static TextField txtCod;
    static TextField txtNom;
    static TextField txtCant;
    static TextField txtPrecio;

    @Override
    public void start(Stage primaryStage) throws Exception {
        txtCod = new TextField();
        txtCod.setPromptText("Número entero");
        HBox.setMargin(txtCod, new Insets(0, 0, 0, 5));

        HBox boxCod = new HBox(new Label("Código:"), txtCod);
        boxCod.setPadding(new Insets(5));

        txtNom = new TextField();
        txtNom.setPromptText("Nombre del Producto");
        HBox.setMargin(txtNom, new Insets(0, 0, 0, 5));

        HBox boxNom = new HBox(new Label("Nombre:"), txtNom);
        boxNom.setPadding(new Insets(5));

        txtCant = new TextField();
        txtCant.setPromptText("Número entero");
        HBox.setMargin(txtCant, new Insets(0, 0, 0, 5));

        HBox boxCant = new HBox(new Label("Cantidad:"), txtCant);
        boxCant.setPadding(new Insets(5));

        txtPrecio = new TextField();
        txtPrecio.setPromptText("Número decimal");
        HBox.setMargin(txtPrecio, new Insets(0, 0, 0, 5));

        HBox boxPrecio = new HBox(new Label("Precio:"), txtPrecio);
        boxPrecio.setPadding(new Insets(5));
        
        Button btnAddProd = new Button("Añadir");
        btnAddProd.setOnAction(e -> addProd());

        Button back = new Button("Volver");
        VBox.setMargin(back, new Insets(20, 0, 0, 50));

        lblAdd = new Label();
        lblAdd.setVisible(false);

        VBox addBox = new VBox(btnAddProd, boxCod, boxNom, boxCant, boxPrecio, lblAdd, back);

        Scene addScene = new Scene(addBox);
        Stage addStage = new Stage();
        addStage.setScene(addScene);
        
        back.setOnAction(e -> {
            primaryStage.show();
            addStage.hide();
            lblAdd.setVisible(false);
        });
        
        Button btnGoAdd = new Button("Añadir nuevo producto");
        VBox.setMargin(btnGoAdd, new Insets(5));
        btnGoAdd.setOnAction(e -> {
            listP.setVisible(false);
            addStage.show();
            primaryStage.hide();
        });
        
        Button btnShowList = new Button("Mostrar productos");
        btnShowList.setOnAction(e -> mostrarProductos());
        VBox.setMargin(btnShowList, new Insets(5));


        Button btnFind = new Button("Buscar código producto");
        btnFind.setOnAction(e -> {
            mostrarProductosBusqueda();
        });

        txtFind = new TextField();
        HBox.setMargin(txtFind, new Insets(0, 0, 0, 5));
        txtFind.setPromptText("Código (cod) o (min, max)");
        txtFind.setMinWidth(160);

        HBox boxFind = new HBox(btnFind, txtFind);
        boxFind.setPadding(new Insets(5));


        lblErrorList = new Label();
        lblErrorList.setVisible(false);

        listP =  new ListView<>();
        listP.setVisible(false);
        

        VBox root = new VBox(btnGoAdd, btnShowList, boxFind, lblErrorList, listP);
        root.minWidth(300);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addProd() {
        int cod;
        String nom;
        int cant;
        double precio;
        Producto p;

        lblAdd.setText("");
        lblAdd.setVisible(true);

        try {
            cod = Integer.valueOf(txtCod.getText().strip());
        } catch (Exception e) {
            lblAdd.setText("Error: Código inválido");
            txtCod.clear();
            return;
        }

        nom = txtNom.getText();
        if (nom == null || nom.isBlank()) {
            lblAdd.setText("Error: El nombre no puede estar en blanco");
            txtNom.clear();
            return;
        }

        try {
            cant = Integer.valueOf(txtCant.getText().strip());
        } catch (Exception e) {
            lblAdd.setText("Error: Cantidad inválida");
            txtCant.clear();
            return;
        }

        try {
            precio = Double.valueOf(txtPrecio.getText().strip());
        } catch (Exception e) {
            lblAdd.setText("Error: Precio inválido");
            return;
        }

        try {
            p = new Producto(cod, nom, cant, precio);
        } catch (Exception e) {
            lblAdd.setText("Error: " + e.getMessage());
            return;
        }
        try {
            AppInventario.addProducto(p, AppInventario.rutaInventario);
            lblAdd.setText("Producto añadido correctamente");
            txtCod.clear();
            txtNom.clear();
            txtCant.clear();
            txtPrecio.clear();
        } catch (Exception e) {
            lblAdd.setText("Error: " + e.getMessage());
        }
    }

    private void mostrarProductosBusqueda() {
        listP.getItems().clear();
        Integer codMin = null;
        Integer codMax = null;
        
        try {
            String[] codigos = txtFind.getText().split(",");
            if (codigos.length > 2) {
                lblErrorList.setText("Error: Escriba unicamente uno o dos códigos");
                lblErrorList.setVisible(true);
                listP.setVisible(false);
                return;
            } else if (codigos.length == 1) {
                codMin = Integer.valueOf(codigos[0].strip());
            } else {
                codMin = Integer.valueOf(codigos[0].strip());
                codMax = Integer.valueOf(codigos[1].strip());
            }
        } catch (Exception e) {
            lblErrorList.setText("Error: Código o códigos con formato numérico invalido");
            codMin = null;
            lblErrorList.setVisible(true);
            listP.setVisible(false);
            return;
        }
        
        List<Producto> encontrados = AppInventario.buscarProducto(AppInventario.rutaInventario, codMin, codMax);

        if (encontrados != null && !encontrados.isEmpty()) {
            listP.getItems().addAll(encontrados);
            listP.setVisible(true);
            lblErrorList.setVisible(false);
        } else {
            lblErrorList.setText("No se encontró ningún producto con ese código o rango");
            lblErrorList.setVisible(true);
            listP.setVisible(false);
        }
    }
    public static void mostrarProductos(){
        listP.getItems().clear();
        List<Producto> l = AppInventario.leerBin(AppInventario.rutaInventario);
        if (l != null) {
            listP.getItems().addAll(l);
            listP.setVisible(true);
            lblErrorList.setVisible(false);
        } else {
            lblErrorList.setText("No hay productos guardados en el inventario");
            lblErrorList.setVisible(true);
            listP.setVisible(false);
        }
    }

     public static void main(String[] args) {
        launch(args);
     }
}
