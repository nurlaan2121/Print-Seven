package project.printseven;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnect {
    public static Connection getConnection() {
        try {
            return  DriverManager.getConnection("jdbc:postgresql://localhost:5432/printseven", "postgres", "nurlan21");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
