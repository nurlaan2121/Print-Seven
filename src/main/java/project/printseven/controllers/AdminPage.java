package project.printseven.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import project.printseven.DataBaseConnect;
import project.printseven.HelloController;
import project.printseven.dto.UserRes;
import project.printseven.entities.User;
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
            userListView.setItems(FXCollections.observableArrayList(users));
            userListView.setCellFactory(new Callback<ListView<UserRes>, ListCell<UserRes>>() {
                @Override
                public ListCell<UserRes> call(ListView<UserRes> param) {
                    return new ListCell<UserRes>() {
                        @Override
                        protected void updateItem(UserRes userRes, boolean empty) {
                            super.updateItem(userRes, empty);
                            if (userRes != null) {
                                HBox hbox = new HBox();
                                Label emailLabel = new Label("Email: ");
                                Label emailValueLabel = new Label(userRes.getEmail());
                                Label successa4 = new Label("Success A4: ");
                                Label successa4ValueLabel = new Label(String.valueOf(userRes.getSuccessA4()));
                                Label two = new Label("Success A5: ");
                                Label twoValueLabel = new Label(String.valueOf(userRes.getSuccessA5()));
                                Label tree = new Label("Success A6: ");
                                Label treeValueLabel = new Label(String.valueOf(userRes.getSuccessA6()));
                                Label errora4 = new Label("Error A4: ");
                                Label errora4ValueLabel = new Label(String.valueOf(userRes.getErrorA4()));
                                Label twoError = new Label("Error A5: ");
                                Label twoErrorValueLabel = new Label(String.valueOf(userRes.getError5()));
                                Label treeError = new Label("Error A6: ");
                                Label treeErrorValueLabel = new Label(String.valueOf(userRes.getErrorA6()));

                                HBox.setHgrow(emailValueLabel, Priority.ALWAYS);
                                HBox.setHgrow(successa4ValueLabel, Priority.ALWAYS);
                                HBox.setHgrow(twoValueLabel, Priority.ALWAYS);
                                HBox.setHgrow(treeValueLabel, Priority.ALWAYS);
                                HBox.setHgrow(errora4ValueLabel, Priority.ALWAYS);
                                HBox.setHgrow(twoErrorValueLabel, Priority.ALWAYS);
                                HBox.setHgrow(treeErrorValueLabel, Priority.ALWAYS);

                                hbox.getChildren().addAll(emailLabel, emailValueLabel, successa4, successa4ValueLabel,two,twoValueLabel,tree,treeValueLabel,errora4,errora4ValueLabel,twoError,twoErrorValueLabel,treeError,treeErrorValueLabel);
                                hbox.setSpacing(10);
                                hbox.setPadding(new Insets(5));

                                setGraphic(hbox);
                            } else {
                                setGraphic(null);
                            }
                        }
                    };
                }
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
                preparedStatement.setString(1,emailFld.getText());
                ResultSet result = preparedStatement.executeQuery();

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("User #" + emailFld.getText() + " was already exist!");
                    alert.showAndWait();
                } else {
                    PreparedStatement preparedStatement1 = connection.prepareStatement(insertData);
                    preparedStatement1.setString(1,emailFld.getText());
                    preparedStatement1.setString(2,passwordFld.getText());
                    preparedStatement1.setString(3, Role.USER.name());
                    preparedStatement1.execute();

                    String getSaveUserId = "SELECT * FROM users WHERE email = ?";
                    PreparedStatement prepared = connection.prepareStatement(getSaveUserId);
                    prepared.setString(1,emailFld.getText());
                    ResultSet res = preparedStatement.executeQuery();
                    Long id  = null;
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
}