package com.pluralsight;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class DealershipFileManager {
    private static final String FILE_NAME = "inventory.csv";

    public Dealership getDealership() {
        Dealership dealership = null;
        try (Scanner scanner = new Scanner(new File(FILE_NAME))) {
            if (scanner.hasNextLine()) {
                String dealershipInfo = scanner.nextLine();
                String[] dealershipData = dealershipInfo.split("\\|");

                String name = dealershipData[0];
                String address = dealershipData[1];
                String phone = dealershipData[2];

                dealership = new Dealership(name, address, phone);
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] vehicleData = line.split("\\|");

                int vin = Integer.parseInt(vehicleData[0]);
                int year = Integer.parseInt(vehicleData[1]);
                String make = vehicleData[2];
                String model = vehicleData[3];
                String type = vehicleData[4];
                String color = vehicleData[5];
                int odometer = Integer.parseInt(vehicleData[6]);
                double price = Double.parseDouble(vehicleData[7]);

                Vehicle vehicle = new Vehicle(vin, year, make, model, type, color, odometer, price);
                dealership.addVehicle(vehicle);
            }
        } catch (FileNotFoundException e) {
            System.err.println("FILE NOT FOUND. Creating a new dealership.");
            dealership = new Dealership("D & B Used Cars", "111 Old Benbrook Rd", "817-555-5555");
        }
        return dealership;
    }

    public void saveDealership(Dealership dealership) {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            String dealershipLine = dealership.getName() + "|" + dealership.getAddress() + "|" + dealership.getPhone();
            writer.write(dealershipLine + "\n");

            for (Vehicle vehicle : dealership.getAllVehicles()) {
                String vehicleLine = vehicle.getVin() + "|" +
                        vehicle.getYear() + "|" +
                        vehicle.getMake() + "|" +
                        vehicle.getModel() + "|" +
                        vehicle.getVehicleType() + "|" +
                        vehicle.getColor() + "|" +
                        vehicle.getOdometer() + "|" +
                        String.format("%.2f", vehicle.getPrice());

                writer.write(vehicleLine + "\n");
            }
        } catch (Exception e) {
            System.err.println("ERROR SAVING DEALERSHIP DATA" + e.getMessage());
        }
    }
}
