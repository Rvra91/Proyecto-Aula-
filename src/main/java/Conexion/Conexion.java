package Conexion;

import Entidades.MensajesAlerta;
import Entidades.MensajesAlerta;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {
    //OBJETO PARA MOSTRAR LAS ALERTAS:

    private static MensajesAlerta showAlert = new MensajesAlerta() {
    };

    public static Connection conectar() {
        try {
            Class.forName("org.sqlite.JDBC");
            String dbPath = System.getProperty("user.dir") + "/database/baseDatos.db";
            Connection cn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            //System.out.println("Conexion Correcta");
            //   showAlert.infoAlert(" se pudo conectar a la base de datos");      
            return cn;
        } catch (ClassNotFoundException | SQLException e) {
            showAlert.errorAlert("No se pudo conectar a la base de datos");
            e.printStackTrace();

        }

        return null;
    }

}
