import java.util.*;

class Pair{


    int first,second;

   public Pair(int first,int second)
    {
        this.first=first;
        this.second=second;
    }
}

class List
{


    ArrayList<Pair> list;

    public List()
    {
        this.list=new ArrayList<>();

    }
    
}
 class test1
{


    static public void main(String...args) {
        

        System.out.println("This is from test 1");


/*
        int n=10;

        List G[] = new List[10];


        for(int i=0;i<n;i++)
        {
            G[i]= new List();
        }


        int m=5;
        Scanner sc=new Scanner(System.in);
        while (m>0) {
            int x,y;
            x=sc.nextInt();
            y=sc.nextInt();
            m--;
            if ( x> n-1 || y> n-1)
            {
                continue;
            }
            G[x].list.add(new Pair(y, 0));
            G[y].list.add(new Pair(x,0));

        }


        for(int i=0;i<n;i++)
        {
            System.out.println("Node "+ i);
            for (Pair ch : G[i].list)

            {

                System.out.println(ch.first+" "+ch.second);

            }

            System.out.println();
        }

 */

        
    }




    
}

class test2
{

    protected void m()
    {

    }
    public static void main(String[] args) {

        System.out.println("this is from test2");
        

    }
}