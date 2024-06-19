package com.mycompany.proyectoaula;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */


import Entidades.Categoria;
import Entidades.Categoria;
import Entidades.MensajesAlerta;
import Entidades.Producto;
import Logica.CrudCategoria;
import Logica.CrudCategoria;
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
public class CategoriaController implements Initializable {

    CrudCategoria CCategoria = new CrudCategoria();
    Categoria categoria = new Categoria();
    CrudProducto CProducto = new CrudProducto();
    Producto produtco = new Producto();
    MensajesAlerta mensaje = new MensajesAlerta() {
    };
    @FXML
    private TextField buscar;
    @FXML
    private ImageView btnbuscar;
    @FXML
    private Button btninfo;
    @FXML
    private ImageView info;
    @FXML
    private Button btnañadir;
    @FXML
    private ImageView info2;
    @FXML
    private TableView<Object[]> tablaproductos;
    @FXML
    private TableView<Object[]> tablacategoria;
    @FXML
    private Label lblcategoria;
    @FXML
    private Label lblganancia;
    @FXML
    private ComboBox<String> noexiste;
    @FXML
    private TextField txtnombre;
    @FXML
    private TextField txtdescripcion;
    @FXML
    private Label lblinfo;
    @FXML
    private TextField a;
    @FXML
    private TextField e;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CCategoria.cargarTabla(tablacategoria, lblcategoria);
        Label seleccionLabel = new Label();

        // Agregar un listener para manejar el evento de selección
    }

    @FXML
    private void info(MouseEvent event) throws IOException {
        if (event.getSource() == info) {
            mensaje.infoAlert("ESTO SIRVE PARA HACER REPORTE DE LA CATEGORIA");

        } else if (event.getSource() == info2) {
            mensaje.infoAlert("ESTO SIRVE PARA AÑADIR UNA CATEGORIA NUEVA");

        }

    }

    @FXML
    private void reporte(ActionEvent event) {
    }

    @FXML
    private void añadir(ActionEvent event) {

        if (txtnombre.getText().isEmpty()) {
            lblinfo.setText("LLENE LOS DATOS CORRECTAMENTE");
        } else {

            // Configurar los datos del cliente
            categoria.setNombre(txtnombre.getText());
            categoria.setDescripcion(txtdescripcion.getText());

            // Asignar el valor convertido al producto
            // Aquí podrías configurar más datos del cliente según sea necesario
            // Llamar al método para guardar el cliente
            CCategoria.guardar(categoria, noexiste);

            // Actualizar la tabla de clientes después de guardar
            tablaproductos.getColumns().clear();
            CCategoria.pasarCategoria(tablaproductos, noexiste, 2);

            // Limpiar campos y mensajes después de agregar
            txtnombre.clear();
            txtdescripcion.clear();

            lblinfo.setText("");
            lblinfo.setText("Cliente Añadido Correctamente");
              CCategoria.cargarTabla(tablaproductos, lblganancia);
        }

    }

    @FXML
    private void pasar(MouseEvent event) {

        CCategoria.pasarCategoria(tablacategoria, noexiste, 1);
        String nombre = noexiste.getValue();
        
        CProducto.cargarTabla(tablaproductos, lblinfo, 0, nombre,a,e);
    
    }
}
