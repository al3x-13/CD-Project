module cd.project.backend {
    requires java.sql;
    requires jakarta.xml.bind;
    requires java.rmi;
    exports cd.project.backend.interfaces;
    exports cd.project.backend.services;
}