package project.printseven.service;

import project.printseven.DataBaseConnect;
import project.printseven.HelloController;
import project.printseven.dto.UserRes;
import project.printseven.entities.User;
import project.printseven.entities.models.AnswerPShell;
import project.printseven.enums.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    Connection connection = DataBaseConnect.getConnection();

    public List<UserRes> getAllUsers(Long currentAdminId) {
        String sql = "SELECT u.email, " +
                "    SUM(CASE WHEN p.success = true AND p.size = 4 THEN 1 ELSE 0 END) AS successA4,\n" +
                "    SUM(CASE WHEN p.success = true AND p.size = 5 THEN 1 ELSE 0 END) AS successA5,\n" +
                "    SUM(CASE WHEN p.success = true AND p.size = 6 THEN 1 ELSE 0 END) AS successA6,\n" +
                "    SUM(CASE WHEN p.success = false AND p.size = 4 THEN 1 ELSE 0 END) AS errorA4,\n" +
                "    SUM(CASE WHEN p.success = false AND p.size = 5 THEN 1 ELSE 0 END) AS errorA5,\n" +
                "    SUM(CASE WHEN p.success = false AND p.size = 6 THEN 1 ELSE 0 END) AS errorA6\n" +
                "FROM users u " +
                "LEFT JOIN photos p ON u.id = p.user_id LEFT JOIN users_users uu on users_id = u.id WHERE uu.user_id = "+currentAdminId +" GROUP BY u.email";


        List<UserRes> users = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String email = resultSet.getString("email");
                int successA4 = resultSet.getInt("successA4");
                int successA5 = resultSet.getInt("successA5");
                int successA6 = resultSet.getInt("successA6");
                int errorA4 = resultSet.getInt("errorA4");
                int errorA5 = resultSet.getInt("errorA5");
                int errorA6 = resultSet.getInt("errorA6");

//                users.add(new UserRes(email, (long) successA4, (long) successA5, (long)successA6,(long) errorA4,(long) errorA5, (long)errorA6));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
    public void test(String word){
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
            if (paperSize.contains("A4")){
                size = 4;
            }else if (paperSize.contains("A5")){
                size = 5;
            }else if (paperSize.contains("A6")){
                size = 6;
            }else {
                size =1;
            }
            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setObject(1, answerPShell.getSubmittedTime());
            preparedStatement.setInt(2,size);
            preparedStatement.setString(3, urlImage);
            preparedStatement.setBoolean(4, true);
            preparedStatement.setLong(5, HelloController.currentUser.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
