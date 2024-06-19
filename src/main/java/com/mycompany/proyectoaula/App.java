package com.mycompany.proyectoaula;





import animatefx.animation.ZoomIn;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 * JavaFX App
 */
public class App extends Application {
    
    private static Scene scene;
    
    @Override
    public void start(Stage stage) throws IOException {
        
        scene = new Scene(loadFXML("Login"), 850, 670);
        stage.setScene(scene);
        stage.setResizable(false); // Establecer que la ventana no es resizable
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }
    
    public static void loadSubView(HBox contentPane, String fxml) {
        try {
            // Cargar el archivo FXML de la sub-vista
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
            Parent subView = loader.load();

            // Aplicar animación a la subvista recién cargada
            ZoomIn zoomInAnimation = new ZoomIn(subView);
            zoomInAnimation.setSpeed(2); // Configura la velocidad de la animación si es necesario
            zoomInAnimation.play();

            // Configurar el crecimiento horizontal del nodo dentro del HBox
            HBox.setHgrow(subView, Priority.ALWAYS);

            // Limpiar cualquier contenido previo en el contenedor y añadir la sub-vista con animación
            contentPane.getChildren().clear();
            contentPane.getChildren().add(subView);
            
        } catch (IOException e) {
            // Manejo de la excepción: imprime el seguimiento de la pila o realiza otra acción adecuada
            e.printStackTrace();
        } catch (Exception e) {
            // Manejo de cualquier otra excepción que pueda ocurrir durante la carga o configuración
            e.printStackTrace();
        }
    }
    
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    // Reemplaza una stage por otra

    public static void setStage(String fxml, boolean n) throws IOException {
        Window ventana = scene.getWindow();
        Stage stage = (Stage) ventana;
        
        Stage nuevaStage = new Stage();
        scene = new Scene(loadFXML(fxml));
        nuevaStage.setScene(scene);
        
        if (n = false) {
            stage.hide();
            nuevaStage.setMaximized(true);
        } else {
     
            nuevaStage.setResizable(false);
           
        }
        
        nuevaStage.show();
    }

    // Crea una nueva scenea y la establece como escene de la Stage principal
    public static void setNewScene(String fxml) throws IOException {
        Window ventana = scene.getWindow();
        Stage stage = (Stage) ventana;
        setRoot(fxml);
        stage.setMaximized(true);
    }
    
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    public static void main(String[] args) {
        launch();
    }
    
}
