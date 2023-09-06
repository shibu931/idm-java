module org.idm {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.idm to javafx.fxml;
    exports org.idm;
}