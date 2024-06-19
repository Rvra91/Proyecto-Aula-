module com.mycompany.proyectoaula {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires animatefx;

    opens com.mycompany.proyectoaula to javafx.fxml;
    exports com.mycompany.proyectoaula;
}
