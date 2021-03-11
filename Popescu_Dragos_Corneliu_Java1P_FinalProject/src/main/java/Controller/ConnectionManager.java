package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static final class Singleton {
        public static final ConnectionManager INSTANCE = new ConnectionManager();
    }
    private final Connection connection;
    private ConnectionManager() {
        String url = "jdbc:mysql://localhost:3306/proiect";
        try {
            connection = DriverManager.getConnection(url, "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public static ConnectionManager getInstance() {
        return Singleton.INSTANCE;
    }

    public Connection getConnection() {
        return connection;
    }

}
