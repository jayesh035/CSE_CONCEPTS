public class ParkingSpot {


   private final String id;
   private Vehicle vehicle;
   private boolean isAvailable;

    public String getId() {
        return id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public ParkingSpot(String id, Vehicle vehicle, boolean isAvailable) {
        this.id = id;
        this.vehicle = vehicle;
        this.isAvailable = isAvailable;
    }

    public ParkingSpot(String id, boolean isAvailable) {
        this.id = id;
        this.isAvailable = isAvailable;
    }

    void assignVehicle(Vehicle vehicle)
    {

        this.vehicle=vehicle;

    }

    void releaseSpot()
    {
        this.vehicle.setLicenseNumber("");
    }
}
