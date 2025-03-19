
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ParkingLot {
    private static final Logger logger = LoggerFactory.getLogger(ParkingLot.class);

    // List of levels in the parking lot
    private CopyOnWriteArrayList<Level> levels;

    // Store active tickets keyed by ParkingSpot ID
    private ConcurrentHashMap<String, Ticket> activeTickets;

    public ParkingLot() {
        levels = new CopyOnWriteArrayList<>();
        activeTickets = new ConcurrentHashMap<>();
        // Assume the parking lot has 4 levels.
        for (int i = 1; i <= 4; i++) {
            levels.add(new Level(i));
        }
    }

    public CopyOnWriteArrayList<Level> getLevels() {
        return levels;
    }

    public synchronized String  parkVehicle(Vehicle vehicle) {
        for (Level level : levels) {
            ParkingSpot spot = level.findSpot(vehicle);
            if (spot != null) {
                spot.assignVehicle(vehicle);
                spot.setAvailable(false);
                // Create a unique ticket id (using the spot id and current time)
                String ticketId = "T-" + spot.getId() + "-" + System.currentTimeMillis();
                Ticket ticket = new Ticket(ticketId, vehicle, spot);
                activeTickets.put(spot.getId(), ticket);
                return "Vehicle " + vehicle.getLicenseNumber()
                        + "\nparked at spot " + spot.getId() +
                        "\nlevel:"+level+
                        "\nTicket issued: " + ticket.toString();
            }
        }
        return "No available spot for vehicle type " + vehicle.getType();
    }


    public synchronized String releaseSpot(String spotId) {
        for (Level level : levels) {
            ConcurrentHashMap<String, ParkingSpot> spots = level.getSpots();
            if (spots.containsKey(spotId)) {
                ParkingSpot spot = spots.get(spotId);
                if (!spot.isAvailable()) {
                    Ticket ticket = activeTickets.get(spotId);
                    if (ticket != null) {
                        ticket.closeTicket();
                        Duration duration = Duration.between(ticket.getEntryTime(), ticket.getExitTime());
                        activeTickets.remove(spotId);
                        spot.releaseSpot();
                        spot.setAvailable(true);
                        return "Spot " + spotId +"at level "+level+ " released.\n" +
                                "Vehicle:" + ticket.getVehicle().getLicenseNumber() +
                                " was parked for " + duration.toMinutes() + " minutes.";
                    } else {
                        // Fallback if no ticket is found (should not normally happen)
                        spot.releaseSpot();
                        spot.setAvailable(true);
                        return "Spot " + spotId + " released, but no ticket found.";
                    }
                } else {
                    return "Spot " + spotId + " is already available.";
                }
            }
        }
        return "Spot " + spotId + " not found.";
    }

    /**
     * Returns a multi-line status report of each level and its parking spots.
     *
     * @return a string representing the current status of the parking lot.
     */
    public String getStatus() {
        StringBuilder sb = new StringBuilder();
        // Iterate over each level in the parking lot.
        for (Level level : levels) {
            sb.append("Level ").append(level.getLevelNumber()).append(":\n");
            // Get the parking spots for the current level.
            ConcurrentHashMap<String, ParkingSpot> spots = level.getSpots();
            if (spots == null || spots.isEmpty()) {
                sb.append("  No spots found for this level.\n");
            } else {
                // List each parking spot and its status.
                for (ParkingSpot spot : spots.values()) {
                    sb.append(spot.getId())
                            .append(" - ")
                            .append(spot.isAvailable() ? "Available - " : "Occupied - ");
                    // If occupied, display the vehicle's license number.
                    if (!spot.isAvailable() && spot.getVehicle() != null) {
                        sb.append(" (").append(spot.getVehicle().getLicenseNumber()).append(")");
                    }
                    sb.append(spot.getVehicle().getType());
                    sb.append("\n");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
