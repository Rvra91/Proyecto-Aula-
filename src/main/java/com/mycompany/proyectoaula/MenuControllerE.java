package com.mycompany.proyectoaula;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */


import animatefx.animation.ZoomIn;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Ramon
 */
public class MenuControllerE implements Initializable {

    @FXML
    private HBox rootPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void cargarClientes() throws IOException {

        App.loadSubView(rootPane, "Cliente");
    }

    private void abrirCategoria(MouseEvent event) {
        App.loadSubView(rootPane, "Categoria");
    }

    private void abrirAjustes(MouseEvent event) throws IOException {
        App.setStage("Ajustes", true);

    }

    private void abrirProductos(MouseEvent event) {
        App.loadSubView(rootPane, "Producto");
    }

    @FXML
    private void abrirVentas(MouseEvent event) {
        App.loadSubView(rootPane, "Venta");
    }

    @FXML
    private void abrirReportes(MouseEvent event) {
    }
}
