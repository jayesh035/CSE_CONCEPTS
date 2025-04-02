import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionProcessing
{
    public static void main(String[] args)
    {

      Connection connection= PostgresDBConn.connectToDB("");
        PreparedStatement stmt1=null,stmt2=null;
        try
        {
            assert connection != null;

            connection.setAutoCommit(false);

            String deductMoney = "UPDATE accounts SET balance = balance - 500 WHERE id = 1";
            stmt1 = connection.prepareStatement(deductMoney);
            stmt1.executeUpdate();

            String addMoney = "UPDATE accounts SET balance = balance + 500 WHERE id = 1";
            stmt2 = connection.prepareStatement(addMoney);
            stmt2.executeUpdate();

            // Step 5: Commit the Transaction (if all queries succeed)
            connection.commit();
            System.out.println("Transaction Successful!");
        }
        catch (Exception e)
        {
            try
            {
                connection.rollback();
                System.out.println("Transaction failed! Rollback");
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(stmt1!=null)stmt1.close();
                if(stmt2!=null)stmt2.close();
                if(connection!=null)connection.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
}
