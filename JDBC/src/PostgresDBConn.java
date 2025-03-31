import java.sql.*;

public class PostgresDBConn {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/postgres"; // Connect to an existing DB first
        String user = "postgres";
        String password = "jayesh";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            System.out.println("Connected to PostgreSQL successfully!");

            // Check PostgreSQL version
            String sql = "SELECT VERSION()";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet res = stmt.executeQuery()) {
                if (res.next()) {
                    System.out.println("PostgreSQL version: " + res.getString(1));
                }
            }

            // Create database
            createDB(user, password);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void createDB(String user, String password) {
        String dbUrl = "jdbc:postgresql://localhost:5432/";

        try (Connection conn = DriverManager.getConnection(dbUrl, user, password);
             Statement stmt = conn.createStatement()) {

            String sql = "CREATE DATABASE \"TestDB\"";
            stmt.executeUpdate(sql);
            System.out.println("Database 'TestDB' created successfully!");

        } catch (SQLException e) {
            if (e.getMessage().contains("already exists")) {
                System.out.println("Database 'TestDB' already exists.");
            } else {
                e.printStackTrace();
            }
        }
    }
}
