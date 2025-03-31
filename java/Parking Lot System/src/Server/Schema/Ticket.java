package Server.Schema;
import java.time.LocalDateTime;

public class Ticket
{
    private final String id;

    private final LocalDateTime entryTime;

    private LocalDateTime exitTime;

    private final Vehicle vehicle;

    private final ParkingSpot parkingSpot;

    // Ticket created with current entry time.
    public Ticket(String id, Vehicle vehicle, ParkingSpot parkingSpot)
    {

        this.id = id;

        this.vehicle = vehicle;

        this.parkingSpot = parkingSpot;

        this.entryTime = LocalDateTime.now();

    }

    // Record exit time.
    public void closeTicket()
    {
        if (exitTime == null)
        {
            // Only set exit time if it's not already set
            this.exitTime = LocalDateTime.now();

        }

    }

    public LocalDateTime getEntryTime()
    {
        return entryTime;
    }

    public LocalDateTime getExitTime()
    {
        return exitTime;
    }

    public Vehicle getVehicle()
    {
        return vehicle;
    }

    @Override
    public String toString()
    {
        return "Ticket{" +
                "\nid='" + id + '\'' +
                "\nentryTime=" + entryTime +
                "\nexitTime=" + exitTime +
                "\nvehicle=" + vehicle +
                "\nparkingSpot=" + parkingSpot +
                '}';
    }
}
