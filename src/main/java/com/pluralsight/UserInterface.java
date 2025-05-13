package com.pluralsight;

import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private final Dealership dealership;
    private final Scanner scanner;
    private final DealershipFileManager fileManager;

    public UserInterface() {
        this.scanner = new Scanner(System.in);
        this.fileManager = new DealershipFileManager();
        this.dealership = fileManager.getDealership();
    }

    public void display() {
        boolean running = true;
        while (running){
            displayMenu();
            int choice = getIntInput("ENTER YOUR CHOICE: ");

            switch (choice) {
                case 1:
                    getByPriceRequest();
                    break;
                case 2:
                    getByMakeModelRequest();
                    break;
                case 3:
                    getByYearRequest();
                    break;
                case 4:
                    getByColorRequest();
                    break;
                case 5:
                    getByMileageRequest();
                    break;
                case 6:
                    getByVehicleTypeRequest();
                    break;
                case 7:
                    getAllVehiclesRequest();
                    break;
                case 8:
                    addVehicleRequest();
                    break;
                case 9:
                    removeVehicleRequest();
                    break;
                case 99:
                    running = false;
                    break;
                default:
                    System.out.println("INVALID CHOICE TRY AGAIN");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n--- DEALERSHIP MENU ---");
        System.out.println("1 - FIND VEHICLES WITHIN PRICE RANGE");
        System.out.println("2 - FIND VEHICLES BY MAKE/MODEL");
        System.out.println("3 - FIND VEHICLES BY YEAR RANGE");
        System.out.println("4 - FIND VEHICLES BY COLOR");
        System.out.println("5 - FIND VEHICLES BY MILEAGE RANGE");
        System.out.println("6 - FIND VEHICLES BY CAR TYPE (CAR, TRUCK, SUV, VAN)");
        System.out.println("7 - LIST ALL VEHICLES");
        System.out.println("8 - ADD A VEHICLE");
        System.out.println("9 - REMOVE A VEHICLE");
        System.out.println("99 - QUIT");
    }

    private void displayVehicles(List<Vehicle> vehicles) {
        if (vehicles.isEmpty()) {
            System.out.println("NO VEHICLES FOUND");
            return;
        }
        System.out.println("\n--- MATCHING VEHICLES FOUND ---");
        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle);
        }
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("PLEASE ENTER VALID INPUT");
            }
        }
    }

    private double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("PLEASE ENTER A VALID NUMBER");
            }
        }
    }

    private void getByPriceRequest() {
        System.out.println("\n--- SEARCH BY PRICE ---");
        double min = getDoubleInput("ENTER MINIMUM PRICE: ");
        double max = getDoubleInput("ENTER MAXIMUM PRICE: ");
        List<Vehicle> vehicles = dealership.getVehiclesByPrice(min, max);
        displayVehicles(vehicles);
}

    private void getByMakeModelRequest() {
        System.out.println("\n--- SEARCH BY MAKE/MODEL ---");
        System.out.print("ENTER MAKE: ");
        String make = scanner.nextLine();
        System.out.print("ENTER MODEL: ");
        String model = scanner.nextLine();
        List<Vehicle> vehicles = dealership.getVehiclesByMakeModel(make, model);
        displayVehicles(vehicles);
}

    private void getByYearRequest() {
        System.out.println("\n--- SEARCH BY YEAR ---");
        int min = getIntInput("ENTER MINIMUM YEAR: ");
        int max = getIntInput("ENTER MAXIMUM YEAR: ");
        List<Vehicle> vehicles = dealership.getVehiclesByYear(min, max);
        displayVehicles(vehicles);
    }

    private void getByColorRequest() {
        System.out.println("\n--- SEARCH BY COLOR ---");
        System.out.print("ENTER COLOR: ");
        String color = scanner.nextLine();
        List<Vehicle> vehicles = dealership.getVehiclesByColor(color);
        displayVehicles(vehicles);
    }

    private void getByMileageRequest() {
        System.out.println("\n--- SEARCH BY MILEAGE ---");
        int min = getIntInput("ENTER MINIMUM MILEAGE: ");
        int max = getIntInput("ENTER MAXIMUM MILEAGE: ");
        List<Vehicle> vehicles = dealership.getVehiclesByMileage(min, max);
        displayVehicles(vehicles);
    }

    private void getByVehicleTypeRequest() {
        System.out.println("\n--- SEARCH BY VEHICLE TYPE ---");
        System.out.print("ENTER VEHICLE TYPE (CAR, TRUCK, SUV, VAN): ");
        String type = scanner.nextLine();
        List<Vehicle> vehicles = dealership.getVehiclesByType(type);
        displayVehicles(vehicles);
    }

    private void getAllVehiclesRequest() {
        System.out.println("\n--- ALL VEHICLES ---");
        List<Vehicle> vehicles = dealership.getAllVehicles();
        displayVehicles(vehicles);
    }

    private void addVehicleRequest() {
        System.out.println("\n--- ADD NEW VEHICLE ---");
        int vin = getIntInput("ENTER VIN: ");
        int year = getIntInput("ENTER YEAR: ");
        System.out.print("ENTER MAKE: ");
        String make = scanner.nextLine();
        System.out.print("ENTER MODEL: ");
        String model = scanner.nextLine();
        System.out.print("ENTER VEHICLE TYPE (CAR, TRUCK, SUV, VAN): ");
        String type = scanner.nextLine();
        System.out.print("ENTER COLOR: ");
        String color = scanner.nextLine();
        int odometer = getIntInput("ENTER MILEAGE: ");
        double price = getDoubleInput("ENTER PRICE: ");

        dealership.addVehicle(new Vehicle(vin, year, make, model, type, color, odometer, price));

        fileManager.saveDealership(dealership);
        System.out.println("VEHICLE ADDED SUCCESSFULLY");
    }

    private void removeVehicleRequest() {
        System.out.println("\n--- REMOVE VEHICLE ---");
        List<Vehicle> allVehicles = dealership.getAllVehicles();

        if (allVehicles.isEmpty()) {
            System.out.println("NO VEHICLES LEFT");
            return;
        }

        displayVehicles(allVehicles);
        int vinRemove = getIntInput("ENTER VIN OF VEHICLE TO REMOVE: ");

        boolean removed = false;
        for (Vehicle vehicle : allVehicles) {
            if (vehicle.getVin() == vinRemove) {
                dealership.removeVehicle(vehicle);
                fileManager.saveDealership(dealership);
                System.out.println("VEHICLE WITH VIN " + vinRemove + " REMOVED SUCCESSFULLY");

                removed = true;
                break;
            }
        }

        if (!removed) {
            System.out.println("NO VEHICLE WITH VIN " + vinRemove + " FOUND");
        }
    }
}
