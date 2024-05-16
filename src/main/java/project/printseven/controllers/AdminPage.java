package project.printseven.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import project.printseven.DataBaseConnect;
import project.printseven.HelloController;
import project.printseven.dto.PhotoRes;
import project.printseven.dto.UserRes;
import project.printseven.enums.Role;
import project.printseven.service.UserService;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

public class AdminPage {
    private UserService userService = new UserService();
    @FXML
    private ListView<UserRes> userListView;
    @FXML
    private AnchorPane main_form;
    @FXML
    private Button close;

    @FXML
    private Button minimize;

    @FXML
    private Label username;

    @FXML
    private Button home_btn;

    @FXML
    private Button addStudents_btn;

    @FXML
    private Button availableCourse_btn;

    @FXML
    private Button studentGrade_btn;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane home_form;

    @FXML
    private Label home_totalEnrolled;

    @FXML
    private Label home_totalFemale;

    @FXML
    private Label home_totalMale;

    @FXML
    private BarChart<?, ?> home_totalEnrolledChart;

    @FXML
    private AreaChart<?, ?> home_totalFemaleChart;

    @FXML
    private LineChart<?, ?> home_totalMaleChart;

    @FXML
    private AnchorPane addStudents_form;

    @FXML
    private TextField addStudents_search;
    @FXML
    private TextField addStudents_studentNum;

    @FXML
    private ComboBox<?> addStudents_year;

    @FXML
    private ComboBox<?> addStudents_course;

    @FXML
    private TextField emailFld;
    @FXML
    private TextField passwordFld;

    @FXML
    private TextField addStudents_lastName;

    @FXML
    private DatePicker addStudents_birth;

    @FXML
    private ComboBox<?> addStudents_status;

    @FXML
    private ComboBox<?> addStudents_gender;

    @FXML
    private ImageView addStudents_imageView;

    @FXML
    private Button addStudents_insertBtn;

    @FXML
    private Button addStudents_addBtn;

    @FXML
    private Button addStudents_updateBtn;

    @FXML
    private Button addStudents_deleteBtn;

    @FXML
    private Button addStudents_clearBtn;

    @FXML
    private TextField availableCourse_course;

    @FXML
    private TextField availableCourse_description;

    @FXML
    private TextField availableCourse_degree;

    @FXML
    private Button availableCourse_addBtn;

    @FXML
    private Button availableCourse_updateBtn;

    @FXML
    private Button availableCourse_clearBtn;

    @FXML
    private Button availableCourse_deleteBtn;

    @FXML
    private TextField studentGrade_studentNum;

    @FXML
    private Label studentGrade_year;

    @FXML
    private Label studentGrade_course;

    @FXML
    private TextField studentGrade_firstSem;

    @FXML
    private TextField studentGrade_secondSem;

    @FXML
    private Button studentGrade_updateBtn;

    @FXML
    private Button studentGrade_clearBtn;

