import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Vehicle {

    private static int nextVehicleID = 1;

    private String vehicleID;
    private String reg;
    private String make;
    private String model;
    private int year;

    public Vehicle(String reg, String make, String model, int year){
        this.vehicleID = String.format("CAR-%04d", nextVehicleID++);
        this.reg = reg;
        this.make = make;
        this.model = model;
        this.year = year;
    }

    // Constructor for loading from file
    public Vehicle(String vehicleID, String reg, String make, String model, int year){
        this.vehicleID = vehicleID;
        this.reg = reg;
        this.make = make;
        this.model = model;
        this.year = year;
    }

    public static void initializeVehicleID() {

        int maxID = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader("vehicle.txt"))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");
                String idPart = parts[0]; // CAR-0003

                int number = Integer.parseInt(idPart.substring(4));

                if (number > maxID) {
                    maxID = number;
                }
            }

        } catch (IOException e) {
            // file may not exist
        }

        nextVehicleID = maxID + 1;
    }

    public void saveToFile() {

        String data = vehicleID + "," +
                reg + "," +
                make + "," +
                model + "," +
                year;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("vehicle.txt", true))) {
            writer.write(data);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving vehicle.");
        }
    }

    public String getVehicleID() { return vehicleID; }
    public String getReg() { return reg; }
    public String getMake() { return make; }
    public String getModel() { return model; }
    public int getYear() { return year; }

    public String getDetails(){
        return vehicleID + " " + reg + " " + make + " " + model + " " + year;
    }
}