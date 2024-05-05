package project.printseven;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import project.printseven.service.DirectoryWatcher;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    public static DirectoryWatcher directoryWatcher = new DirectoryWatcher();
    private double x = 0;
    private double y = 0;
    @Override
    public void start(Stage stage) throws IOException {
        Parent fxmlLoader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        Scene scene = new Scene(fxmlLoader);
        fxmlLoader.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();});
        fxmlLoader.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - x);
            stage.setY(mouseEvent.getScreenY() - y);
            stage.setOpacity(.8);});
        fxmlLoader.setOnMouseReleased(mouseEvent -> stage.setOpacity(1));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        // Создаем новый поток для запуска watcherStarter()
        Thread watcherThread = new Thread(() -> directoryWatcher.watcherStarter());
        watcherThread.setDaemon(true); // Устанавливаем флаг демона, чтобы поток завершился, когда закончится основной поток
        watcherThread.start();
    }

    public static void main(String[] args) {
        launch();
    }
}