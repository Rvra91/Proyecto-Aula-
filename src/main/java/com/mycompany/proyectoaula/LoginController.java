package com.mycompany.proyectoaula;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */


import Entidades.MensajesAlerta;

import Entidades.Usuario;
import Logica.CrudUsuario;
import animatefx.animation.Bounce;
import animatefx.animation.FadeIn;
import animatefx.animation.Flash;
import animatefx.animation.ZoomIn;
import animatefx.animation.ZoomOut;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author Ramon
 */
public class LoginController implements Initializable {

    Usuario usuario = new Usuario();
    CrudUsuario cr = new CrudUsuario();
    MensajesAlerta showAlert = new MensajesAlerta() {};

    @FXML
    private TextField txtuser;
    @FXML
    private PasswordField txtpassword;
    @FXML
    private Button btnregistro;
    @FXML
    private TextField txtuser1;
    @FXML
    private PasswordField txtpassword1;
    @FXML
    private Button btnlogin1;
    @FXML
    private Button btnregistro1;
    @FXML
    private Circle btnmin1;
    @FXML
    private Circle btnmax1;
    @FXML
    private Circle btnclose1;
    @FXML
    private Circle btnmin;
    @FXML
    private Circle btnmax;
    @FXML
    private Circle btnclose;
    @FXML
    private ImageView btnback;
    @FXML
    private Pane pnregistro;
    @FXML
    private Pane pnlogin;
    @FXML
    private Label btnrecuperar;
    @FXML
    private Label aviso;
    @FXML
    private Label aviso1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//INICIAMOS LOS OBJETOS Y CLASES NECESARIAS PARA EL INICIO DE SESION

    }

    @FXML
    private void Login(ActionEvent event) throws IOException {

        usuario.setNombre(txtuser1.getText());
        usuario.setPassword(txtpassword1.getText());
        cr.login(usuario);
        if (cr.login(usuario) == true) {
            
            if(usuario.getTipo().equals("Admin")){
                 new FadeIn(pnlogin).play();
            showAlert.infoAlert("Iniciando Sesion...");
            App.setStage("Menu",true);
            }else{
                
                 new FadeIn(pnlogin).play();
            showAlert.infoAlert("Iniciando Sesion...");
            App.setStage("Menu_1",true);
            }
           
        } else {

            aviso1.setText("Usuario O Contraseña Incorrectos.");

        }

    }

    @FXML
    private void Registro(ActionEvent event) throws IOException {
        if (event.getSource() == btnregistro1) {
            new Bounce(pnregistro).play();
            pnlogin.toBack();

        } else if (event.getSource() == btnregistro) {
            usuario.setNombre(null);
            usuario.setPassword(null);
            usuario.setNombre(txtuser.getText());
            usuario.setPassword(txtpassword.getText());
            if (usuario.getNombre().isEmpty() || usuario.getPassword().isEmpty()) {

                aviso.setText("Usuario O Contraseña Vacios.");
            } else if (usuario.getPassword().length() <= 4) {
                // Verificar si la longitud de la contraseña es mayor a 4 caracteres
                aviso.setText("La contraseña debe tener más de 4 caracteres.");
            } else {
                cr.Registrar(usuario);
                System.out.println(txtuser1.getText());
                System.out.println(txtpassword1.getText());
                aviso.setText("Usuario Registrado");
            }

        }

    }

    private void Close(ActionEvent event) throws IOException {
        System.exit(1);

    }

    @FXML
    private void close(MouseEvent event) throws IOException {
        System.exit(1);
    }

    @FXML
    private void recuperar(MouseEvent event) throws IOException {

    }

    @FXML
    private void back(MouseEvent event) throws IOException {
        new Bounce(pnregistro).play();

        new Bounce(pnlogin).play();
        pnlogin.toFront();

    }
}
