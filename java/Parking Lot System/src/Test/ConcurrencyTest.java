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
