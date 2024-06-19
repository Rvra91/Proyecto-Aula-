package com.mycompany.proyectoaula;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */


import Entidades.Cruds;
import Entidades.MensajesAlerta;
import Entidades.Producto;
import Logica.CrudProducto;
import Logica.CrudProducto;
import Entidades.MensajesAlerta;
import Entidades.Producto;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Ramon
 */
public class ProductoController implements Initializable {

    MensajesAlerta mensaje = new MensajesAlerta() {
    };
    CrudProducto producto = new CrudProducto();
    Producto producto2 = new Producto();

    @FXML
    private ImageView btnbuscar;
    @FXML
    private TextField txtnombre;
    @FXML
    private TextField txtdescripcion;
    @FXML
    private TextField txtprecio;
    @FXML
    private Button btnañadir;
    @FXML
    private ImageView info2;
    @FXML
    private Button btninfo;
    @FXML
    private ImageView info;
    @FXML
    private TableView<Object[]> tablaproductos;
    @FXML
    private ComboBox<String> boxcategoria;
    @FXML
    private Label lblinfo;
    @FXML
    private TextField txtcantidad;
    @FXML
    private TextField txtbuscar;
    @FXML
    private Label lblproducto;
    @FXML
    private TextField a;
    @FXML
    private TextField e;

    public void initialize(URL url, ResourceBundle rb) {
        producto.cargarCombo(boxcategoria);
        // producto.cargarTabla(tablaproductos, lblproducto);
        txtbuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            producto.buscar(tablaproductos, txtbuscar, lblproducto);
        });
    }

    @FXML
    private void añadirProducto(ActionEvent event) {

        if (txtnombre.getText().isEmpty()
                || txtdescripcion.getText().isEmpty()
                || txtprecio.getText().isEmpty()
                || "Seleccione".equals(boxcategoria.getSelectionModel().getSelectedItem())) {
            lblinfo.setText("LLENE LOS DATOS CORRECTAMENTE");
        } else {

            // Configurar los datos del cliente
            producto2.setNombre(txtnombre.getText());
            producto2.setDescripcion(txtdescripcion.getText());
            double precio = Double.parseDouble(txtprecio.getText());
            int stock = Integer.parseInt(txtcantidad.getText());

            producto2.setStock(stock);

            // Asignar el valor convertido al producto
            producto2.setPrecio(precio);

            // Aquí podrías configurar más datos del cliente según sea necesario
            // Llamar al método para guardar el cliente
            producto.guardar(producto2, boxcategoria);

            // Actualizar la tabla de clientes después de guardar
            tablaproductos.getColumns().clear();
            String nombre = boxcategoria.getValue();
            producto.cargarTabla(tablaproductos, lblinfo, 0, nombre,a,e);
            // Limpiar campos y mensajes después de agregar
            txtnombre.clear();
            txtdescripcion.clear();
            txtprecio.clear();
            txtcantidad.clear();
            lblinfo.setText("");
            lblinfo.setText("Cliente Añadido Correctamente");
        }

    }

    @FXML
    private void infoProducto(ActionEvent event) {
    }

    @FXML
    private void buscar(MouseEvent event) {

        producto.buscar(tablaproductos, txtbuscar, lblproducto);
    }

    @FXML
    private void info(MouseEvent event) throws IOException {
        if (event.getSource() == info) {
            mensaje.infoAlert("ESTO SIRVE PARA HACER REPORTE DE PRODUCTO");

        } else if (event.getSource() == info2) {
            mensaje.infoAlert("ESTO SIRVE PARA AÑADIR UN NUEVO PRODUCTO");

        }

    }

    @FXML
    private void cargarTabla(ActionEvent event) {
        String nombre = boxcategoria.getValue();
        producto.cargarTabla(tablaproductos, lblinfo, 0, nombre,e,a);

    }

}
