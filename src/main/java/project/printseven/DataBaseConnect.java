package project.printseven;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnect {
    public static Connection getConnection() {

        try {
            return  DriverManager.getConnection("jdbc:postgresql://ep-still-bar-a5d9uu3u.us-east-2.aws.neon.tech:5432/simple", "simple_owner", "CcByfEWNuw24");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
