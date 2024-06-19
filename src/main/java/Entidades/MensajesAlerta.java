package Entidades;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public abstract class MensajesAlerta {
    
    public void infoAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mensaje: ");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void errorAlert(String message) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Alerta");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void confirmationAlert(String message) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alerta");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void warningAlert(String message) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Alerta");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    
    
}
