/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public abstract class Crud<T> implements Cruds<T> {

    @Override
    public boolean guardar(T objeto, ComboBox<String> combo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void cargarCombo(ComboBox<String> combo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void cargarTabla(TableView<Object[]> tabla,Label label) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean eliminar(String objeto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override

    public void buscar(TableView<Object[]> tabla, TextField txtBuscar,Label label) {
        String textoBusqueda = txtBuscar.getText().trim().toLowerCase();

        if (textoBusqueda.isEmpty()) {
            this.cargarTabla(tabla,label);
            return;
        }

        // Lista auxiliar para almacenar las filas que cumplen con el criterio de búsqueda
        List<Object[]> filasFiltradas = new ArrayList<>();

        // Recorrer los datos actuales de la tabla y filtrar según el texto de búsqueda
        for (Object[] fila : tabla.getItems()) {
            // Aquí debes implementar la lógica de comparación para cada fila de la tabla
            // Por ejemplo, supongamos que la columna de nombre está en la posición 1
            String nombre = fila[1].toString().toLowerCase();

            if (nombre.contains(textoBusqueda)) {
                filasFiltradas.add(fila); // Agregar la fila que cumple con el filtro
            }
        }

        // Limpiar los datos actuales de la tabla
        tabla.getItems().clear();

        // Agregar las filas filtradas a la tabla
        tabla.getItems().addAll(filasFiltradas);
    }
    @Override
    public void editar(String id){
        
    }
}
