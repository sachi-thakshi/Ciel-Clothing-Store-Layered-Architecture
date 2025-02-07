module lk.ijse.gdse.cielclothingstore {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires lombok;
    requires com.jfoenix;
    requires net.sf.jasperreports.core;
    requires java.mail;
    requires mysql.connector.j;

    opens lk.ijse.gdse.cielclothingstore to javafx.fxml;
    exports lk.ijse.gdse.cielclothingstore;
    exports lk.ijse.gdse.cielclothingstore.controller;
    opens lk.ijse.gdse.cielclothingstore.controller to javafx.fxml;
    exports lk.ijse.gdse.cielclothingstore.dto;
    opens lk.ijse.gdse.cielclothingstore.dto to javafx.fxml;
    exports lk.ijse.gdse.cielclothingstore.view.tm;
    opens lk.ijse.gdse.cielclothingstore.view.tm to javafx.base;
}