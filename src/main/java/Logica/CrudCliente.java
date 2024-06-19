package Entidades;

import Conexion.Conexion;
import static Conexion.Conexion.conectar;
import Entidades.Cliente;

import Entidades.MensajesAlerta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CrudCliente {

    //OBJETO PARA MOSTRAR LAS ALERTAS
    MensajesAlerta showAlert = new MensajesAlerta() {};
    //SE USARAN OBJETOS DE LA CLASE USUARIO

    public boolean GuardarCliente(Cliente objeto, ComboBox<String> comboSexo) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();

        try {
            // Consulta para obtener el ID del sexo
            PreparedStatement obtener = cn.prepareStatement("SELECT id FROM sexos WHERE sexo=?");
            obtener.setString(1, comboSexo.getValue());
            ResultSet rs = obtener.executeQuery();

            int idSexo = -1;
            if (rs.next()) {
                idSexo = rs.getInt("id");
                objeto.setSexo(idSexo);
            } else {
                System.err.println("No se encontró el sexo en la tabla 'sexos'");
                return false;
            }

            // Consulta para insertar el cliente
            PreparedStatement consulta = cn.prepareStatement("INSERT INTO clientes (nombre, cedula, sexo) VALUES (?, ?, ?)");
            consulta.setString(1, objeto.getNombre());
            consulta.setString(2, objeto.getCedula());
            consulta.setInt(3, objeto.getSexo());

            // Consulta para actualizar el contador de sexos
            PreparedStatement actualizar = cn.prepareStatement("UPDATE sexos SET Count = Count + 1 WHERE id = ?");
            actualizar.setInt(1, objeto.getSexo());

            // Ejecución de las consultas
            cn.setAutoCommit(false);  // Iniciar una transacción explícitamente
            if (consulta.executeUpdate() > 0) {
                if (actualizar.executeUpdate() > 0) {
                    cn.commit();  // Confirmar la transacción si todo ha ido bien
                    respuesta = true;
                } else {
                    cn.rollback();  // Revertir la transacción si falla la actualización
                }
            } else {
                cn.rollback();  // Revertir la transacción si falla la inserción
            }

            // Cerrar la conexión y revertir al estado automático de commit
            cn.setAutoCommit(true);
            cn.close();

        } catch (SQLException e) {
            showAlert.errorAlert("Error al guardar cliente: " + e.getMessage());
            e.printStackTrace();
        }

        return respuesta;
    }

    
       public void cargarClientes(TableView<Object[]> tabla, Label variable1, Label variable2) {
        Connection cn = Conexion.conectar();

        // Limpiamos las columnas existentes en caso de que se llame varias veces a este método
        tabla.getColumns().clear();

        // Definimos las columnas del TableView
        TableColumn<Object[], String> idColumna = new TableColumn<>("id");
        TableColumn<Object[], String> nombreColumna = new TableColumn<>("nombre");
        TableColumn<Object[], String> cedulaColumna = new TableColumn<>("cedula");
        TableColumn<Object[], String> gananciaColumna = new TableColumn<>("ganancia");

        // Asignamos las celdas a las columnas
        idColumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0].toString()));
        nombreColumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1].toString()));
        cedulaColumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2].toString()));
        gananciaColumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[3].toString()));

        // Establecemos tamaños diferentes para cada columna
        idColumna.setPrefWidth(50); // Ancho preferido
        idColumna.setMinWidth(50); // Ancho mínimo

        nombreColumna.setPrefWidth(200);
        nombreColumna.setMinWidth(150);

        cedulaColumna.setPrefWidth(120);
        cedulaColumna.setMinWidth(100);

        gananciaColumna.setPrefWidth(120);
        gananciaColumna.setMinWidth(100);

        // Agregamos las columnas al TableView
        tabla.getColumns().addAll(idColumna, nombreColumna, cedulaColumna, gananciaColumna);

        // Creamos un ArrayList para almacenar los datos
        ArrayList<Object[]> datos = new ArrayList<>();

        try {
            PreparedStatement consulta = cn.prepareStatement("SELECT * FROM clientes");
            ResultSet rs = consulta.executeQuery();

            // Iteramos sobre el resultado de la consulta
            while (rs.next()) {
                // Creamos un array para almacenar los datos de cada fila
                Object[] fila = new Object[4]; // Suponiendo que hay 4 columnas en la consulta

                // Guardamos los datos en el array
                fila[0] = rs.getString("id");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("cedula");
                fila[3] = rs.getString("ganancia");

                // Agregamos el array a la lista de datos
                datos.add(fila);
            }

            // Limpiamos los items actuales del TableView
            tabla.getItems().clear();

            // Cargamos los datos actualizados en el TableView
            tabla.getItems().addAll(datos);

            // Evento para capturar la selección de una fila
            tabla.setOnMouseClicked(event -> {
                if (!tabla.getSelectionModel().isEmpty()) {
                    Object[] filaSeleccionada = tabla.getSelectionModel().getSelectedItem();

                    // Guardar los valores de la primera y segunda columna en variables
                    if (filaSeleccionada.length > 0) {
                        variable1.setText(filaSeleccionada[0].toString()); // Primera columna
                    }
                    if (filaSeleccionada.length > 1) {
                        variable2.setText(filaSeleccionada[1].toString()); // Segunda columna
                    }
                }
            });

        } catch (SQLException e) {
            showAlert.errorAlert("Error al actualizar clientes: " + e.getMessage());
        }
    }




    public boolean Eliminar(Cliente objeto) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();
        try {
            PreparedStatement consulta = cn.prepareStatement("DELETE FROM cliente WHERE id = ?");

            consulta.setInt(2, objeto.getId());

            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }
            cn.close();
        } catch (SQLException e) {
            showAlert.errorAlert("Error al eliminar el cliente" + e.getMessage());
            e.printStackTrace();

        }
        return respuesta;
    }

    public void CargarSexo(ComboBox<String> comboSexo) {
        // OBTENEMOS LOS DATOS DEL COMBOBOX Y LIMPIAMOS EL COMBOBOX
        comboSexo.getItems().clear();
        comboSexo.setValue("Seleccion Sexo");

        Connection cn = null;
        PreparedStatement consulta = null;
        ResultSet resultado = null;

        try {
            // Conectar a la base de datos
            cn = Conexion.conectar();

            // Preparamos la consulta SQL para obtener los sexos de la tabla "sexos"
            consulta = cn.prepareStatement("SELECT sexo FROM sexos");

            // Ejecutamos la consulta y obtenemos los resultados
            resultado = consulta.executeQuery();

            // Recorremos los resultados y los agregamos al ComboBox
            while (resultado.next()) {
                // Obtenemos el valor del campo "sexo"
                String sexo = resultado.getString("sexo");

                // Agregamos el valor al ComboBox
                comboSexo.getItems().add(sexo);
            }

        } catch (SQLException e) {
            // Mostrar una alerta en caso de error
            showAlert.errorAlert("Error al cargar sexos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cerramos los recursos
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

}
