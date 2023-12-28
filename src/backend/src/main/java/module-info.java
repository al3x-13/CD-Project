module cd.project.backend {
    requires java.sql;
    requires java.rmi;
    requires jakarta.xml.bind;
    exports cd.project.backend.interfaces;
    exports cd.project.backend.services;
}