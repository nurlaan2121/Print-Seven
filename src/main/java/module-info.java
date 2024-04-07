module project.printseven {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jakarta.persistence;
    requires static lombok;

    exports project.printseven; // Экспортируем пакет для других модулей
    opens project.printseven to javafx.fxml; // Открываем пакет для других модулей, включая javafx.fxml
    opens project.printseven.controllers to javafx.fxml; // Открываем пакет controllers для других модулей, включая javafx.fxml


}