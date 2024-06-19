/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import Conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author Ramon
 */
public class Reportes {
       public void cargarTabla(TableView<Object[]> tabla, Label labelPrecio, int idCategoria, String nombreCategoria, TextField uno, TextField dos) {
        Connection cn = Conexion.conectar();

        // Limpiamos las columnas existentes en caso de que se llame varias veces a este método
        tabla.getColumns().clear();

        // Definimos las columnas del TableView
        TableColumn<Object[], String> idColumna = new TableColumn<>("id");
        TableColumn<Object[], String> nombreColumna = new TableColumn<>("idproducto");
        TableColumn<Object[], String> descripcionColumna = new TableColumn<>("categoria");
        TableColumn<Object[], String> stockColumna = new TableColumn<>("cliente");
        TableColumn<Object[], String> gananciaColumna = new TableColumn<>("tipo");
        TableColumn<Object[], String> precioColumna = new TableColumn<>("cantidad"); // Nueva columna para el precio
        TableColumn<Object[], String> dinero = new TableColumn<>("cantidad"); // Nueva columna para el precio
        TableColumn<Object[], String> fecha = new TableColumn<>("cantidad"); // Nueva columna para el precio

        // Asignamos las celdas a las columnas
        idColumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0].toString()));
        nombreColumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1].toString()));
        descripcionColumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2].toString()));
        stockColumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[3].toString()));
        gananciaColumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[4].toString()));
        precioColumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[5].toString())); // Asignación de celda para el precio
        dinero.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[6].toString())); // Asignación de celda para el precio
        fecha.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[7].toString())); // Asignación de celda para el precio

        // Establecemos tamaños diferentes para cada columna
        idColumna.setPrefWidth(50);
        idColumna.setMinWidth(50);
        
        nombreColumna.setPrefWidth(200);
        nombreColumna.setMinWidth(150);
        
        descripcionColumna.setPrefWidth(120);
        descripcionColumna.setMinWidth(100);
        
        stockColumna.setPrefWidth(120);
        stockColumna.setMinWidth(100);
        
        gananciaColumna.setPrefWidth(120);
        gananciaColumna.setMinWidth(100);
        
        precioColumna.setPrefWidth(120); // Tamaño preferido para la nueva columna de precio
        precioColumna.setMinWidth(100);  // Tamaño mínimo para la nueva columna de precio

        // Agregamos las columnas al TableView
        tabla.getColumns().addAll(idColumna, nombreColumna, descripcionColumna, stockColumna, gananciaColumna, precioColumna);

        // Creamos un ArrayList para almacenar los datos
        ArrayList<Object[]> datos = new ArrayList<>();
        
      
        
        try {
            // Incluimos la columna 'precio' en la consulta SQL
            PreparedStatement consulta = cn.prepareStatement("SELECT id, nombre, descripcion, stock, ganancia, precio FROM producto WHERE id_categoria = ?");
            consulta.setInt(1, idCategoria);
            
            ResultSet rs = consulta.executeQuery();

            // Iteramos sobre el resultado de la consulta
            while (rs.next()) {
                // Creamos un array para almacenar los datos de cada fila
                Object[] fila = new Object[6]; // Ahora el array tiene 6 elementos

                // Guardamos los datos en el array
                fila[0] = rs.getString("id");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("descripcion");
                fila[3] = rs.getString("stock");
                fila[4] = rs.getString("ganancia");
                fila[5] = rs.getString("precio"); // Agregamos el precio

                // Agregamos el array a la lista de datos
                datos.add(fila);
            }

            // Limpiamos los items actuales del TableView
            tabla.getItems().clear();

            // Cargamos los datos actualizados en el TableView
            tabla.getItems().addAll(datos);
            
        } catch (SQLException e) {
            System.out.println("Error al actualizar productos: " + e.getMessage());
        }

        // Manejar el evento de selección de filas
        tabla.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Obtener el precio de la fila seleccionada y mostrarlo en el Label
                Object[] filaSeleccionada = newSelection;
                String precio = filaSeleccionada[5].toString(); // El precio está en la sexta posición del array
                String id = filaSeleccionada[0].toString(); // El precio está en la sexta posición del array
                dos.setText(id);
                uno.setText(precio);
            }
        });
    }
    
}
