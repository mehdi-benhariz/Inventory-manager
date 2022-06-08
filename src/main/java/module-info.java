module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens com.example to javafx.fxml;
    opens com.example.Models to javafx.fxml, javafx.base;
    opens com.example.DAO to javafx.fxml;
    opens com.example.DTO to javafx.fxml, javafx.base;
    opens com.example.controllers to javafx.fxml, javafx.base;

    exports com.example;
}