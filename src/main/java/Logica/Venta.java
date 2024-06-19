/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import Conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Entidades.VentaE;
import Entidades.VentaE;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import javafx.scene.control.TextField;

/**
 *
 * @author Ramon
 */
public class Venta {

    // Método para realizar una venta
    public void realizarVenta(VentaE objeto) throws SQLException {
        // Consulta SQL para insertar la venta
        String sql = "INSERT INTO ventas (id_producto, id_categoria, id_cliente, tipo, cant_vendida, dinero, fecha) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection cn = null;
        PreparedStatement consulta = null;
        ResultSet rs = null; // Resultado de la consulta para obtener id_categoria

        try {
            cn = Conexion.conectar();

            // Subconsulta para obtener id_categoria a partir de id_producto
            String sqlSubconsulta = "SELECT id_categoria FROM producto WHERE id = ?";
            PreparedStatement consultaSub = cn.prepareStatement(sqlSubconsulta);
            consultaSub.setInt(1, objeto.getId_producto());
            rs = consultaSub.executeQuery();

            // Obtener el resultado de la subconsulta
            int idCategoria = 0; // Valor por defecto o manejo de error si no se encuentra
            if (rs.next()) {
                idCategoria = rs.getInt("id_categoria");
            } else {
                throw new SQLException("No se encontró id_categoria para el id_producto: " + objeto.getId_producto());
            }

            // Preparar la consulta principal de inserción
            consulta = cn.prepareStatement(sql);

            // Asignar valores a los parámetros de la consulta principal
            consulta.setInt(1, objeto.getId_producto());
            consulta.setInt(2, idCategoria); // Asignar id_categoria obtenido de la subconsulta
            consulta.setInt(3, objeto.getId_cliente());
            consulta.setString(4, objeto.getTipo());
            consulta.setInt(5, objeto.getCant_vendida());
            consulta.setDouble(6, objeto.getDinero() * objeto.getCant_vendida());
            LocalDate fechaActual = LocalDate.now();
            consulta.setDate(7, Date.valueOf(fechaActual));
            // Ejecutar la consulta principal de inserción
            consulta.executeUpdate();

            System.out.println("Venta registrada correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al registrar la venta: " + e.getMessage());
            throw e; // Lanzar la excepción para manejo superior
        } finally {
            // Cerrar ResultSet, PreparedStatement y Connection en el bloque finally para asegurar la liberación de recursos
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
    }

    // Método para calcular el cambio
    public void calcularCambio(double totalVenta, double dineroRecibido, TextField txt) {
        if (dineroRecibido >= totalVenta) {
            double cambio = dineroRecibido - totalVenta; // Calcula el cambio
            String cambioString = String.format("%.2f", cambio); // Formatea el cambio a un String con dos decimales
            txt.setText("Cambio: " + cambioString); // Asigna el cambio al TextField
        } else {
            txt.setText("Error: Dinero insuficiente."); // Muestra un mensaje de error en el TextField
            System.out.println("Error: Dinero recibido insuficiente."); // O también puedes optar por esta línea para mostrar un mensaje en consola.
        }
    }

}


