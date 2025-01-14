package bstefanov.transportOrg.service;

import bstefanov.transportOrg.dao.VehicleDao;
import bstefanov.transportOrg.dto.VehicleDto;
import bstefanov.transportOrg.entity.VehicleType;
import jakarta.validation.ConstraintViolationException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class VehicleService {
    private final long companyId;

    public VehicleService(long companyId) {
        this.companyId = companyId;
    }

    public void showMenu() {
        boolean shouldExit = false;
        do {
            System.out.println("\n");
            System.out.println("=".repeat(40));
            System.out.println("Vehicle management menu:");
            System.out.println("-".repeat(40));
            System.out.println("1. Add vehicle");
            System.out.println("2. List vehicles");
            System.out.println(" ");
            System.out.println("0. Back");
            System.out.println("-".repeat(40));

            try {
                System.out.println("Waiting for input:");
                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("=".repeat(40));
                        addVehicle();
                        break;
                    case 2:
                        System.out.println("=".repeat(40));
                        listVehicles();
                        break;
                    case 0:
                        System.out.println("=".repeat(40));
                        shouldExit = true;
                        break;
                    default:
                        System.out.println("Invalid choice, try again");
                        System.out.println("=".repeat(40));
                }
            } catch (Exception e) {
                System.out.println("Invalid input, try again");
                System.out.println("=".repeat(40));
            }

        } while (!shouldExit);
    }

    private void addVehicle() {
        System.out.println("\n");
        System.out.println("=".repeat(40));
        System.out.println("Add vehicle");
        System.out.println("-".repeat(40));

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter vehicle license plate:");
            String licensePlate = scanner.nextLine();

            System.out.println("Enter vehicle type (0 - Mini bus, 1 - Bus, 2 - Normal truck, 3 - Tanker truck):");
            VehicleType type = VehicleType.values()[scanner.nextInt()]; // error-prone, idk fix it if you got time
            scanner.nextLine(); // dirty hack to fix the scanner bug

            System.out.println("Enter vehicle brand:");
            String brand = scanner.nextLine();

            System.out.println("Enter vehicle model:");
            String model = scanner.nextLine();

            System.out.println("Enter fuel price per kilometer:");
            BigDecimal fuelPricePerKM = scanner.nextBigDecimal();

            VehicleDto vehicleDto = new VehicleDto(licensePlate, type, brand, model, fuelPricePerKM, companyId);
            VehicleDao.createVehicle(vehicleDto);
            System.out.println("Vehicle added successfully!");
        } catch (ConstraintViolationException e) {
            System.out.println("Error adding vehicle:");
            for (var violation : e.getConstraintViolations()) {
                System.out.println("[!] - " + violation.getMessage());
            }
        }
        System.out.println("=".repeat(40));
    }

    private void listVehicles() {
        boolean shouldExit = false;
        do {
            System.out.println("\n");
            System.out.println("=".repeat(40));
            System.out.println("Manage vehicles");
            System.out.println("-".repeat(40));
            System.out.println("Syntax: [Operation] [OpID]");
            System.out.println("edit - Edit vehicle details");
            System.out.println("delete - Remove vehicle");
            System.out.println(" ");
            System.out.println("0 - Back");
            System.out.println("-".repeat(40));
            System.out.println("Vehicles (OpID - Type - License plate - Brand - Model - Fuel price per kilometer):");

            List<VehicleDto> vehicles = VehicleDao.getVehicles(companyId);
            for (int i = 0; i < vehicles.size(); i++) {
                VehicleDto vehicle = vehicles.get(i);
                System.out.println("[" + (i + 1) + "] - " + vehicle.getType() + " - " + vehicle.getLicensePlate() + " - " + vehicle.getBrand() + " - " + vehicle.getModel() + " - " + vehicle.getFuelPricePerKM());
            }

            System.out.println("-".repeat(40));
            System.out.println("Waiting for input:");

            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String[] splitInput = input.split(" ");

            if (splitInput.length == 2) {
                VehicleDto vehicle = vehicles.get(Integer.parseInt(splitInput[1]) - 1);

                switch (splitInput[0]) {
                    case "edit":
                        editVehicle(vehicle);
                        System.out.println("=".repeat(40));
                        break;
                    case "delete":
                        String name = vehicle.getLicensePlate() + " - "
                                + vehicle.getBrand() + " " + vehicle.getModel();
                        VehicleDao.deleteVehicle(vehicle.getId());
                        System.out.println("Vehicle " + name + " was successfully removed!");
                        System.out.println("=".repeat(40));
                        break;
                    default:
                        System.out.println("Invalid operation, try again");
                        System.out.println("=".repeat(40));
                }
            } else if (input.equals("0")) {
                shouldExit = true;
            } else {
                System.out.println("Invalid input, try again");
                System.out.println("=".repeat(40));
            }

        } while (!shouldExit);
        System.out.println("=".repeat(40));
    }

    private void editVehicle(VehicleDto vehicle) {
        System.out.println("\n");
        System.out.println("=".repeat(40));
        System.out.println("Edit vehicle " + vehicle.getLicensePlate() + ":");
        System.out.println("-".repeat(40));

        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter new license plate (leave blank for current: " + vehicle.getLicensePlate() + "):");
            String licensePlate = scanner.nextLine();
            if (!licensePlate.isBlank()) {
                vehicle.setLicensePlate(licensePlate);
            }

            // Changing the type of vehicle does not make sense

            System.out.println("Enter new brand (leave blank for current: " + vehicle.getBrand() + "):");
            String brand = scanner.nextLine();
            if (!brand.isBlank()) {
                vehicle.setBrand(brand);
            }

            System.out.println("Enter new model (leave blank for current: " + vehicle.getModel() + "):");
            String model = scanner.nextLine();
            if (!model.isBlank()) {
                vehicle.setModel(model);
            }

            System.out.println("Enter new fuel price per kilometer (leave blank for current: "
                    + vehicle.getFuelPricePerKM() + "):");
            String fuelPricePerKM = scanner.nextLine();
            if (!fuelPricePerKM.isBlank()) {
                vehicle.setFuelPricePerKM(new BigDecimal(fuelPricePerKM));
            }

            VehicleDao.editVehicle(vehicle);
            System.out.println("Vehicle edited successfully!");
        } catch (ConstraintViolationException e) {
            System.out.println("Error editing vehicle:");
            for (var violation : e.getConstraintViolations()) {
                System.out.println("[!] - " + violation.getMessage());
            }
        }
        System.out.println("=".repeat(40));
    }
}
