import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class BatchProcessing
{
    public static void main(String[] args)
    {
        Connection connection=PostgresDBConn.connectToDB("TestDB");


        ObjectMapper objectMapper=new ObjectMapper();

        ObjectNode jsonNodes=objectMapper.createObjectNode();


        String sql="INSERT INTO vehicles(licensenumber,type) VALUES(?,?)";

        Random random=new Random();
        String [] types={"BIKE","CAR","TRUCK"};

        try {
            assert connection != null;

            PreparedStatement preparedStatement=connection.prepareStatement(sql);

            int counter=0;
            for(int i=0;i<10;i++)
            {
//            long licenseNumber=System.currentTimeMillis();
            counter++;
            String type= types[random.nextInt(3)];

            preparedStatement.setString(1,Long.toString(System.currentTimeMillis()));
            preparedStatement.setString(2,type);
            preparedStatement.addBatch();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        int[] rowsAffected=    preparedStatement.executeBatch();
            if(rowsAffected.length>0)
            {
                System.out.println(rowsAffected.length+ " rows added successfully");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for(int i=0;i<10;i++)
        {

        }
//        random.nextInt(3)


    }
}
