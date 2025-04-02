import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PreparedStatements
{
    public static void main(String[] args)
    {
        Connection connection=
                PostgresDBConn.connectToDB("TestDB");



        String sql="INSERT INTO vehicles(licensenumber, type) VALUES(?,?)";

        try {
            assert connection != null;
            PreparedStatement preparedStatement=connection.prepareStatement(sql);

            preparedStatement.setString(1,"GJ11AB5405");
            preparedStatement.setString(2,"BIKE");
           int numAffectedRows = preparedStatement.executeUpdate();

           if(numAffectedRows>0)
           {
               System.out.println("Data inserted successfully");
           }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
