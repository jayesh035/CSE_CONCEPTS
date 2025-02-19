abstract  class Vehicle{


   abstract void start();

   abstract void stop();
}

class Bike extends  Vehicle{



   private boolean isOn;

    @Override
    void start() {
    isOn=true;
        System.out.println("Bike has been started");
    }

    @Override
    void stop() {
    isOn=false;
        System.out.println("bike has been stopped");

    }
}

class Car extends  Vehicle{
    private boolean isOn;
    @Override
    void start() {
    isOn=true;
        System.out.println("car has been started");

    }

    @Override
    void stop() {

        isOn=false;
        System.out.println("car has been stopped");

    }
}





public class  practical9
{

    public static void main(String[] args) {


        Vehicle bike=new Bike();

        Vehicle car=new Car();


        bike.start();
        car.start();
        bike.stop();
        car.stop();





    }
}