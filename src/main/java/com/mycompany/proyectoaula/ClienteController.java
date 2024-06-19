package com.mycompany.proyectoaula;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */


import Entidades.Cliente;
import Entidades.CrudCliente;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Ramon
 */
public class ClienteController implements Initializable {

    //SE INCIALIZA EL OBJETO
    CrudCliente cliente = new CrudCliente();
    Cliente clientes = new Cliente();

    @FXML
    private TextField txtnombre;
    @FXML
    private TextField txtcedula;
    @FXML
    private ComboBox<String> boxsexo;
    @FXML
    private Button btnagregar;
    @FXML
    private Button btneliminar;
    @FXML
    private TableView<Object[]> tblcliente;
    @FXML
    private Label lblinfo;
    @FXML
    private Button btnreporte;
    @FXML
    private Label lblid;
    @FXML
    private Label lblnombre;

    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {

//INICIALIZAMOS LA TABLA CON LOS CLIENTES
        cliente.cargarClientes(tblcliente,lblid,lblnombre);
        cliente.CargarSexo(boxsexo);
    }

    @FXML
    public void Agregar() throws IOException {

        if (txtnombre.getText().isEmpty()
                || txtcedula.getText().isEmpty()
                || !boxsexo.getValue().equals(boxsexo.getItems().get(0))
                && !boxsexo.getValue().equals(boxsexo.getItems().get(1))
                && !boxsexo.getValue().equals(boxsexo.getItems().get(2))) {
            lblinfo.setText("LLENE LOS DATOS CORRECTAMENTE");
        } else {
            clientes.setNombre(null);
            clientes.setCedula(null);
            // Configurar los datos del cliente
            clientes.setNombre(txtnombre.getText());
            clientes.setCedula(txtcedula.getText());
            // Aquí podrías configurar más datos del cliente según sea necesario

            // Llamar al método para guardar el cliente
            cliente.GuardarCliente(clientes, boxsexo);

            // Actualizar la tabla de clientes después de guardar
            tblcliente.getColumns().clear();
            cliente.cargarClientes(tblcliente,lblid,lblnombre);

            // Limpiar campos y mensajes después de agregar
            txtnombre.clear();
            txtcedula.clear();
            lblinfo.setText("");
            lblinfo.setText("Cliente Añadido Correctamente");
        }
    }

    @FXML
    private void generarReporte(ActionEvent event) {
    }

}
