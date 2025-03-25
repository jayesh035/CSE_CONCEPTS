package Server.Classes;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;


public class ParkingLot {



    // Store active tickets
    // to store data of parking spot and vehicle at particular time
    private final ConcurrentHashMap<String, Ticket> activeTickets;

    //to vehicle type wise store parking slots data
    private final HashMap<String, List<ParkingSpot> >typeSlots;

    //Spots to retrieve spots based on spotNumber and levelNumber
    private final ConcurrentHashMap<String, ParkingSpot> Spots;


    public ConcurrentHashMap<String, Ticket> getActiveTickets()
    {
        return activeTickets;
    }

    public ParkingLot()
    {
        // Initialize active tickets and spots
        Spots = new ConcurrentHashMap<>();

        typeSlots=new HashMap<>();

        activeTickets = new ConcurrentHashMap<>();

        // Assume the parking lot has 4 levels.
        for (int i = 1; i <= 4; i++)
        {

            loadSpots(i);

        }

    }

    void loadSpots(int levelNumber)
    {

        Random random = new Random();

        String[] vehicleTypes = {"CAR", "BIKE", "TRUCK"};

        for (int i = 1; i <= 15; i++)
        {


            String vehicleType=vehicleTypes[random.nextInt(3)];
            Vehicle vehicle = new Vehicle(null, vehicleType);

            String spotID = i + "SL" + levelNumber;

            ParkingSpot spot = new ParkingSpot(spotID, vehicle, true);

            if(!typeSlots.containsKey(vehicleType))
            {
                typeSlots.put(vehicleType,new ArrayList<>());
            }

            typeSlots.get(vehicleType).add(spot);



            Spots.put(spotID, spot);

        }

    }

    public String parkVehicle(String levelName, String spotNumber, String type, String license) {

        //create spotId base on spotNumber and levelName
        String spotID = spotNumber + "SL" + levelName;

            //check if spotId is present in parking lot or not
            if (Spots.containsKey(spotID))
            {

                //get target spot based on spotID
                ParkingSpot spot = Spots.get(spotID);


                if (spot.isAvailable() &&
                        spot.getVehicle().getType().equalsIgnoreCase(type))
                {

                    //used compute if absent method to prevent race condition
                     Ticket ticket=activeTickets.computeIfAbsent
                             (spot.getId(),key-> {
                                 if(activeTickets.containsKey(spotID))
                                 {
                                     return null;
                                 }

                            String ticketId = "T-" + spot.getId() + "-" + System.currentTimeMillis();

                                 //assign vehicle to spot and set it as
                            spot.assignVehicle(license);

                            spot.setAvailable(false);
                            //generate new ticket
                            return new Ticket(ticketId, spot.getVehicle(), spot);

                        });


                     //check if ticket is generated for same vehicle by license number or
                     if((ticket!=null) &&
                             (ticket.getVehicle().getLicenseNumber().equalsIgnoreCase(license)))
                     {

                         return "Success!! \nYou can park your vehicle at " + spot.getId()
                                 + "\nLevel: " + levelName + "\nTicket issued: " + ticket;
                     }
                     else
                     {
                         return "Spot is not available for " + type;
                     }

                    } else {
                        return "Spot is not available for " + type;
                    }
                }


        return "No available spot for vehicle type " + type;
    }


    static String getSpotNumber(String spotId)
    {
        boolean flag = true;
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < spotId.length(); i++) {
            if (spotId.charAt(i) == 'S') {
                flag = false;
            }
            if (flag) {
                result.append(spotId.charAt(i));
            }
        }
        return result.toString();
    }

    static String getLevel(String spotId) {
        boolean flag = false;
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < spotId.length(); i++) {
            if (flag) {
                result.append(spotId.charAt(i));
            }
            if (spotId.charAt(i) == 'L') {
                flag = true;
            }
        }
        return result.toString();
    }

    public String checkAvailable(String levelNumber, String spotNumber, String vehicleType) {
        String SpotId = spotNumber + "SL" + levelNumber;

        if (Spots.containsKey(SpotId)) {
            var spot = Spots.get(SpotId);

            if (spot.isAvailable()) {
                Vehicle vehicle = spot.getVehicle();

                if (vehicle.getType().equalsIgnoreCase(vehicleType)) {
                    return "Spot is available. You can park the vehicle.";
                } else {
                    return "Spot is available but is designated for " + vehicle.getType();
                }
            } else {
                return "Spot exists but is currently occupied.";
            }
        }
        return "Spot does not exist.";
    }

    public String releaseSpot(String spotId) {
        if (Spots.containsKey(spotId)) {
            String level = getLevel(spotId);
            var spot = Spots.get(spotId);
            boolean isAvailable = spot.isAvailable();

            if (!isAvailable) {
                Ticket ticket = activeTickets.get(spotId);

                if (ticket != null) {
                    ticket.closeTicket();
                    Duration duration = Duration.between(ticket.getEntryTime(), ticket.getExitTime());

                    try{

                        activeTickets.remove(spotId);
                    } catch (RuntimeException e) {
                        return "Spot " + spotId + " is already available.";
                    }

                    spot.releaseSpot();
                    spot.setAvailable(true);

                    return "Spot " + spotId + " at level " + level + " released.\n"
                            + "Vehicle: " + ticket.getVehicle().getLicenseNumber()
                            + " was parked for " + duration.toMinutes() + " minutes.";
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
        return "Spot " + spotId + " not found.";
    }

    public String getStatus(String vehicleType) {


        StringBuilder sb = new StringBuilder();


        if(typeSlots.containsKey(vehicleType))
        {
            List<ParkingSpot>spots=typeSlots.get(vehicleType);
            for(ParkingSpot spot:spots)
            {

                String spotID=spot.getId();

                Vehicle vehicle = spot.getVehicle();


                sb.append("\nSpotID: ").append(spotID);
                sb.append(", SpotName: ").append(getSpotNumber(spotID));
                sb.append(", Level: ").append(getLevel(spotID));

                if (!spot.isAvailable())
                {
                        sb.append(", Occupied");
                        sb.append(", LicenseNumber: ").append(vehicle.getLicenseNumber());
                }
                else
                {
                        sb.append(", Available");
                }

                    sb.append(" VehicleType: ").append(vehicle.getType());
                    sb.append("\n\n");


            }
        }







//        for (String spotID : Spots.keySet()) {

//        }

        return sb.isEmpty()?"Slots are not available for "+vehicleType: sb.toString();
    }
}
