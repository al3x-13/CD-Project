module cd.project.frontend {
    requires org.apache.cxf.frontend.jaxws;
    requires jakarta.xml.ws;
    requires com.fasterxml.jackson.core;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires java.sql;
    requires com.auth0.jwt;
    requires jbcrypt;
    requires jakarta.jws;
    requires spring.web;
    requires jakarta.servlet;
    requires cd.project.backend;
    requires java.rmi;
    exports cd.project.frontend;
    exports cd.project.frontend.soap;
    exports cd.project.frontend.rest;
}