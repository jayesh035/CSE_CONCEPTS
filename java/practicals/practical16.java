import java.io.File;

public class practical16 {


    static  void listFiles(String path,String extension)
    {


        try {

            File directory=new File(path);
            if(!directory.exists() || !directory.isDirectory())
            {
                System.out.println("Enter correct directory path");
                return ;
            }



            File[] files=  directory.listFiles();

            for(var file: files)
            {

             String fileName=  file.toString();
//                System.out.println(file);

             if(fileName.endsWith(extension))
             {
                 System.out.println(fileName);
             }
            }





        } catch (Exception e) {
            System.out.println(e);
        }


    }

    public static void main(String[] args) {


        String path="/home/jayesh/Desktop/code";
        String extension=".txt";


        listFiles(path,extension);



    }

}