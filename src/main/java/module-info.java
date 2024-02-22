module sn.dev.userapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires java.sql;

    opens sn.dev.userapp to javafx.fxml;
    exports sn.dev.userapp;

    opens sn.dev.userapp.controller to javafx.fxml;
    exports sn.dev.userapp.controller;

    opens sn.dev.userapp.entity;


}