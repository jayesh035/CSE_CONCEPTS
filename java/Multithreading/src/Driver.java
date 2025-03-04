import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Driver {


    public static void main(String[] args) throws InterruptedException {


        TicketBookingSystem system=new TicketBookingSystem(5,7);


        system.displaySeats();

        System.out.println();
        System.out.println();


//        ExecutorService threds= Executors.newFixedThreadPool(5);
//
//        threds.submit(()->{
//           system.
//        });

        Thread t1=new Thread(()->{
//           system.displaySeats();
            boolean result=  system.bookTickets(new int[]{1,2,3,4});
            if(result)
            {
                System.out.println("Ticket Booked successfully");
            }
            else
            {
                System.out.println("Sorry! Booking is unsuccessfull");
            }

        });
        Thread t2=new Thread(()->{
//           system.displaySeats();
            boolean result=  system.bookTickets(new int[]{33,34});
            if(result)
            {
                System.out.println("Ticket Booked successfully");
            }
            else
            {
                System.out.println("Sorry! Booking is unsuccessfull");
            }
        });

        Thread t3=new Thread(()->{
//           system.displaySeats();
          boolean result=  system.bookTickets(new int[]{33,34,35,27});
            if(result)
            {
                System.out.println("Ticket Booked successfully");
            }
            else
            {
                System.out.println("Sorry! Booking is unsuccessfull");
            }
        });


        t1.start();
        t2.start();
        t3.start();


        t1.join();
        t2.join();
        t3.join();


        System.out.println();
        System.out.println();
        system.displaySeats();
    }
}
