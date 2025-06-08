module com.mycompany.bd_museomahn_proyecto2 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.bd_museomahn_proyecto2 to javafx.fxml;
    exports com.mycompany.bd_museomahn_proyecto2;
}
