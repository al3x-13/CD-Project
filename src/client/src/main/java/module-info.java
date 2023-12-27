module cd.project.client {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires jbcrypt;
    requires org.apache.cxf.frontend.jaxws;
    requires jakarta.xml.ws;
    requires cd.project.frontend;

    opens cd.project.client to javafx.fxml;
    exports cd.project.client;
    exports cd.project.client.ui.controllers;
    opens cd.project.client.ui.controllers to javafx.fxml;
    exports cd.project.client.core;
    opens cd.project.client.core to javafx.fxml;
}
