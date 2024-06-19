/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import Conexion.Conexion;
import Entidades.Crud;
import Entidades.Cruds;
import Entidades.MensajesAlerta;
import Entidades.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author Ramon
 */
public class CrudProducto extends Crud<Producto> {
    
    @Override
    public boolean guardar(Producto objeto, ComboBox<String> coHYU776mbo) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();
        PreparedStatement obtener = null;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        
        try {
            // Consulta para obtener el ID de la categoría
            obtener = cn.prepareStatement("SELECT id FROM categoria WHERE nombre=?");
            obtener.setString(1, combo.getValue());
            rs = obtener.executeQuery();
            
            if (rs.next()) {
                int idCat = rs.getInt("id");
                objeto.setIdCategoria(idCat);
            } else {
                System.err.println("No se encontró la categoría");
                return false;
            }

            // Consulta para insertar el producto
            consulta = cn.prepareStatement("INSERT INTO producto (nombre, descripcion, precio, stock, id_categoria) VALUES (?, ?, ?, ?, ?)");
            consulta.setString(1, objeto.getNombre());
            consulta.setString(2, objeto.getDescripcion());
            consulta.setDouble(3, objeto.getPrecio());
            consulta.setInt(4, objeto.getStock());
            consulta.setInt(5, objeto.getIdCategoria());

            // Manejo de la transacción
            cn.setAutoCommit(false); // Iniciar una transacción explícitamente

            if (consulta.executeUpdate() > 0) {
                cn.commit(); // Confirmar la transacción si la inserción es exitosa
                respuesta = true;
            } else {
                cn.rollback(); // Revertir la transacción si falla la inserción
            }

            // Restaurar el estado automático de commit
            cn.setAutoCommit(true);
            
        } catch (SQLException e) {
            // Manejar el error y revertir la transacción en caso de excepción
            try {
                if (cn != null) {
                    cn.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            // Mostrar una alerta al usuario y registrar el error para la depuración
            //   showAlert.errorAlert("Error al guardar producto: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cerrar recursos en el bloque finally para asegurar que se liberen
            try {
                if (rs != null) {
                    rs.close();
                }
                if (obtener != null) {
                    obtener.close();
                }
                if (consulta != null) {
                    consulta.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return respuesta;
    }
    
    int id;
    
    public void cargarTabla(TableView<Object[]> tabla, Label labelPrecio, int idCategoria, String nombreCategoria, TextField uno, TextField dos) {
        Connection cn = Conexion.conectar();

        // Limpiamos las columnas existentes en caso de que se llame varias veces a este método
        tabla.getColumns().clear();

        // Definimos las columnas del TableView
        TableColumn<Object[], String> idColumna = new TableColumn<>("id");
        TableColumn<Object[], String> nombreColumna = new TableColumn<>("nombre");
        TableColumn<Object[], String> descripcionColumna = new TableColumn<>("descripcion");
        TableColumn<Object[], String> stockColumna = new TableColumn<>("stock");
        TableColumn<Object[], String> gananciaColumna = new TableColumn<>("ganancia");
        TableColumn<Object[], String> precioColumna = new TableColumn<>("precio"); // Nueva columna para el precio

        // Asignamos las celdas a las columnas
        idColumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0].toString()));
        nombreColumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1].toString()));
        descripcionColumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2].toString()));
        stockColumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[3].toString()));
        gananciaColumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[4].toString()));
        precioColumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[5].toString())); // Asignación de celda para el precio

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
            // Consulta para obtener el ID de la categoría por su nombre
            PreparedStatement consultaIdCategoria = cn.prepareStatement("SELECT id FROM categoria WHERE nombre = ?");
            consultaIdCategoria.setString(1, nombreCategoria);
            
            ResultSet rsIdCategoria = consultaIdCategoria.executeQuery();
            
            if (rsIdCategoria.next()) {
                idCategoria = rsIdCategoria.getInt("id");
            } else {
                System.out.println("No se encontró la categoría con nombre: " + nombreCategoria);
            }
            
            rsIdCategoria.close(); // Cerrar ResultSet
            consultaIdCategoria.close(); // Cerrar PreparedStatement

        } catch (SQLException e) {
            System.out.println("Error al obtener ID de categoría: " + e.getMessage());
        }
        
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
    
    @Override
    public void cargarCombo(ComboBox<String> combo) {

        // Limpiamos el ComboBox y establecemos un valor por defecto
        combo.getItems().clear();
        combo.setValue("Seleccione");
        
        Connection cn = null;
        PreparedStatement consulta = null;
        ResultSet resultado = null;
        
        try {
            // Conectar a la base de datos
            cn = Conexion.conectar();

            // Consulta para obtener los nombres de las categorías
            consulta = cn.prepareStatement("SELECT nombre FROM categoria");

            // Ejecutamos la consulta y obtenemos los resultados
            resultado = consulta.executeQuery();

            // Recorremos los resultados y los agregamos al ComboBox
            while (resultado.next()) {
                String nombreCategoria = resultado.getString("nombre");
                combo.getItems().add(nombreCategoria);
            }
            
        } catch (SQLException e) {
            // Mostrar una alerta en caso de error
            System.out.println("Error al cargar categorías: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cerramos los recursos en el bloque finally
            try {
                if (resultado != null) {
                    resultado.close();
                }
                if (consulta != null) {
                    consulta.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public boolean eliminar(String objeto) {
        return super.eliminar(objeto); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
}
