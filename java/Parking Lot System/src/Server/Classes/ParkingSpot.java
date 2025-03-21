package Server.Classes;

public class ParkingSpot {


    //To uniquely identify each parking spot
    private final String id;

    //Set parking spot for specific vehicle
    private Vehicle vehicle;

    //For checking spot is available of not
    private boolean isAvailable;

    public ParkingSpot(String id, Vehicle vehicle, boolean isAvailable)
    {

        this.id = id;

        this.vehicle = vehicle;

        this.isAvailable = isAvailable;

    }

    public ParkingSpot(String id, boolean isAvailable)
    {
        this.id = id;

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

    public void setVehicle(Vehicle vehicle)
    {
        this.vehicle = vehicle;
    }

    public void setAvailable(boolean available)
    {
        isAvailable = available;
    }

    void assignVehicle(String license)
    {
        this.vehicle.setLicenseNumber(license);
    }

    void releaseSpot()
    {

        // Ensure vehicle is not null before setting to null
        if (this.vehicle != null)
        {

            this.vehicle = null;

        }

        // Mark spot as available
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