    @FXML
    private TextField studentGrade_search;

    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == home_btn) {
            List<UserRes> users = userService.getAllUsers(HelloController.currentUser.getId());

            // Создание таблицы пользователей
            TableView<UserRes> tableView = new TableView<>();

            // Создание столбцов таблицы
            TableColumn<UserRes, Integer> idColumn = new TableColumn<>("Id");
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

            TableColumn<UserRes, String> emailColumn = new TableColumn<>("Email");
            emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

            TableColumn<UserRes, Integer> successA4Column = new TableColumn<>("Success A4");
            successA4Column.setCellValueFactory(new PropertyValueFactory<>("successA4"));

            TableColumn<UserRes, Integer> successA5Column = new TableColumn<>("Success A5");
            successA5Column.setCellValueFactory(new PropertyValueFactory<>("successA5"));

            TableColumn<UserRes, Integer> successA6Column = new TableColumn<>("Success A6");
            successA6Column.setCellValueFactory(new PropertyValueFactory<>("successA6"));

            TableColumn<UserRes, Integer> errorA4Column = new TableColumn<>("Error A4");
            errorA4Column.setCellValueFactory(new PropertyValueFactory<>("errorA4"));

            TableColumn<UserRes, Integer> errorA5Column = new TableColumn<>("Error A5");
            errorA5Column.setCellValueFactory(new PropertyValueFactory<>("errorA5"));

            TableColumn<UserRes, Integer> errorA6Column = new TableColumn<>("Error A6");
            errorA6Column.setCellValueFactory(new PropertyValueFactory<>("errorA6"));

            // Добавление столбцов в TableView
            tableView.getColumns().addAll(idColumn, emailColumn, successA4Column, successA5Column, successA6Column, errorA4Column, errorA5Column, errorA6Column);

            // Добавление данных в TableView
            ObservableList<UserRes> data = FXCollections.observableArrayList(users);
            tableView.setItems(data);

            // Установка предпочтительного размера TableView
            tableView.setPrefSize(841, 500);

            AnchorPane parentAnchorPane = (AnchorPane) home_form.getParent();

            // Удаление ListView из родительского узла
            parentAnchorPane.getChildren().remove(userListView);
            System.out.println("1________" + parentAnchorPane);
            // Добавление TableView в родительский узел
            parentAnchorPane.getChildren().add(tableView);

            // Установка макета ячейки для TableView
            tableView.setRowFactory(tv -> {
                TableRow<UserRes> row = new TableRow<>();
                row.setOnMouseClicked(event2 -> {
                    if (!row.isEmpty()) {
                        UserRes rowData = row.getItem();
                        System.out.println("rowData.getId() = " + rowData.getId());
                        // Ваш код для обработки нажатия на пользователя
                        showPhotosByUserId(rowData.getId());
                    }
                });
                return row;
            });


            System.out.println("1-IF");
            System.out.println("DO TRUE " + home_form.isVisible());
            home_form.setVisible(true);
            System.out.println("POSLE: " + home_form.isVisible());
            System.out.println(addStudents_form.isVisible());
            addStudents_form.setVisible(false);
            System.out.println(addStudents_form.isVisible());
            home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            addStudents_btn.setStyle("-fx-background-color:transparent");
            availableCourse_btn.setStyle("-fx-background-color:transparent");
            studentGrade_btn.setStyle("-fx-background-color:transparent");
        } else if (event.getSource() == addStudents_btn) {
            System.out.println("2-IF");
            System.out.println("DO TRUE: " + addStudents_form.isVisible());
            addStudents_form.setVisible(true);
            System.out.println("POSLE: " + addStudents_form.isVisible());
            System.out.println(home_form.isVisible());
            home_form.setVisible(false);
            System.out.println(home_form.isVisible());


            addStudents_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            home_btn.setStyle("-fx-background-color:transparent");
            availableCourse_btn.setStyle("-fx-background-color:transparent");
            studentGrade_btn.setStyle("-fx-background-color:transparent");

        }
    }

    private double x = 0;
    private double y = 0;

    public void logout() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                //HIDE YOUR DASHBOARD FORM
                logout.getScene().getWindow().hide();

                //LINK YOUR LOGIN FORM
                URL location = getClass().getResource("/project/printseven/hello-view.fxml");
                if (location == null) {
                    System.err.println("FXML file not found!");
                    return;
                }

                // Создаем загрузчик FXML
                FXMLLoader loader = new FXMLLoader(location);

                // Загружаем корневой элемент из файла FXML
                Parent root = loader.load();

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();

            } else {
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addStudentsAdd() {
        String insertData = "INSERT INTO users "
                + "(email,password,role) "
                + "VALUES(?,?,?)";
        String insertRelation = "insert into users_users(user_id,users_id) values(?,?)";
        Connection connection = DataBaseConnect.getConnection();

        try {
            Alert alert;

            if (emailFld.getText().isEmpty()
                    || passwordFld.getText().isEmpty() || !emailFld.getText().endsWith("@gmail.com") || passwordFld.getText().length() < 4) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                String checkData = "SELECT * FROM users WHERE email = ?";

                PreparedStatement preparedStatement = connection.prepareStatement(checkData);
                preparedStatement.setString(1, emailFld.getText());
                ResultSet result = preparedStatement.executeQuery();

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("User #" + emailFld.getText() + " was already exist!");
                    alert.showAndWait();
                } else {
                    PreparedStatement preparedStatement1 = connection.prepareStatement(insertData);
                    preparedStatement1.setString(1, emailFld.getText());
                    preparedStatement1.setString(2, passwordFld.getText());
                    preparedStatement1.setString(3, Role.USER.name());
                    preparedStatement1.execute();

                    String getSaveUserId = "SELECT * FROM users WHERE email = ?";
                    PreparedStatement prepared = connection.prepareStatement(getSaveUserId);
                    prepared.setString(1, emailFld.getText());
                    ResultSet res = preparedStatement.executeQuery();
                    Long id = null;
                    while (res.next()) {
                        id = res.getLong("id");
                    }
                    PreparedStatement preparedStatement2 = connection.prepareStatement(insertRelation);
                    preparedStatement2.setLong(1, HelloController.currentUser.getId());
                    preparedStatement2.setLong(2, id);
                    preparedStatement2.execute();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showPhotosByUserId(Long userId) {
        List<PhotoRes> photoRes = userService.getPhotosByUserId(userId);
        TableView<PhotoRes> tableView = new TableView<>();
        // Создание столбцов таблицы
        TableColumn<PhotoRes, Integer> idColumn = new TableColumn<>("Id");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<PhotoRes, String> size = new TableColumn<>("Size");
        size.setCellValueFactory(new PropertyValueFactory<>("Size"));
        TableColumn<PhotoRes, String> status = new TableColumn<>("success");
        status.setCellValueFactory(new PropertyValueFactory<>("success"));
        TableColumn<PhotoRes, String> createdAd = new TableColumn<>("createdAd");
        createdAd.setCellValueFactory(new PropertyValueFactory<>("createdAd"));
        TableColumn<PhotoRes, String> url = new TableColumn<>("imageUrl");
        url.setCellValueFactory(new PropertyValueFactory<>("imageUrl"));

        // Добавление столбцов в TableView
        tableView.getColumns().addAll(idColumn, size, status, createdAd, url);

        // Добавление данных в TableView
        ObservableList<PhotoRes> data = FXCollections.observableArrayList(photoRes);
        tableView.setItems(data);

        // Установка предпочтительного размера TableView
        tableView.setPrefSize(841, 500);
        // Получение родительского узла для ListView (AnchorPane)
        AnchorPane parentAnchorPane = (AnchorPane) home_form.getParent();

        System.out.println("2____" + parentAnchorPane);
        // Удаление ListView из родительского узла
        parentAnchorPane.getChildren().remove(userListView);

        // Добавление TableView в родительский узел
        parentAnchorPane.getChildren().add(tableView);

        // Установка макета ячейки для TableView
        tableView.setRowFactory(tv -> {
            TableRow<PhotoRes> row = new TableRow<>();
            row.setOnMouseClicked(event2 -> {
                if (!row.isEmpty()) {
                    PhotoRes rowData = row.getItem();
                    System.out.println("Clicked on row: " + rowData.getId());

                    // Получение ссылки на изображение из объекта rowData
                    String imageUrl = rowData.getImageUrl();

                    // Копирование ссылки на изображение в буфер обмена
                    Clipboard clipboard = Clipboard.getSystemClipboard();
                    ClipboardContent content = new ClipboardContent();
                    content.putString(imageUrl);
                    clipboard.setContent(content);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Успешно!");
                    alert.setHeaderText(null);
                    alert.setContentText("Ссылка скопирована в буфер!");

                    // Создание таймлайна для автоматического закрытия сообщения через 2 секунды
                    Timeline timeline = new Timeline(
                            new KeyFrame(Duration.seconds(2), e -> alert.hide())
                    );
                    timeline.play();

                    // Показать информационное сообщение
                    alert.show();
                }


            });
            return row;
        });
    }
}