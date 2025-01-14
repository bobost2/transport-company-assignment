package bstefanov.transportOrg.service;

import bstefanov.transportOrg.dao.EmployeeDao;
import bstefanov.transportOrg.dao.RouteDao;
import bstefanov.transportOrg.dto.DriverProfitsDto;
import bstefanov.transportOrg.dto.EmployeeDto;
import bstefanov.transportOrg.dto.RouteCostDto;
import bstefanov.transportOrg.dto.RouteDto;

import java.util.List;
import java.util.Scanner;

public class ReportingService {
    final private long companyId;

    public ReportingService(long companyId) {
        this.companyId = companyId;
    }

    public void showMenu() {
        boolean shouldExit = false;
        do {
            System.out.println("\n");
            System.out.println("=".repeat(40));
            System.out.println("Reporting menu:");
            System.out.println("-".repeat(40));
            System.out.println("1. Amount of routes done");
            System.out.println("2. Costs of routes");
            System.out.println("3. Employee - route");
            System.out.println("4. Company profits in time range");
            System.out.println("5. Each driver profits");
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
                        amountOfRoutesDone();
                        break;
                    case 2:
                        System.out.println("=".repeat(40));
                        costsOfRoutes();
                        break;
                    case 3:
                        System.out.println("=".repeat(40));
                        employeeRoute();
                        break;
                    case 4:
                        System.out.println("=".repeat(40));
                        companyProfits();
                        break;
                    case 5:
                        System.out.println("=".repeat(40));
                        driverProfits();
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

    private void amountOfRoutesDone() {
        System.out.println("\n");
        System.out.println("=".repeat(40));

        List<RouteDto> routes = RouteDao.getTransits(companyId);

        System.out.println("Amount of routes done - " + routes.size());
        System.out.println("-".repeat(40));
        System.out.println("Press enter to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        System.out.println("=".repeat(40));
    }

    private void costsOfRoutes() {
        System.out.println("\n");
        System.out.println("=".repeat(40));
        System.out.println("Costs of routes");
        System.out.println("-".repeat(40));

        List<RouteCostDto> routes = RouteDao.getTransitCost(companyId);
        routes.forEach(route -> {
            RouteDto routeDto = RouteDao.getTransit(route.getRouteId());
            System.out.println("Route from " + routeDto.getStartingPoint() + " to " + routeDto.getDestinationPoint() +
                    " with distance of " + routeDto.getDistance() + "km has cost of " + route.getCosts());
        });

        System.out.println("-".repeat(40));
        System.out.println("Press enter to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        System.out.println("=".repeat(40));
    }

    private void employeeRoute() {
        System.out.println("\n");
        System.out.println("=".repeat(40));
        System.out.println("Employee - route");
        System.out.println("-".repeat(40));

        List<RouteDto> routes = RouteDao.getTransits(companyId);
        routes.forEach(route -> {
            EmployeeDto employee = EmployeeDao.getEmployee(route.getEmployeeId());
            System.out.println("Employee " + employee.getFirstName() + " " + employee.getLastName()
                    + " has done route from " + route.getStartingPoint() + " to " + route.getDestinationPoint());
        });

        System.out.println("-".repeat(40));
        System.out.println("Press enter to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        System.out.println("=".repeat(40));
    }

    private void companyProfits() {
        System.out.println("\n");
        System.out.println("=".repeat(40));
        System.out.println("Company profits in time range");
        System.out.println("-".repeat(40));

        // TODO
        System.out.println("To do (not going to be done probably) :D");

        System.out.println("-".repeat(40));
        System.out.println("Press enter to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        System.out.println("=".repeat(40));
    }

    private void driverProfits() {
        System.out.println("\n");
        System.out.println("=".repeat(40));
        System.out.println("Each driver profits");
        System.out.println("-".repeat(40));

        List<DriverProfitsDto> driverProfits = EmployeeDao.getDriverProfits(companyId);
        driverProfits.forEach(driver -> {
            System.out.println("Driver " + driver.getFirstName() + " "
                    + driver.getLastName() + " has profits of " + driver.getProfits());
        });

        System.out.println("-".repeat(40));
        System.out.println("Press enter to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        System.out.println("=".repeat(40));
    }
}
