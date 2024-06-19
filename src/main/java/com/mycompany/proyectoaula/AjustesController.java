package com.mycompany.proyectoaula;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */


import animatefx.animation.ZoomIn;
import animatefx.animation.ZoomOut;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Ramon
 */
public class AjustesController implements Initializable {

    @FXML
    private Pane pnusuarios;
    @FXML
    private TextField txtusuario;
    @FXML
    private TextField txtpassword;
    @FXML
    private Button btnempleado;
    @FXML
    private Button btnadmin;
    @FXML
    private TableView<?> tablaventas;
    @FXML
    private TableView<?> tablausuarios;
    @FXML
    private HBox btnusuario;
    @FXML
    private Pane pntienda;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void mostrarUsuarios(MouseEvent event) {

        new ZoomOut(pntienda).play();

        new ZoomIn(pnusuarios).play();
        pnusuarios.toFront();
    }

    @FXML
    private void mostrarTienda(MouseEvent event) {
        new ZoomOut(pnusuarios).play();

        new ZoomIn(pntienda).play();
        pntienda.toFront();
    }

}
