
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandling {

public static void main(String[] args) {
    

    // File file=new File("file.txt");

        try {

         
            // file.createNewFile();
        
            
            //for writing file

            FileWriter writer=new FileWriter("file.txt",true);

            writer.append("hiii my name is jayesh\n");
            writer.close();

            
        } catch (IOException e) {
            // TODO: handle exception
            System.out.println(e);
        }
     

}   

}