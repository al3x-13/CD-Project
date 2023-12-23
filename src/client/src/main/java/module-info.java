module cd.project.frontend {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires jbcrypt;

    opens cd.project.client to javafx.fxml;
    exports cd.project.client;
    exports cd.project.client.controllers;
    opens cd.project.client.controllers to javafx.fxml;
}