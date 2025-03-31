import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PostgresDBConn {


    public static void main(String[] args) {

        String url="jdbc:postgresql://localhost:5432/TestDB";
        String user="user";
        String password="password";

        try{

            Connection conn= DriverManager.getConnection(url,user,password);

            String sql="SELECT VERSION()";

            PreparedStatement stmt= conn.prepareStatement(sql);
            ResultSet res= stmt.executeQuery();
            while (res.next())
            {
                System.out.println("Postgresql version: "+ res.getString(1));
            }
            stmt.close();
            res.close();
            conn.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
