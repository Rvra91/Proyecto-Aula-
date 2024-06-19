package com.mycompany.proyectoaula;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
import Entidades.MensajesAlerta;
import Entidades.VentaE;
import Entidades.CrudCliente;
import Logica.CrudProducto;
import Logica.Venta;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;

import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Ramon
 */
public class VentaController implements Initializable {

    MensajesAlerta mensaje = new MensajesAlerta() {
    };
    String tipo = " ";
    Venta vender = new Venta();
    VentaE venta = new VentaE();

    CrudCliente cliente = new CrudCliente();
    CrudProducto producto = new CrudProducto();
    @FXML
    private CheckBox checkcliente;
    @FXML
    private TextField buscaproducto;
    @FXML
    private TextField buscarcliente;
    @FXML
    private TableView<Object[]> tablacliente;
    @FXML
    private TextField txtcantidad;
    @FXML
    private TextField txtcosto;
    @FXML
    private TextField txtdinero;
    @FXML
    private TextField txtcambio;
    @FXML
    private Button btnvender;
    @FXML
    private Button transferencia;
    @FXML
    private Button efectivo;
    @FXML
    private Button tarjeta;
    @FXML
    private TableView<Object[]> tablaproductos;
    @FXML
    private Label lblcliente;
    @FXML
    private ComboBox<String> boxcategoria;
    @FXML
    private Label lbltipo;
    @FXML
    private TextField txtid;
    @FXML
    private ImageView btnbuscar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtcosto.setDisable(true);
        txtcambio.setDisable(true);
        cliente.cargarClientes(tablacliente, lblcliente, lblcliente);
        producto.cargarCombo(boxcategoria);
    }

    @FXML
    private void vender(ActionEvent event) throws SQLException {
        if (txtdinero.getText().isEmpty()
                || txtcantidad.getText().isEmpty()
                || lbltipo.getText().isEmpty() || lblcliente.getText().isEmpty() || txtid.getText().isEmpty()) {
            mensaje.errorAlert("REVISE QUE LOS DATOS ESTEN CORRECTOS");
        } else {

            double dinero = Double.parseDouble(txtdinero.getText());
            double costo = Double.parseDouble(txtcosto.getText());
            int cantidad = Integer.parseInt(txtcantidad.getText());

            costo = costo * cantidad;
            int idproducto = Integer.parseInt(txtid.getText());
            String clienteTexto = lblcliente.getText();
            int idCliente = Integer.parseInt(clienteTexto);
            // Comparamos los valores numéricos
            if (dinero >= costo) {
                venta.setCant_vendida(cantidad);
                venta.setId_producto(idproducto);
                venta.setId_cliente(idCliente);

                // Acción cuando el dinero es suficiente para cubrir el costo
                vender.realizarVenta(venta);

                txtdinero.clear();
                txtcantidad.clear();
                mensaje.infoAlert("VENTA REALIZADA");
            } else {

                mensaje.infoAlert("Dinero Insuficiente");
            }

        }

    }

    @FXML
    private void cambio(ActionEvent event) {
        double dinero = Double.parseDouble(txtdinero.getText());
        double costo = Double.parseDouble(txtcosto.getText());
        vender.calcularCambio(dinero, costo, txtcambio);     //   String textoCambio = String.valueOf(cambio);
        //  txtcambio.setText(textoCambio);
    }

    @FXML
    private void cargarProductos(ActionEvent event) {

        String nombreCategoria = boxcategoria.getValue();
        producto.cargarTabla(tablaproductos, lblcliente, 0, nombreCategoria, txtcosto, txtid);

    }

    @FXML
    private void cambio(InputMethodEvent event) {
        double dinero = Double.parseDouble(txtdinero.getText());
        double costo = Double.parseDouble(txtcosto.getText());

        vender.calcularCambio(dinero, costo, txtcambio);
        //String textoCambio = String.valueOf(cambio);
        //txtcambio.setText(textoCambio);
    }

    @FXML
    private void tomar(ActionEvent event) {
        if (event.getSource() == transferencia) {
            lbltipo.setText("Transferencia");
            tipo = "Transferencia";
            venta.setTipo(tipo);

        } else if (event.getSource() == efectivo) {
            lbltipo.setText("efectivo");
            tipo = "Efectivo";
            venta.setTipo(tipo);
        }
        if (event.getSource() == tarjeta) {
            lbltipo.setText("tarjeta");
            tipo = "Tarjeta";
            venta.setTipo(tipo);
            System.out.println("aaaa");
        }
    }

    @FXML
    private void tomar(MouseEvent event) {
        if (event.getSource() == transferencia) {
            lbltipo.setText("Transferencia");
            tipo = "Transferencia";
            venta.setTipo(tipo);

        } else if (event.getSource() == efectivo) {
            lbltipo.setText("efectivo");
            tipo = "Efectivo";
            venta.setTipo(tipo);
        }
        if (event.getSource() == tarjeta) {
            lbltipo.setText("tarjeta");
            tipo = "Tarjeta";
            venta.setTipo(tipo);
            System.out.println("aaaa");
        }
    }

    @FXML
    private void buscar(MouseEvent event) {
        if (event.getSource() == btnbuscar) {

            producto.buscar(tablaproductos, buscaproducto, lbltipo);
        } else if (event.getSource() == btnbuscar) {

            producto.buscar(tablacliente, buscarcliente, lbltipo);

        }

    }

    @FXML
    private void buscar(ActionEvent event) {
    }

}
