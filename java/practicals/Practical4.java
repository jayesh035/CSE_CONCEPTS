import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Practical4 {
    public static void main(String[] args) {
    
        
        try {

            //read data from input file using file input stream
            FileInputStream file=new FileInputStream("/home/jayesh/Desktop/code/Concepts/input.txt");

                
                StringBuilder str=new StringBuilder();
                int totalWords=0;
                boolean inWord=false;
                while(true)
                {
                    int i=file.read();
                    if(i== -1)
                    {
                        break;
                    }
                        char c=(char)i;
                    str.append(c);
                    
                    if(c!= ' ' &&  c!='\n')
                    {

                        if(inWord==false)
                        {
                            totalWords++;
                            inWord=true;
                        }

                        
                        
                    }
                    else{
                        inWord=false;
                    }
                }
               
 
                System.out.println("total words: "+totalWords);

                //write data of output.txt file using fileoutput stream

                
                // String data=str.toString();
                FileOutputStream fileOut=new FileOutputStream("output.txt");
                // fileOut.write(data.getBytes());
                for(int i=0;i<str.length();i++)
                {
                
                    fileOut.write(str.charAt(i));
                }
                fileOut.close();



            
        } catch (FileNotFoundException e) {
            // TODO: handle exception
            System.out.println(e);
        }
        catch (IOException e)
        {

        }

    }    
}


// public class WordCount {
//     public static void main(String[] args) {
//         String text = "hiii my name is jayeshhiii my name is jayeshhiii my name is jayeshhiii my name is jayeshhiii my name is jayeshhiii my name is jayeshhiii my name is jayeshhiii my name is jayeshhiii my name is jayeshhiii my name is jayesh "
//                     + "hiii my name is jayesh "
//                     + "hiii my name is jayesh "
//                     + "hiii my name is jayesh "
//                     + "hiii my name is jayesh "
//                     + "hiii my name is jayesh "
//                     + "hiii my name is jayesh "
//                     + "hiii my name is jayesh "
//                     + "hiii my name is jayesh "
//                     + "hiii my name is jayesh "
//                     + "hiii my name is jayesh "
//                     + "hiii my name is jayesh "
//                     + "hiii my name is jayesh "
//                     + "hiii my name is jayesh "
//                     + "hiii my name is jayesh "
//                     + "hiii my name is jayesh "
//                     + "hiii my name is jayesh "
//                     + "hiii my name is jayesh "
//                     + "hiii my name is jayesh "
//                     + "hiii my name is jayesh "
//                     + "hiii my name is jayesh "
//                     + "hiii my name is jayesh ";

//         // Split text into words using space as a delimiter
//         String[] words = text.trim().split("\\s+");

//         // Print the total word count
//         System.out.println("Total number of words: " + words.length);
//     }
// }
