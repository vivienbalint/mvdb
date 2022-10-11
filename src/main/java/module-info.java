module app.mvdb {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    requires mysql.connector.java;
    requires java.sql;

    opens db to javafx.fxml;
    exports db;

    opens gui to javafx.fxml;
    exports gui;
}