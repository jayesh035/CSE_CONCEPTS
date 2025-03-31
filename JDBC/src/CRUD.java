import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CRUD {

    private static final String url="jdbc:postgresql://localhost:5432/TestDB";
    private static final String user="postgres";
    private static final String password="jayesh";
    private static  Connection connection;

    public static void main(String[] args) {

    connectToDB();

    createTables();


    insertData();


    }


    static void connectToDB()
    {

        try {
            CRUD.connection= DriverManager.getConnection(url,user,password);

            System.out.println("Connected to Database");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static  void createTables()
    {
        try {

            Statement statement=CRUD.connection.createStatement();

            String parkingSpots="CREATE TABLE IF NOT EXISTS ParkingSpots(" +
                   "id varchar PRIMARY KEY, " +
                    "level int NOT NULL, "+
                    "isAvaliable BOOLEAN DEFAULT TRUE"+")";

            statement.executeUpdate(parkingSpots);
            System.out.println("parkingSpots Table created successfully");

            String createVehicles="CREATE TABLE IF NOT EXISTS Vehicles(" +
                    "licenseNumber varchar PRIMARY KEY, "+
                    "type varchar(10) NOT NULL"+")";
            statement.executeUpdate(createVehicles);
            System.out.println("Vehicle table created successfully");

            String createTickets="CREATE TABLE IF NOT EXISTS Tickets("
                    + "id VARCHAR(20) PRIMARY KEY, "
                    + "entryTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                    + "exitTime TIMESTAMP NULL, "
                    + "vehicleLicense VARCHAR(20), "
                    + "parkingSpotId VARCHAR(10), "
                    + "FOREIGN KEY (vehicleLicense) REFERENCES Vehicles(licenseNumber), "
                    + "FOREIGN KEY (parkingSpotId) REFERENCES ParkingSpots(id)"
                    + ")";
            statement.executeUpdate(createTickets);
            System.out.println("Tickets table created.");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static  void insertData()
    {
        try {
            Statement statement=CRUD.connection.createStatement();

            for(int i=1;i<=4;i++)
            {
                for(int j=1;j<=15;j++)
                {
                    String id= j+"SL"+i;
                    String query="INSERT INTO parkingSpots(id, level) VALUES('"
                            + id+ "', "+ i + ")";

                    statement.executeUpdate(query);

                    System.out.println("Row added successfully");

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
