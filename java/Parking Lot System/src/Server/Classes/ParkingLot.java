package Server.Classes;
import Util.Constants;
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
    private final HashMap<String, List<ParkingSpot>> typeSlots;

    //Spots to retrieve spots based on spotNumber and levelNumber
    private final HashMap<String, ParkingSpot> spots;



    public ParkingLot()
    {
        // Initialize active tickets and spots
        spots = new HashMap<>();

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
            //getting vehicle type randomly for initialization
            String vehicleType=vehicleTypes[random.nextInt(3)];

            //creating vehicle
            Vehicle vehicle = new Vehicle(Constants.emptyFlag, vehicleType);


            /*
            creating spot ID such that it can contain spotNumber and levelNumber
            by separating with 'SL' so that we can use it as key of activeTickets
            and spots hashmaps.
            */
            String spotID = i + "SL" + levelNumber;

            ParkingSpot spot = new ParkingSpot(spotID, vehicle, true);

            if(!typeSlots.containsKey(vehicleType))
            {
                typeSlots.put(vehicleType,new ArrayList<>());
            }

            typeSlots.get(vehicleType).add(spot);

            this.spots.put(spotID, spot);
        }
    }

    public String parkVehicle(String levelName, String spotNumber, String type, String license)
    {
        //create spotId base on spotNumber and levelName
        String spotID = spotNumber + "SL" + levelName;

            //check if spotId is present in parking lot or not
            if (spots.containsKey(spotID))
            {
                //get target spot based on spotID
                ParkingSpot spot = this.spots.get(spotID);

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
                             ticket.getVehicle().getLicenseNumber().equalsIgnoreCase(license))
                     {

                         return "Success!! \nYou can park your vehicle at " + spot.getId()
                                 + "\nLevel: " + levelName + "\nTicket issued: " + ticket;
                     }
                     else
                     {
                         return "Spot is not available for " + type;
                     }

                    }
                else
                    {
                        return "Spot is not available for " + type;
                    }
                }

            return "No available spot for vehicle type " + type;
    }


    static String getSpotNumber(String spotId)
    {
        boolean flag = true;

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < spotId.length(); i++)
        {
            if (spotId.charAt(i) == 'S')
            {
                flag = false;
            }
            if (flag)
            {
                result.append(spotId.charAt(i));
            }
        }

        return result.toString();
    }

    static String getLevel(String spotId)
    {
        boolean flag = false;

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < spotId.length(); i++)
        {
            if (flag)
            {
                result.append(spotId.charAt(i));
            }
            if (spotId.charAt(i) == 'L')
            {
                flag = true;
            }
        }
        return result.toString();
    }

    public String checkAvailable(String levelNumber, String spotNumber, String vehicleType)
    {
        String SpotId = spotNumber + "SL" + levelNumber;

        if (spots.containsKey(SpotId))
        {
            var spot = this.spots.get(SpotId);

            if (spot.isAvailable())
            {
                Vehicle vehicle = spot.getVehicle();

                if (vehicle.getType().equalsIgnoreCase(vehicleType))
                {
                    return "Spot is available. You can park the vehicle.";
                }
                else
                {
                    return "Spot is available but is designated for " + vehicle.getType();
                }
            }
            else
            {
                return "Spot exists but is currently occupied.";
            }
        }
        return "Spot does not exist.";
    }

    public String releaseSpot(String spotId)
    {
        if (spots.containsKey(spotId))
        {
            var level = getLevel(spotId);

            var spot = this.spots.get(spotId);

            boolean isAvailable = spot.isAvailable();

            if (!isAvailable)
            {
                var licenceNumber=spot.getVehicle().getLicenseNumber();

                Ticket ticket = activeTickets.get(spotId);

                if (ticket != null)
                {
                    ticket.closeTicket();

                    Duration duration = Duration.between(ticket.getEntryTime(), ticket.getExitTime());

                    final String[] res = new String[1];

                    activeTickets.computeIfPresent(spotId,(key,value)->
                    {

                            spot.releaseSpot();

                            spot.setAvailable(true);

                            res[0] ="Spot " + spotId + " at level " + level + " released.\n"
                                        + "Vehicle: " + licenceNumber
                                        + " was parked for " + duration.toMinutes() + " minutes.";

                            return null;
                    });

                    if(res[0]!=null)
                    {
                        return res[0];
                    }
                    else
                    {
                        return "Spot " + spotId + " is already available.";
                    }
                }
                else
                {
                    // Fallback if no ticket is found (should not normally happen)
                    spot.releaseSpot();

                    spot.setAvailable(true);

                    return "Spot " + spotId + " released, but no ticket found.";
                }
            }
            else
            {
                return "Spot " + spotId + " is already available.";
            }
        }
        return "Spot " + spotId + " not found.";
    }

    public String getStatus(String vehicleType)
    {
        //For response
        StringBuilder response = new StringBuilder();

        if(typeSlots.containsKey(vehicleType))
        {
            //get parking spots by its type
            List<ParkingSpot>spots=typeSlots.get(vehicleType);

            for(ParkingSpot spot:spots)
            {
                String spotID=spot.getId();

                Vehicle vehicle = spot.getVehicle();

                response.append("\nSpotID: ").append(spotID);

                response.append(", SpotName: ").append(getSpotNumber(spotID));

                response.append(", Level: ").append(getLevel(spotID));

                if (!spot.isAvailable())
                {
                    response.append(", Occupied");
                    response.append(", LicenseNumber: ").append(vehicle.getLicenseNumber());
                }
                else
                {
                    response.append(", Available");
                }

                response.append(" VehicleType: ").append(vehicle.getType());

                response.append("\n\n");
            }
        }

        return response.isEmpty()?"Slots are not available for "+vehicleType: response.toString();
    }
}
