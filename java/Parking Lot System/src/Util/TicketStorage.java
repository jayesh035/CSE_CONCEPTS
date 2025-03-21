package Util;

import Server.Classes.Ticket;

import java.io.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class TicketStorage {

    private static final String FILE_PATH = "tickets.dat";

    public  static void saveTickets(CopyOnWriteArrayList<Ticket> tickets) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(tickets);
            System.out.println("Tickets saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static CopyOnWriteArrayList<Ticket> loadTickets() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (CopyOnWriteArrayList<Ticket>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No existing tickets found.");
            return new CopyOnWriteArrayList<>();
        }
    }
}
