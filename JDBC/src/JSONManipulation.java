import java.sql.*;

public class JSONManipulation {



    public static void main(String[] args)
    {
      try(Connection connection= PostgresDBConn.connectToDB("TestDB"))
      {
          assert connection != null;
//          insertData(connection);
          queryDB(connection);


      }
      catch (Exception e)
      {
          throw new RuntimeException(e);
      }
    }

    static  void insertData(Connection connection) throws SQLException
    {
            String sql="INSERT INTO users(name , details) VALUES(? , ?::jsonb)";

            PreparedStatement statement=connection.prepareStatement(sql);

            statement.setString(1,"kevin");

            statement.setString(2,"{\"age\": 19, \"city\": \"Ahmedabad\"}");

            statement.executeUpdate();
String str = """
        """;
            System.out.println("data inserted successfully");

    }



        static void queryDB(Connection connection) throws SQLException
        {
            String sql="SELECT * FROM users";

            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(sql);

            while (resultSet.next())
            {
                System.out.println(resultSet.getString(1));
                System.out.println(resultSet.getString(2));
                System.out.println(resultSet.getString(3));
            }


        }
}



