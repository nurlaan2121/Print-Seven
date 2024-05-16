package project.printseven.controllers;//package project.printseven.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import project.printseven.dto.UserRes;
import project.printseven.service.UserListCellFactory;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class YourController implements Initializable {
    @FXML
    private ListView<UserRes> userListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userListView.setCellFactory(new UserListCellFactory());
    }
}





//
//import javafx.application.Application;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.scene.Scene;
//import javafx.scene.control.ListView;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
//public class YourController extends Application {
//
//    @Override
//    public void start(Stage primaryStage) {
//        // Создаем список элементов для отображения в ListView
//        ObservableList<String> items = FXCollections.observableArrayList(
//                "Item 1",
//                "Item 2",
//                "Item 2",
//                "Item 2",
//                "Item 2",
//                "Item 2",
//                "Item 2",
//                "Item 2",
//                "Item 2",
//                "Item 2",
//                "Item 2",
//                "Item 2",
//                "Item 2",
//                "Item 2",
//                "Item 2",
//                "Item 2",
//                "Item 2",
//                "Item 2",
//                "Item 2",
//                "Item 2",
//                "Item 2",
//                "Item 2",
//                "Item 2",
//                "Item 2",
//                "Item 2",
//                "Item 2",
//                "Item 2",
//                "Item 2",
//                "Item 2",
//                "Item 2",
//                "Item 2",
//                "Item 2",
//                "Item 2",
//                "Item 2",
//                "Item 2",
//                "Item 2",
//                "Item 3"
//        );
//
//        // Создаем ListView и передаем в него список элементов
//        ListView<String> listView = new ListView<>(items);
//
//        // Создаем контейнер VBox и добавляем в него ListView
//        VBox root = new VBox(listView);
//
//        // Создаем сцену и добавляем в нее корневой узел
//        Scene scene = new Scene(root, 300, 250);
//
//        // Устанавливаем сцену для primaryStage
//        primaryStage.setScene(scene);
//
//        // Устанавливаем заголовок окна
//        primaryStage.setTitle("ListView Example");
//
//        // Отображаем primaryStage
//        primaryStage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
//
