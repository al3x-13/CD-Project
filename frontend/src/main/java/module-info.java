module cd.project.frontend {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;

    opens cd.project.frontend to javafx.fxml;
    exports cd.project.frontend;
}