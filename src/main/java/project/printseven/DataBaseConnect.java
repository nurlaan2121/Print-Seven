package project.printseven;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnect {
    public static Connection getConnection() {
        try {
            return  DriverManager.getConnection("jdbc:postgresql://test.cbskka8e4hce.us-east-1.rds.amazonaws.com:5432/SampleDB", "postgres", "Nurlan21");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
