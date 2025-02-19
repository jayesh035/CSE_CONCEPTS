import java.util.ArrayList;
import java.util.List;

abstract  class Library{


    String libraryName;
    Library(String libraryName)
    {
        this.libraryName=libraryName;
    }

   abstract void getInfo();

}

class Book extends  Library{

   private  final String bookName;
    private final int bookId;
   private final int numberOfPages;


    Book(String libraryName,String bookName, int bookId, int numberOfPages)
    {
        super(libraryName);
        this.bookName=bookName;
        this.bookId=bookId;
        this.numberOfPages=numberOfPages;
    }

    @Override
    void getInfo() {
        System.out.println("Library Name :"+super.libraryName);
        System.out.println("Library Name :"+bookName);
        System.out.println("Library Name :"+bookId);
        System.out.println("Library Name :"+numberOfPages);
    }

}

class Magazine extends  Library{

  private  final   String magazineName;
  private  final   int magazineId;
  private final    int numberOfPages;
  Magazine(String libraryName, String magazineName, int magazineId,int numberOfPages){

      super(libraryName);
      this.magazineName=magazineName;
      this.magazineId=magazineId;
      this.numberOfPages=numberOfPages;
  }


    @Override
    void getInfo() {
        System.out.println("Library Name :"+super.libraryName);
        System.out.println("Library Name :"+magazineName);
        System.out.println("Library Name :"+magazineId);
        System.out.println("Library Name :"+numberOfPages);
    }
}



class membor extends Practical7 {

    private final String name;
    private final int id;
    private final List<Book>  books;
    private final List<Magazine>  magazines;

    membor(String name,int id )
    {

        this.name=name;
        this.id=id;
        this.books=new ArrayList<>();
        this.magazines=new ArrayList<>();
    }

    @Override
    void Return() {
        super.Return();
    }

    @Override
    void borrow() {
        super.borrow();
    }

    @Override
    void search() {
        super.search();
    }
}



public class Practical7{

    List<Book>books;
    List<Magazine>magzine;

    Practical7(){

        books=new ArrayList<>();
        magzine=new ArrayList<>();
    }

    void borrow()
    {

    }
    void Return()
    {

    }
    void search()
    {

    }
    public static void main(String[] args) {



    }



}