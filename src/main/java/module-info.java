module project.printseven {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jakarta.persistence;
    requires static lombok;
    requires gax;
    requires com.google.auth.oauth2;
    requires google.api.client;
    requires com.google.api.client;
    requires com.google.api.services.drive;
    requires com.google.api.client.json.gson;
    requires java.desktop;
    requires com.sun.jna;
    requires com.sun.jna.platform;
    requires commons.exec;


    exports project.printseven; // Экспортируем пакет для других модулей
    exports project.printseven.service;
    opens project.printseven to javafx.fxml; // Открываем пакет для других модулей, включая javafx.fxml
    opens project.printseven.controllers to javafx.fxml;
    exports project.printseven.controllers; // Открываем пакет controllers для других модулей, включая javafx.fxml


}