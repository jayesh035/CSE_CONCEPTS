public class TicketBookingSystem {



    boolean [][]avaliableSeats;

    TicketBookingSystem(int n,int m)
    {
        avaliableSeats=new boolean[n][m];
    }


   synchronized boolean bookTickets(int[] seats)
    {
        if(seats.length==0)
        {
            return  false;
        }

        for(int i=0;i<seats.length;i++){

            int x=(seats[i]/avaliableSeats[0].length);
            if(seats[i]%avaliableSeats[0].length ==0)
            {
                x--;
            }
            int y=seats[i]-(x*avaliableSeats[0].length)-1;

            if(avaliableSeats[x][y])
            {
                return  false;
            }
            else
            {
                avaliableSeats[x][y]=true;
            }
        }

        return true;
    }

    void displaySeats()
    {

        for(int i=0;i<avaliableSeats.length;i++)
        {
            for(int j=0;j<avaliableSeats[0].length;j++){

//                System.out.print(avaliableSeats[i][j]);
                if(avaliableSeats[i][j])
                {
                    System.out.print("X   ");
                }
                else {
                    System.out.print(((avaliableSeats[0].length*i)+j+1)+"   ");
                }

            }
            System.out.println();
        }
    }




}



