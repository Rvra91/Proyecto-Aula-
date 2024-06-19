package Logica;

import Conexion.Conexion;
import static Conexion.Conexion.conectar;
import Entidades.Crud;
import Entidades.Usuario;
import Entidades.MensajesAlerta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUsuario extends Crud<Usuario> {

    //OBJETO PARA MOSTRAR LAS ALERTAS
    MensajesAlerta showAlert = new MensajesAlerta() {
    };
    //SE USARAN OBJETOS DE LA CLASE USUARIO

   public boolean login(Usuario objeto) {
    String query = "SELECT tipo FROM usuario WHERE nombre = ? AND contraseña = ?";
    try (Connection conn = conectar(); 
         PreparedStatement statement = conn.prepareStatement(query)) {

        // Configura los parámetros de la consulta
        statement.setString(1, objeto.getNombre());
        statement.setString(2, objeto.getPassword());

        // Ejecuta la consulta
        try (ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                // Si hay un resultado, las credenciales son válidas

                // Obtener el valor de la columna 'tipo'
                String tipo = resultSet.getString("tipo");

                // Establecer el tipo en el objeto Usuario
                objeto.setTipo(tipo);

                return true;
            }
        }
    } catch (SQLException e) {
        showAlert.errorAlert("Error al intentar iniciar sesión: " + e.getMessage());
    }
    return false;
}

    public boolean Registrar(Usuario objeto) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();
        try {
            PreparedStatement consulta = cn.prepareStatement("insert into usuario values(?,?,?,?)");

            if (objeto.getNombre() == null || objeto.getNombre().equals(" ") && objeto.getPassword() == null || objeto.getPassword().equals(" ")) {
                respuesta = false;
            }
            consulta.setString(2, objeto.getNombre());
            consulta.setString(3, objeto.getPassword());

            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }
            cn.close();
        } catch (SQLException e) {
            showAlert.errorAlert("Error al guardar usuario" + e.getMessage());
            e.printStackTrace();

        }
        return respuesta;
    }

    public boolean Eliminar(Usuario objeto) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();
        try {
            PreparedStatement consulta = cn.prepareStatement("insert into usuario values(?,?,?,?)");

            if (objeto.getNombre() == null || objeto.getNombre().equals(" ") && objeto.getPassword() == null || objeto.getPassword().equals(" ")) {
                respuesta = false;
            }
            consulta.setString(2, objeto.getNombre());
            consulta.setString(3, objeto.getPassword());

            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }
            cn.close();
        } catch (SQLException e) {
            showAlert.errorAlert("Error al guardar usuario" + e.getMessage());
            e.printStackTrace();

        }
        return respuesta;
    }

}
