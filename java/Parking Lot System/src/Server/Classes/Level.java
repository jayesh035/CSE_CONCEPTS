package Server.Classes;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class Level
{
    private String levelNumber;
    private ConcurrentHashMap<String, ParkingSpot> Spots;

    Level(int levelNumber)
    {
        Spots = new ConcurrentHashMap<>();
        this.levelNumber = Integer.toString(levelNumber);
        loadSpots();
    }

    void loadSpots()
    {
        var random = new Random();
        String[] vehicleTypes = {"CAR", "BIKE", "TRUCK"};

        for (int i = 1; i <= 15; i++) {
            var vehicle = new Vehicle(null, vehicleTypes[random.nextInt(3)]);
            var spotID = i + "SL" + levelNumber;
            var spot = new ParkingSpot(spotID, vehicle, true);
            Spots.put(spotID, spot);
        }
    }

    public ConcurrentHashMap<String, ParkingSpot> getSpots()
    {
        return Spots;
    }

    public String getLevelNumber()
    {
        return levelNumber;
    }

    ParkingSpot findSpot(String SpotID) {
        if (Spots.contains(SpotID)) {
            return Spots.get(SpotID);
        }
        return null;
    }
}
