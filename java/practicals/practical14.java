import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class practical14 {

    public static void main(String[] args) {


        try{

            BufferedReader br=new BufferedReader(new FileReader("log.txt"));
            String line;
            while((line=br.readLine())!=null)
            {
                String [] str=line.trim().split("\\s+");

                System.out.println("Number of words in line: "+ str.length);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

}