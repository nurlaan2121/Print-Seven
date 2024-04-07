package project.printseven.service;

import project.printseven.DataBaseConnect;
import project.printseven.entities.User;
import project.printseven.enums.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {
    Connection connection = DataBaseConnect.getConnection();

    public User signIn(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String emailRes = resultSet.getString("email");
                String passwordRes = resultSet.getString("password");
                String roleRes = resultSet.getString("role");
                return new User(emailRes, passwordRes, Role.valueOf(roleRes));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
