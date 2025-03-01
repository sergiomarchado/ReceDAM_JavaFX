module org.example.recetasapijavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;

    // Hibernate y base de datos
    requires java.sql;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;

    // JSON con Gson
    requires com.google.gson;

    // Lombok (Static solo en tiempo de compilación)
    requires static lombok;

    requires java.naming;

    // Abre paquetes para que Hibernate, Gson y JavaFX puedan acceder a ellos
    opens org.example.recedamjavafx.models to org.hibernate.orm.core, com.google.gson, javafx.base;
    opens org.example.recedamjavafx.dao to org.hibernate.orm.core;
    opens org.example.recedamjavafx.utils to org.hibernate.orm.core;
    opens org.example.recedamjavafx.controllers to javafx.fxml;
    opens org.example.recedamjavafx to javafx.fxml;

    // Exporta paquetes accesibles desde fuera del módulo
    exports org.example.recedamjavafx;
    exports org.example.recedamjavafx.controllers;
    exports org.example.recedamjavafx.models;
}
