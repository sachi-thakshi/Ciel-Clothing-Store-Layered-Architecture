package lk.ijse.gdse.cielclothingstore.db;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
public class DBConnection {
    private static DBConnection instance;

    private final Connection connection;

    private DBConnection() throws SQLException {
        String URL = "jdbc:mysql://localhost:3306/Ciel_Clothing_Store";
        String USER = "root";
        String PASSWORD = "Ijse@1234";
        connection = DriverManager.getConnection(URL,USER, PASSWORD);
    }

    public static DBConnection getInstance() throws SQLException {
        return instance == null ? instance = new DBConnection() : instance;
    }
}
