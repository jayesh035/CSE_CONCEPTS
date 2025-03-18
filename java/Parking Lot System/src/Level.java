import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;



public class Level {

    public ConcurrentHashMap<String, ParkingSpot> getSpots() {
        return Spots;
    }

    public String getLevelNumber() {
        return levelNumber;
    }

    Level(int levelNumber)
{
    Spots=new ConcurrentHashMap<>();
    this.levelNumber=Integer.toString( levelNumber);


    String fileName = "parking_spots.csv"; // File to read

    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
        String line;
        boolean isFirstLine = true; // To skip the header

        while ((line = br.readLine()) != null) {
            // Skip the first line (CSV Header)
            if (isFirstLine) {
                isFirstLine = false;
                continue;
            }

            // Split line by comma
            String[] parts = line.split(",");


            if (parts.length == 5 ) {

                String currentLevel=parts[4].trim();
                if(currentLevel.equals(this.levelNumber))
                {
                    String spotId = parts[0].trim();
                    String status = parts[1].trim();
                    boolean isAvailable=false;
                    if(status.equalsIgnoreCase("true"))
                    {
                        isAvailable=true;
                    }
                    String vehicleLicenseNumber=parts[2];
                    String vehicleType=parts[3].trim();


                        Vehicle vehicle=new Vehicle(vehicleLicenseNumber,vehicleType);

                        ParkingSpot spot=new ParkingSpot(spotId,
                                vehicle,isAvailable);


                        Spots.put(spot.getId(),spot);


                }

            }
        }
    } catch (IOException e) {
        System.out.println(e);
    }

}

  private   String levelNumber;


   private ConcurrentHashMap<String, ParkingSpot>Spots;



    ParkingSpot findSpot(Vehicle vehicle)
    {


     for(String key:Spots.keySet())
     {
         ParkingSpot spot=Spots.get(key);
         Vehicle currentVehicle=spot.getVehicle();

      if(spot.isAvailable() && currentVehicle.getType().equalsIgnoreCase(vehicle.getType()))
      {

            return spot;
      }



     }


        return null;
    }


}
