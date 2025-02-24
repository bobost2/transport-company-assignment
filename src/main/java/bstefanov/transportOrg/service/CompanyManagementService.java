package bstefanov.transportOrg.service;

import bstefanov.transportOrg.dao.ClientDao;
import bstefanov.transportOrg.dto.ClientDto;
import bstefanov.transportOrg.dto.ClientInvoiceDto;
import bstefanov.transportOrg.dto.CompanyListEntityDto;

import java.util.List;
import java.util.Scanner;

public class CompanyManagementService {
    final private CompanyListEntityDto companyListEntity;

    public CompanyManagementService(CompanyListEntityDto companyListEntity) {
        this.companyListEntity = companyListEntity;
    }

    public void showMenu() {
        boolean shouldExit = false;
        do {
            System.out.println("\n");
            System.out.println("=".repeat(40));
            System.out.println(companyListEntity.getName() + " - Manage company:");
            System.out.println("-".repeat(40));
            System.out.println("1. Manage employees");
            System.out.println("2. Manage vehicles");
            System.out.println("3. Manage routes"); // it was routes, not transit - didn't use the correct word, fuck it I'm not renaming the tables and everything else
            System.out.println("4. Check unpaid invoices");
            System.out.println("5. Create report");
            System.out.println(" ");
            System.out.println("0 - Back");
            System.out.println("-".repeat(40));

            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Waiting for input:");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("=".repeat(40));
                        EmployeeService employeeService = new EmployeeService(companyListEntity.getId());
                        employeeService.showMenu();
                        break;
                    case 2:
                        System.out.println("=".repeat(40));
                        VehicleService vehicleService = new VehicleService(companyListEntity.getId());
                        vehicleService.showMenu();
                        break;
                    case 3:
                        System.out.println("=".repeat(40));
                        RoutesService routesService = new RoutesService(companyListEntity.getId());
                        routesService.showMenu();
                        break;
                    case 4:
                        System.out.println("=".repeat(40));
                        showUnpaidInvoices();
                        break;
                    case 5:
                        System.out.println("=".repeat(40));
                        ReportingService reportingService = new ReportingService(companyListEntity.getId());
                        reportingService.showMenu();
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

    private void showUnpaidInvoices() {
        System.out.println("\n");
        System.out.println("=".repeat(40));
        System.out.println("Unpaid invoices");
        System.out.println("-".repeat(40));

        List<ClientInvoiceDto> unpaidInvoices = ClientDao.getClientUnpaidInvoices(companyListEntity.getId());
        if (unpaidInvoices.isEmpty()) {
            System.out.println("No unpaid invoices");
        } else {
            System.out.println("Unpaid invoices:");

            unpaidInvoices.forEach(invoice ->
                System.out.println("Client " + invoice.getClient().getFirstName() + " "
                        + invoice.getClient().getLastName() + " has unpaid invoice for " + invoice.getAmount())
            );
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Press enter to continue");
        scanner.nextLine();

        System.out.println("=".repeat(40));
    }
}
