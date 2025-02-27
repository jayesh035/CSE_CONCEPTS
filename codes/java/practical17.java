import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public  class practical17 {


    public static void main(String[] args) {


        String text="sdkufiudsgjfskdnvfdslkbvngdlkb sdgfdhfgjfgb gdfgrdf\n";
        try {


            FileWriter file=new FileWriter("log.txt",true);

            for(int i=0;i<text.length();i++)
            {

                file.append(text.charAt(i));
            }

            file.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}