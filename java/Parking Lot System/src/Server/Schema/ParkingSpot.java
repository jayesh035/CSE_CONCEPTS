package Server.Schema;
import Util.Constants;

public class ParkingSpot
{
    //To uniquely identify each parking spot
    private final String id;

    //Set parking spot for specific vehicle
    private final Vehicle vehicle;

    //For checking spot is available of not
    private boolean isAvailable;

    public ParkingSpot(String id, Vehicle vehicle, boolean isAvailable)
    {
        this.id = id;

        this.vehicle = vehicle;

        this.isAvailable = isAvailable;
    }

    public String getId()
    {
        return id;
    }

    public Vehicle getVehicle()
    {
        return vehicle;
    }

    public boolean isAvailable()
    {
        return isAvailable;
    }

    public void setAvailable(boolean available)
    {
        isAvailable = available;
    }

   public void assignVehicle(String license)
    {
        //set license number for this particular vehicle
        this.vehicle.setLicenseNumber(license);
    }

    public void releaseSpot()
    {
        if (this.vehicle != null)
        {
            this.vehicle.setLicenseNumber(Constants.emptyFlag); // Just reset the license
        }
        this.isAvailable = true;
    }


    @Override
    public String toString()
    {
        return "ParkingSpot{" +
                "id='" + id + '\'' +
                ", vehicle=" + vehicle +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
