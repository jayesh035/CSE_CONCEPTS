import java.io.*;

class Student implements Serializable {


    private String name;

    private  int rollNumber;

    private int age;


    Student(String name,int rollNumber ,int age){
        this.name=name;
        this.rollNumber=rollNumber;
        this.age=age;
    }
    void getInfo()
    {

        System.out.println("Name:"+name);
        System.out.println("RollNumber:"+rollNumber);
        System.out.println("Age:"+age);
    }

}



public  class practical15{


    public static void main(String[] args) {



        Student s1=new Student("jayesh",139,20);
        try {

            ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("student.txt"));
            oos.writeObject(s1);
            System.out.println("Object seralized successfilly");
        } catch (Exception e) {
            System.out.println(e);
        }


        try {


            ObjectInputStream ois=new ObjectInputStream(new FileInputStream("student.txt"));
            Student s=(Student) ois.readObject();


            System.out.println("Object deseralize successfully");
           s.getInfo();
                    }
        catch (Exception e) {
            System.out.println(e);
        }



    }
}