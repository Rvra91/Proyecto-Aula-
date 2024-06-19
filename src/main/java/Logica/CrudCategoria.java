/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import Conexion.Conexion;
import Entidades.Categoria;
import Entidades.Crud;
import Entidades.Cruds;
import Entidades.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author Ramon
 */
public class CrudCategoria extends Crud<Categoria> {

    @Override
    public boolean guardar(Categoria objeto, ComboBox<String> combo) {
        boolean respuesta = false;

        String sql = "INSERT INTO categoria (nombre, descripcion, stock) VALUES (?, ?, ?)";

        try (Connection cn = Conexion.conectar(); PreparedStatement consulta = cn.prepareStatement(sql)) {

            // Establecer los valores para la inserción
            consulta.setString(1, objeto.getNombre());
            consulta.setString(2, objeto.getDescripcion());
            consulta.setInt(3, objeto.getCantidad());

            // Manejo de la transacción
            cn.setAutoCommit(false); // Iniciar la transacción manualmente

            // Ejecutar la consulta de inserción
            int filasAfectadas = consulta.executeUpdate();

            if (filasAfectadas > 0) {
                cn.commit(); // Confirmar la transacción si la inserción es exitosa
                respuesta = true;
            } else {
                cn.rollback(); // Revertir la transacción si la inserción falla
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Registrar el error para la depuración
            // Manejar la excepción y revertir la transacción si ocurre un error
            try (Connection cn = Conexion.conectar()) {
                if (cn != null) {
                    cn.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }

        } finally {
            try (Connection cn = Conexion.conectar()) {
                if (cn != null) {
                    cn.setAutoCommit(true); // Restaurar el estado automático de commit
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return respuesta;
    }

    @Override
    public void cargarTabla(TableView<Object[]> tabla, Label gananciaLabel) {
        Connection cn = Conexion.conectar();

        // Limpiar las columnas existentes en caso de que se llame varias veces a este método
        tabla.getColumns().clear();

        // Definir las columnas del TableView para los datos
        TableColumn<Object[], String> idColumna = new TableColumn<>("id");
        TableColumn<Object[], String> nombreColumna = new TableColumn<>("nombre");
        TableColumn<Object[], String> descripcionColumna = new TableColumn<>("descripcion");
        TableColumn<Object[], String> stockColumna = new TableColumn<>("stock");

        // Asignar las celdas a las columnas para los datos
        idColumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0].toString()));
        nombreColumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1].toString()));
        descripcionColumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2].toString()));
        stockColumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[3].toString()));

        // Establecer tamaños diferentes para cada columna de datos
        idColumna.setPrefWidth(30); // Ancho preferido
        idColumna.setMinWidth(30); // Ancho mínimo
        nombreColumna.setPrefWidth(50); // Ancho preferido
        nombreColumna.setMinWidth(50); // Ancho mínimo
        descripcionColumna.setPrefWidth(100); // Ancho preferido
        descripcionColumna.setMinWidth(100); // Ancho mínimo
        stockColumna.setPrefWidth(30); // Ancho preferido
        stockColumna.setMinWidth(30); // Ancho mínimo

        // Configurar columnas de botones
        TableColumn<Object[], Void> editarColumna = new TableColumn<>("Editar");
        TableColumn<Object[], Void> eliminarColumna = new TableColumn<>("Eliminar");

        editarColumna.setCellFactory(param -> new TableCell<Object[], Void>() {
            private final Button btnEditar = new Button("Editar");

            {
                btnEditar.getStyleClass().add("shadow");

                btnEditar.setStyle("-fx-background-color: #FFFFFF;"
                        + "-fx-background-radius: 100;");
                btnEditar.setOnAction(event -> {
                    // Obtener el ID del producto de la fila seleccionada
                    Object[] fila = getTableView().getItems().get(getIndex());
                    String idProducto = fila[0].toString();

                    // Lógica para editar el producto según el ID
                    editar(idProducto);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnEditar);
                }
            }
        });

        eliminarColumna.setCellFactory(param -> new TableCell<Object[], Void>() {
            private final Button btnEliminar = new Button("Eliminar");

            {
                btnEliminar.getStyleClass().add("shadow");
                btnEliminar.setStyle("-fx-background-color: #FFFFFF; "
                        + "-fx-text-fill: black; "
                        + "-fx-background-radius: 100;");
                btnEliminar.setOnAction(event -> {
                    // Obtener el ID del producto de la fila seleccionada
                    Object[] fila = getTableView().getItems().get(getIndex());
                    String idProducto = fila[0].toString();

                    // Lógica para eliminar el producto según el ID
                    eliminar(idProducto);

                    // Remover el elemento eliminado de la tabla
                    getTableView().getItems().remove(getIndex());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnEliminar);
                }
            }
        });

        // Agregar todas las columnas al TableView
        tabla.getColumns().addAll(idColumna, nombreColumna, descripcionColumna, stockColumna, editarColumna, eliminarColumna);

        // Crear un ArrayList para almacenar los datos
        ArrayList<Object[]> datos = new ArrayList<>();

        try {
            PreparedStatement consulta = cn.prepareStatement("SELECT id, nombre, descripcion, stock FROM categoria");
            ResultSet rs = consulta.executeQuery();

            // Iterar sobre el resultado de la consulta
            while (rs.next()) {
                // Crear un array para almacenar los datos de cada fila
                Object[] fila = new Object[4]; // Ajustar según la cantidad de columnas en la consulta

                // Guardar los datos en el array
                fila[0] = rs.getString("id");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("descripcion");
                fila[3] = rs.getString("stock");

                // Agregar el array a la lista de datos
                datos.add(fila);
            }

            // Limpiar los items actuales del TableView
            tabla.getItems().clear();

            // Cargar los datos actualizados en el TableView
            tabla.getItems().addAll(datos);

        } catch (SQLException e) {
            System.out.println("Error al actualizar productos: " + e.getMessage());
        }
    }

    @Override
    public void cargarCombo(ComboBox<String> combo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean eliminar(String id) {

        boolean respuesta = false;
        Connection cn = Conexion.conectar();
        PreparedStatement obtener = null;
        PreparedStatement consulta = null;
        ResultSet rs = null;

        try {

            // Consulta para insertar el producto
            consulta = cn.prepareStatement("DELETE FROM categoria where id=?");
            consulta.setString(1, id);

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

  public void pasarCategoria(TableView<?> tabla, ComboBox<String> comboBox, int columnaSeleccionada) {
    // Obtener el elemento seleccionado en la tabla
    Object itemSeleccionado = tabla.getSelectionModel().getSelectedItem();

    if (itemSeleccionado instanceof Object[]) {
        Object[] fila = (Object[]) itemSeleccionado;

        // Verificar si la columna seleccionada está dentro de los límites
        if (columnaSeleccionada >= 1 && columnaSeleccionada < fila.length && fila[columnaSeleccionada] != null) {
            String valor = fila[columnaSeleccionada].toString();

            // Establecer el valor en el ComboBox
            comboBox.setValue(valor); // o comboBox.getSelectionModel().select(valor);
        }
    }
}

}
