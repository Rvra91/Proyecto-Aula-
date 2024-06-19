/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Entidades;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author Ramon
 */
public interface Cruds<T> {

    boolean guardar(T objeto, ComboBox<String> combo);

    public void cargarCombo(ComboBox<String> combo);

    public void cargarTabla(TableView<Object[]> tabla,Label label);

    boolean eliminar(String id);

    void buscar(TableView<Object[]> tabla, TextField txtBuscar,Label Label);

    void editar(String id);
}
