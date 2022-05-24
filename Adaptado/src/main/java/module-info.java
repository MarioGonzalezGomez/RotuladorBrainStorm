module RotulosDeportes {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires static lombok;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires org.mybatis;

    opens mggcode to javafx.fxml;
    opens mggcode.entity to javafx.base;
    exports mggcode;
    exports  mggcode.entity;
}