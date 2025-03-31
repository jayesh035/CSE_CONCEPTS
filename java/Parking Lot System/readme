# Parking Lot Management System

## Overview
This project is a simple Parking Lot Management System implemented in Java using socket programming for client-server communication. It allows users to perform various parking operations such as parking a vehicle, releasing a parking spot, checking parking status, and checking availability.

## Features
- **Multi-threaded Server:** Supports multiple concurrent client connections.
- **Client-Server Communication:** Uses Java sockets for interaction.
- **Parking Lot Operations:**
  - Park a vehicle.
  - Release a vehicle.
  - Check parking status.
  - Check spot availability.
- **Concurrency Support:** Ensures multiple clients can perform operations simultaneously.

## Technologies Used
- Java
- Socket Programming
- SLF4J (Logging)

## Project Structure
```
├── src
│   ├── Server
│   │   ├── Server.java          # Multi-threaded server handling client requests
│   │   ├── ParkingLot.java      # Manages parking spots and vehicle information
│   ├── Client
│   │   ├── ParkingClient.java   # Client application for user interaction
│   ├── Test
│   │   ├── TestingClient.java   # Automated testing client to send requests to the server
│   ├── Util
│   │   ├── Constants.java       # Contains server configuration values
```

## Installation & Setup

1. **Clone the repository**
   ```sh
   git clone <repository-url>
   cd <project-folder>
   ```

2. **Compile the project**
   ```sh
   javac -d out -sourcepath src src/Server/Server.java src/Client/ParkingClient.java src/Test/TestingClient.java
   ```

3. **Run the Server**
   ```sh
   java -cp out Server.Server
   ```

4. **Run a Client Instance**
   ```sh
   java -cp out Client.ParkingClient
   ```

5. **Run the Testing Client (Optional for stress testing)**
   ```sh
   java -cp out Test.TestingClient
   ```

## Example Usage

### Park a Vehicle
```
Command: PARK
Vehicle Type: CAR
License Plate: ABC123
Level: 1
Spot Number: 10
Server Response: Vehicle parked successfully at 1SL10.
```

### Release a Vehicle
```
Command: RELEASE
Spot ID: 10SL1
Server Response: Spot 10SL1 is now available.
```

### Check Parking Status
```
Command: STATUS
Vehicle Type: CAR
Server Response: Occupied spots for CAR: [10SL1, 12SL2]
```

## Concurrency Testing Code
Below is a test case that creates multiple threads to simulate concurrent parking requests:

```java
package Test;

import java.util.HashMap;

public class ConcurrencyTest {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            int spotNumber = 10 + i;
            Thread t = new Thread(() -> {
                HashMap<String, String> data = new HashMap<>();
                data.put("command", "PARK");
                data.put("type", "CAR");
                data.put("license", "CAR" + spotNumber);
                data.put("spotNumber", String.valueOf(7));
                data.put("level", "1");

                TestingClient.sendData(data);
            });
            t.start();
        }
    }
}
```

### Sample Output
```
Server Response: Spot is not available for CAR
Server Response: Success!!
You can park your vehicle at 7SL1
Level: 1
Ticket issued: Ticket{
id='T-7SL1-1743405896661'
entryTime=2025-03-31T12:54:56.661506004
exitTime=null
vehicle=Vehicle{licenseNumber='CAR17', type='CAR'}
parkingSpot=ParkingSpot{id='7SL1', vehicle=Vehicle{licenseNumber='CAR17', type='CAR'}, isAvailable=false}}
Server Response: Spot is not available for CAR
Server Response: Spot is not available for CAR
Server Response: Spot is not available for CAR
Server Response: Spot is not available for CAR
Server Response: Spot is not available for CAR
Server Response: Spot is not available for CAR
Server Response: Spot is not available for CAR
Server Response: Spot is not available for CAR
```

This test case helps evaluate the server's ability to handle multiple simultaneous parking requests and identify potential concurrency issues.

## License
This project is open-source and free to use.

