package project.printseven.service;

import project.printseven.DataBaseConnect;
import project.printseven.dto.PhotoRes;
import project.printseven.dto.UserRes;
import project.printseven.entities.User;
import project.printseven.entities.models.AnswerPShell;
import project.printseven.enums.Role;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserService {
    Connection connection = DataBaseConnect.getConnection();

    public List<UserRes> getAllUsers(Long currentAdminId) {
        String sql = " SELECT u.email,u.id," +
                "    SUM(CASE WHEN p.success = true AND p.size = 4 THEN 1 ELSE 0 END) AS successA4,\n" +
                "    SUM(CASE WHEN p.success = true AND p.size = 5 THEN 1 ELSE 0 END) AS successA5,\n" +
                "    SUM(CASE WHEN p.success = true AND p.size = 6 THEN 1 ELSE 0 END) AS successA6,\n" +
                "    SUM(CASE WHEN p.success = false AND p.size = 4 THEN 1 ELSE 0 END) AS errorA4,\n" +
                "    SUM(CASE WHEN p.success = false AND p.size = 5 THEN 1 ELSE 0 END) AS errorA5,\n" +
                "    SUM(CASE WHEN p.success = false AND p.size = 6 THEN 1 ELSE 0 END) AS errorA6\n" +
                " FROM users u " +
                "LEFT JOIN photos p ON u.id = p.user_id LEFT JOIN users_users uu on users_id = u.id WHERE uu.user_id = " + currentAdminId + " GROUP BY u.id,u.email";

        List<UserRes> users = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String email = resultSet.getString("email");
                int successA4 = resultSet.getInt("successA4");
                int successA5 = resultSet.getInt("successA5");
                int successA6 = resultSet.getInt("successA6");
                int errorA4 = resultSet.getInt("errorA4");
                int errorA5 = resultSet.getInt("errorA5");
                int errorA6 = resultSet.getInt("errorA6");

                users.add(new UserRes(id,email, (long) successA4, (long) successA5, (long)successA6,(long) errorA4,(long) errorA5, (long)errorA6));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;

    }

    public User signIn(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long idRes = resultSet.getLong("id");
                String emailRes = resultSet.getString("email");
                String passwordRes = resultSet.getString("password");
                String roleRes = resultSet.getString("role");
                return new User(idRes, emailRes, passwordRes, Role.valueOf(roleRes));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void test(String word) {
        String insert = "INSERT INTO users (email, password, role) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setString(1, word);
            preparedStatement.setString(2, word);
            preparedStatement.setString(3, "USER");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addPhoto(String urlImage, String paperSize, AnswerPShell answerPShell) {
        String insert = "INSERT INTO photos (created_ad, size, image_url,success,user_id) " +
                "VALUES (?,?,?,?,?)";
        try {
            int size = 0;
            if (paperSize.contains("A4")) {
                size = 4;
            } else if (paperSize.contains("A5")) {
                size = 5;
            } else if (paperSize.contains("A6")) {
                size = 6;
            } else {
                size = 1;
            }
            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setObject(1, answerPShell.getSubmittedTime());
            preparedStatement.setInt(2, size);
            preparedStatement.setString(3, urlImage);
            preparedStatement.setBoolean(4, true);
            preparedStatement.setLong(5, getIdInFile());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Long getIdInFile() {
        String filePath = "C:\\Users\\user\\Desktop\\ValuesForPrintContoller\\values.properties";

        // Создание объекта Properties
        Properties properties = new Properties();
        String value1;
        // Чтение файла
        try (FileInputStream input = new FileInputStream(filePath)) {
            properties.load(input);
            // Получение значений по ключам
            value1 = properties.getProperty("id");
            // Вывод значений
            System.out.println("Значение для key1: " + value1);
            return Long.valueOf(value1);
        } catch (IOException io) {
            io.printStackTrace();
        }
        return null;
    }

    public List<PhotoRes> getPhotosByUserId(Long userId) {
        String sql = " SELECT *" +
                    " FROM photos p " +
                "WHERE p.user_id = " + userId;

        List<PhotoRes> photoRes = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Timestamp createdAdTimestamp = resultSet.getTimestamp("created_ad");
                ZonedDateTime createdAdZonedDateTime = createdAdTimestamp.toInstant().atZone(ZoneId.systemDefault());
                int size = resultSet.getInt("size");
                String imageUrl = resultSet.getString("image_url");
                boolean success = resultSet.getBoolean("success");
                photoRes.add(new PhotoRes(id, createdAdZonedDateTime, getSizeInInt(size), imageUrl, getSuccess(success)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return photoRes;
    }
    private String getSizeInInt(int size){
        if (size == 4){
            return "A4";
        }if (size == 5){
            return "A5";
        }if (size == 6) {
            return "A6";
        }
        return "A1";
    }
    private String getSuccess(boolean success){
       if (success){
           return "Success";
       }return "ERROR";
    }

}
