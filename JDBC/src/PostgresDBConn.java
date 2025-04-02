import java.sql.*;

public class PostgresDBConn {

    public static void main(String[] args) {

    Connection connection=    connectToDB("TestDB");

            // Create database
//            createDB(user, password);

    }

    static  Connection connectToDB(String databaseName)
    {
        String url = "jdbc:postgresql://localhost:5432/"+databaseName; // Connect to an existing DB first
        String user = "postgres";
        String password = "jayesh";
        Connection conn ;
        try
        {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to PostgreSQL successfully!");

            // Check PostgreSQL version
//            String sql = "SELECT VERSION()";
//            try (PreparedStatement stmt = conn.prepareStatement(sql);
//                 ResultSet res = stmt.executeQuery()) {
//                if (res.next()) {
//                    System.out.println("PostgreSQL version: " + res.getString(1));
//                }
//            }
            return  conn;
        } catch (SQLException e) {
            e.printStackTrace();

            return null;
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
