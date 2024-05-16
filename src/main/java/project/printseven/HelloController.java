package project.printseven;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import project.printseven.controllers.UserPage;
import project.printseven.entities.User;
import project.printseven.enums.Role;
import project.printseven.service.UserService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Properties;


public class HelloController {
    private UserService userService = new UserService();
    public static User currentUser;

    private double x = 0;
    private double y = 0;


    @FXML
    private Button closeBtn;

    @FXML
    private TextField emailFld;

    @FXML
    private Button loginBtn;

    @FXML
    private AnchorPane mainForm;

    @FXML
    private PasswordField passwordFld;

    @FXML
    void close() {
        System.exit(0);
    }

    @FXML
    public void signIn() throws IOException {
        String email = emailFld.getText();
        String password = passwordFld.getText();
        User user = userService.signIn(email, password);
        if (user != null) {
            loginBtn.getScene().getWindow().hide();
            //LINK YOUR DASHBOARD
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminPage.fxml")));
            currentUser = user;
            if (currentUser.getRole().equals(Role.ADMIN)) {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminPage.fxml")));
            } else {
                String res = saveInfoInComp(user);
                System.out.println(res);
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("userPage.fxml")));
            }
            Stage stage = new Stage();
            Scene scene = new Scene(root);

            root.setOnMousePressed((MouseEvent event) -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });

            root.setOnMouseDragged((MouseEvent event) -> {
                stage.setX(event.getScreenX() - x);
                stage.setY(event.getScreenY() - y);
            });

            stage.initStyle(StageStyle.TRANSPARENT);

            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Email or password invalid!");
            alert.showAndWait();
        }
    }

    private String saveInfoInComp(User user) {
        String filePath = "C:\\Users\\user\\Desktop\\ValuesForPrintContoller\\values.properties";

        // Создание объекта Properties
        Properties properties = new Properties();

        // Добавление ключ-значение
        properties.setProperty("id", String.valueOf(user.getId()));
        properties.setProperty("email", String.valueOf(user.getEmail()));
        properties.setProperty("password", String.valueOf(user.getPassword()));

        // Запись в файл
        try (OutputStream output = new FileOutputStream(filePath)) {
            properties.store(output, "Key-Value Pairs");
            System.out.println("Запись успешно выполнена.");
            return "Success";
        } catch (IOException io) {
            io.printStackTrace();
        }
        return "INVALID CHTO TO";
    }


}
