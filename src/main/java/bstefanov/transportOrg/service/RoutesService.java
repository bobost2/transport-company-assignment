package bstefanov.transportOrg.service;

import bstefanov.transportOrg.dao.*;
import bstefanov.transportOrg.data.EmployeeSort;
import bstefanov.transportOrg.dto.*;
import bstefanov.transportOrg.entity.*;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RoutesService {
    private final long companyId;

    public RoutesService(long companyId) {
        this.companyId = companyId;
    }

    public void showMenu() {
        boolean shouldExit = false;
        do {
            System.out.println("\n");
            System.out.println("=".repeat(40));
            System.out.println("Routes menu:");
            System.out.println("-".repeat(40));
            System.out.println("1. Add route");
            System.out.println("2. List routes");
            System.out.println("3. Export routes");
            System.out.println(" ");
            System.out.println("0 - Back");
            System.out.println("-".repeat(40));

            try {
                System.out.println("Waiting for input:");
                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("=".repeat(40));
                        addRoute();
                        break;
                    case 2:
                        System.out.println("=".repeat(40));
                        listRoutes();
                        break;
                    case 3:
                        System.out.println("=".repeat(40));
                        exportRoutes();
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

    private void addRoute() {
        System.out.println("\n");
        System.out.println("=".repeat(40));
        System.out.println("Add route");
        System.out.println("-".repeat(40));

        Scanner scanner = new Scanner(System.in);

        System.out.println("Available vehicles:");
        System.out.println("Vehicles (OpID - Type - License plate - Brand - Model - Fuel price per kilometer):");

        List<VehicleDto> vehicles = VehicleDao.getVehicles(companyId);
        for (int i = 0; i < vehicles.size(); i++) {
            VehicleDto vehicle = vehicles.get(i);
            System.out.println("[" + (i + 1) + "] - " + vehicle.getType() + " - " + vehicle.getLicensePlate()
                    + " - " + vehicle.getBrand() + " - " + vehicle.getModel() + " - " + vehicle.getFuelPricePerKM());
        }

        System.out.println("-".repeat(40));
        System.out.println("Select vehicle:");
        int vehicleIndex = scanner.nextInt();
        VehicleDto vehicle = vehicles.get(vehicleIndex - 1);

        BaseCargo cargo;
        switch (vehicle.getType()) {
            case MINI_BUS:
            case BUS:
                System.out.println("Enter amount of people the bus will carry:");
                int people = scanner.nextInt();
                cargo = new PeopleTransport(people);
                break;
            case TRUCK_NORMAL:
                System.out.println("Enter cargo weight:");
                BigDecimal weight = scanner.nextBigDecimal();
                cargo = new NormalCargo(weight);
                break;
            case TRUCK_TANKER:
                System.out.println("Enter cargo weight:");
                BigDecimal weightTanker = scanner.nextBigDecimal();

                System.out.println("Enter cargo liters:");
                int liters = scanner.nextInt();

                cargo = new LiquidCargo(weightTanker, liters);
                break;

            default:
                System.out.println("Invalid vehicle type");
                cargo = new BaseCargo();
        }

        List<QualificationDto> qualifications = new ArrayList<>();
        System.out.println("Available qualifications:");
        List<QualificationDto> availableQualifications = QualificationsDao.getQualifications();
        for (int i = 0; i < availableQualifications.size(); i++) {
            System.out.println("[" + (i + 1) + "] - " + availableQualifications.get(i).getName());
        }

        ArrayList<Long> qualificationsIDArray = new ArrayList<>();
        System.out.println("Enter qualifications for cargo (like: '1 2 3'), leave empty if none:");
        scanner.nextLine(); // consume the newline
        String[] qualificationIds = scanner.nextLine().split(" ");

        try {
            for (String qualificationId : qualificationIds) {
                int id = Integer.parseInt(qualificationId);
                qualificationsIDArray.add(availableQualifications.get(id - 1).getId());
                qualifications.add(availableQualifications.get(id - 1));
            }
        }
        catch (Exception e) {
            // ignore
        }

        System.out.println("Available employees:");
        List<EmployeeDto> employees = EmployeeDao.getEmployees(companyId, EmployeeSort.NONE, qualificationsIDArray);

        if (employees.isEmpty()) {
            System.out.println("No employees available for this route and with the specified qualifications");
            System.out.println("=".repeat(40));
            return;
        }

        for (int i = 0; i < employees.size(); i++) {
            EmployeeDto employee = employees.get(i);
            StringBuilder qualificationsT = new StringBuilder();
            for (QualificationDto qualification : employee.getQualifications()) {
                qualificationsT.append(qualification.getName()).append(", ");
            }

            System.out.println("[" + (i + 1) + "] - " + employee.getFirstName() + " " + employee.getLastName()
                    + " - " + employee.getPayRateByHour() + " - " + qualificationsT);
        }

        System.out.println("-".repeat(40));
        System.out.println("Select employee:");
        int employeeIndex = scanner.nextInt();
        EmployeeDto employee = employees.get(employeeIndex - 1);

        System.out.println("Available clients:");
        List<ClientDto> clients = ClientDao.getClients();
        for (int i = 0; i < clients.size(); i++) {
            ClientDto client = clients.get(i);
            if (client.isLegalEntity()) {
                System.out.println("[" + (i + 1) + "] - " + client.getFirstName() + " " + client.getLastName()
                        + " - Yes - " + client.getCompanyName());
            } else {
                System.out.println("[" + (i + 1) + "] - " + client.getFirstName() + " " + client.getLastName()
                        + " - No");
            }
        }
        System.out.println("-".repeat(40));
        System.out.println("Select client:");
        int clientIndex = scanner.nextInt();
        ClientDto client = clients.get(clientIndex - 1);
        scanner.nextLine(); // consume the newline

        System.out.println("Enter client price:");
        BigDecimal price = scanner.nextBigDecimal();
        scanner.nextLine(); // consume the newline

        System.out.println("Enter departure time (format YYYY-MM-DDTHH:MM:SS):");
        String departureTimeStr = scanner.nextLine();
        LocalDateTime departureTime = LocalDateTime.parse(departureTimeStr);

        System.out.println("Enter arrival time (format YYYY-MM-DDTHH:MM:SS):");
        String arrivalTimeStr = scanner.nextLine();
        LocalDateTime arrivalTime = LocalDateTime.parse(arrivalTimeStr);

        System.out.println("Enter start point:");
        String startPoint = scanner.nextLine();

        System.out.println("Enter end point:");
        String endPoint = scanner.nextLine();

        System.out.println("Enter distance:");
        int distance = scanner.nextInt();

        // Calculate costs
        BigDecimal fuelCost = vehicle.getFuelPricePerKM().multiply(BigDecimal.valueOf(distance))
                .setScale(2, RoundingMode.HALF_UP);

        Duration timeDuration = Duration.between(departureTime, arrivalTime);
        double hours = timeDuration.toMinutes() / 60.0;
        BigDecimal employeeCost = employee.getPayRateByHour().multiply(BigDecimal.valueOf(hours))
                .setScale(2, RoundingMode.HALF_UP);

        // Write to database
        RouteDto route = new RouteDto(startPoint, endPoint, distance, departureTime, arrivalTime,
                companyId, vehicle.getId(), employee.getId());
        LinkClientDto linkClientDto = new LinkClientDto(client.getId(), price);
        List<RouteExpenseDto> expenses = new ArrayList<>();
        expenses.add(new RouteExpenseDto(fuelCost, "Fuel cost"));
        expenses.add(new RouteExpenseDto(employeeCost, "Employee pay", employee.getId()));

        try {
            RouteDao.createRoute(route, linkClientDto, expenses, cargo, qualifications);
            System.out.println("Route added successfully");
        } catch (Exception e) {
            System.out.println("Failed to add route:");
            System.out.println(e.getMessage());
        }
        System.out.println("=".repeat(40));
    }

    private void listRoutes() {
        System.out.println("\n");
        System.out.println("=".repeat(40));
        System.out.println("Routes");
        System.out.println("-".repeat(40));

        List<RouteDto> routes = RouteDao.getTransits(companyId);
        if (routes.isEmpty()) {
            System.out.println("No routes");
        } else {
            System.out.println("Routes:");
            routes.forEach(route ->
                System.out.println("Route from " + route.getStartingPoint() + " to " + route.getDestinationPoint()
                        + " with distance " + route.getDistance() + " km, departure time " + route.getDepartureTime()
                        + " and arrival time " + route.getArrivalTime())
            );
        }
        System.out.println("-".repeat(40));
        System.out.println("Press enter to continue");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        System.out.println("=".repeat(40));
    }

    private void exportRoutes() {
        System.out.println("\n");
        System.out.println("=".repeat(40));
        System.out.println("Export routes");
        System.out.println("-".repeat(40));

        List<RouteDto> routes = RouteDao.getTransits(companyId);
        if (routes.isEmpty()) {
            System.out.println("No routes to export");
        } else {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save routes to file");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));

            int userSelection = fileChooser.showSaveDialog(null);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                if (!fileToSave.getAbsolutePath().endsWith(".txt")) {
                    fileToSave = new File(fileToSave + ".txt");
                }

                try (FileWriter fileWriter = new FileWriter(fileToSave)) {
                    fileWriter.write("Routes:\n");
                    routes.forEach(route ->
                            {
                                try {
                                    fileWriter.write("- Route from " + route.getStartingPoint() + " to " + route.getDestinationPoint()
                                            + " with distance " + route.getDistance() + " km, departure time " + route.getDepartureTime()
                                            + " and arrival time " + route.getArrivalTime() + "\n");
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                    );
                    System.out.println("File saved: " + fileToSave.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("-".repeat(40));
        System.out.println("Press enter to continue");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        System.out.println("=".repeat(40));
    }
}
