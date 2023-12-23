module cd.project.frontend {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires jbcrypt;

    opens cd.project.client to javafx.fxml;
    exports cd.project.client;
    exports cd.project.client.ui.controllers;
    opens cd.project.client.ui.controllers to javafx.fxml;
    exports cd.project.client.core;
    opens cd.project.client.core to javafx.fxml;
}