package project.printseven.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import project.printseven.entities.models.AnswerPShell;
import project.printseven.service.GooglePhotoService;
import project.printseven.service.PrintExample;
import project.printseven.service.UserService;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public class UserPage {
    private UserService userService = new UserService();
    public PrintExample printExample = new PrintExample();
    private GooglePhotoService googlePhotoService = new GooglePhotoService();
    private double x = 0;
    private double y = 0;
    @FXML
    private Button close;

    @FXML
    private Button currenttusk_btn;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button minimize;

    @FXML
    private Button printers_btn;

    @FXML
    private Button studentGrade_btn;

    @FXML
    private Button tasks_btn;

    @FXML
    private Label username;

    @FXML
    void close() {
        System.exit(0);
    }

    @FXML
    void logout() {
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

    @FXML
    void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
//                String getQueue = "Get-PrintJob -PrinterName 'HP LaserJet MFP M28-M31'";
    }

    @FXML
    void switchForm(ActionEvent event) {
        if (event.getSource() == printers_btn) {
            System.out.println(printExample.getAllConnectionPrinters());
        } else if (event.getSource() == currenttusk_btn) {
            System.out.println("Current Task");

        }
    }

    public void start() {
        ProcessBuilder processBuilder = new ProcessBuilder("powershell.exe",
                "-ExecutionPolicy", "Bypass", "-Command",
                "$OutputEncoding = [System.Console]::OutputEncoding = [System.Text.Encoding]::UTF8; Get-PrintJob -PrinterName 'HP LaserJet MFP M28-M31' | Format-List");
        processBuilder.redirectErrorStream(true);
        try {
            Process process = processBuilder.start();
            List<AnswerPShell> answerPShells = getStringStringMap(process);
            System.out.println("PowerShell output:");
            System.out.println("answerPShells.size() = " + answerPShells.size());
            for (AnswerPShell answerPShell : answerPShells) {
                String namePhoto = answerPShell.getNamePhoto();
                System.out.println("namePhoto = " + namePhoto);
                String path = null;
                if (namePhoto.contains(".psd")) {
                    long second = 1L;
                    while (path == null) {
                        System.out.println("orig time = " + answerPShell.getSubmittedTime());

                        // Получаем модифицированное время с добавлением секунд
                        LocalDateTime modifiedTime = answerPShell.getSubmittedTime().plusSeconds(second);
                        System.out.println("addedOneSec = " + modifiedTime);

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd=MM=uuuu HH=mm=ss");
                        // Форматируем модифицированную дату и время
                        String formattedDateTime = modifiedTime.format(formatter);
                        System.out.println("Форматированная дата и время: " + formattedDateTime);
                        path = findPathByName("Photo " + formattedDateTime + ".jpg");
                        second++;
                    }
                } else {
                    path = findPathByName(namePhoto + ".jpg");
                }
                assert path != null;
                File file = new File(path);
                String urlImage = googlePhotoService.uploadImageToDrive(file);
                String paperSize = getPaperSize(answerPShell.getId());
                System.out.println("PAPERSIZE: " + paperSize);
                System.out.println("PATH:  " + path);
                userService.addPhoto(urlImage, paperSize, answerPShell);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<AnswerPShell> getStringStringMap(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));

        // Читаем ответ PowerShell и помещаем его в Map
        String line;
        List<AnswerPShell> answers = new ArrayList<>();
        for (int i = 0; i < queuePrint("HP LaserJet MFP M28-M31"); i++) {
            AnswerPShell answerPShell = new AnswerPShell();
            while ((line = reader.readLine()) != null) {
                if (line.contains("SubmittedTime")) {
                    String[] date = line.split(" : ");
                    LocalDateTime parse = LocalDateTime.parse(date[1], DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
                    answerPShell.setSubmittedTime(parse);
                }
                // Предполагаем, что ответ имеет формат "ключ: значение"
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    if (key.equalsIgnoreCase("Id")) {
                        answerPShell.setId(Long.valueOf(value));
                    } else if (key.equalsIgnoreCase("ComputerName")) {
                        answerPShell.setComputerName(value);
                    } else if (key.equalsIgnoreCase("PrinterName")) {
                        answerPShell.setPrinterName(value);
                    } else if (key.equalsIgnoreCase("UserName")) {
                        answerPShell.setUser(value);
                    } else if (key.equalsIgnoreCase("DocumentName")) {
                        answerPShell.setNamePhoto(value);
                    } else if (key.equalsIgnoreCase("Datatype")) {
                        answerPShell.setDateType(value);
                    } else if (key.equalsIgnoreCase("Priority")) {
                        answerPShell.setPriority(Integer.parseInt(value));
                    } else if (key.equalsIgnoreCase("Position")) {
                        answerPShell.setPosition(Integer.parseInt(value));
                    } else if (key.equalsIgnoreCase("Size")) {
                        answerPShell.setSize(Long.valueOf(value));
                    } else if (key.equalsIgnoreCase("JobTime")) {
                        answerPShell.setSecondJobTIme(Integer.parseInt(value));
                    } else if (key.equalsIgnoreCase("PagesPrinted")) {
                        answerPShell.setPagesPrinted(Integer.parseInt(value));
                    } else if (key.equalsIgnoreCase("TotalPages")) {
                        answerPShell.setTotalPages(Integer.parseInt(value));
                    } else if (key.equalsIgnoreCase("JobStatus")) {
                        answerPShell.setJobStatus(value);
                    }
                }
            }
            answers.add(answerPShell);
        }
        return answers;
    }

    private static long queuePrint(String printerName) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("powershell.exe",
                "-ExecutionPolicy", "Bypass", "-Command",
                "$OutputEncoding = [System.Console]::OutputEncoding = [System.Text.Encoding]::UTF8; Get-PrintJob -PrinterName '" + printerName + "' | Measure-Object | Select-Object -ExpandProperty Count");
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
            return Long.parseLong(reader.readLine().trim());
        }
    }

    private static String getPaperSize(Long taskId) throws IOException {
        String query = "Get-WmiObject -Class Win32_PrintJob | Where-Object { $_.JobId -eq " + taskId + " } | Select-Object -ExpandProperty PaperSize";
        ProcessBuilder processBuilder = new ProcessBuilder("powershell.exe", "-Command", query);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    return line;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Null";
    }


    public static String findPathByName(String name) throws IOException {
        System.out.println("NAME: " + name);
        List<Path> foundPaths = new ArrayList<>();
        Path startDir = Paths.get("C:\\Users\\user\\Desktop");

        Files.walkFileTree(startDir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.getFileName().toString().equals(name)) {
                    foundPaths.add(file); // Добавить найденный файл в список
                }
                return FileVisitResult.CONTINUE; // Продолжить обход файлов
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE; // Продолжить обход, если произошла ошибка при доступе к файлу
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                return FileVisitResult.CONTINUE; // Обрабатываем содержимое директории
            }
        });

        if (!foundPaths.isEmpty()) {
            System.out.println("NOT EMPTY!!!");
            return foundPaths.getFirst().toString();
        }
        return null;
    }

}

