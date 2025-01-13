package bstefanov.transportOrg.service;

import java.util.Scanner;

public class InteractionService {

    public InteractionService() {
    }

    public void showMenu() {
        boolean shouldExit = false;
        do {
            System.out.println("\n");
            System.out.println("=".repeat(40));
            System.out.println("Application main menu:");
            System.out.println("-".repeat(40));
            System.out.println("1. Manage companies");
            System.out.println("2. Manage clients");
            System.out.println("3. Manage qualifications");
            System.out.println(" ");
            System.out.println("0. Exit application");
            System.out.println("-".repeat(40));

            try {
                System.out.println("Waiting for input:");
                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1: {
                        System.out.println("=".repeat(40));
                        CompanyService companyService = new CompanyService();
                        companyService.showMenu();
                        break;
                    }
                    case 2: {
                        System.out.println("=".repeat(40));
                        ClientService clientService = new ClientService();
                        clientService.showMenu();
                        break;
                    }
                    case 3: {
                        System.out.println("=".repeat(40));
                        QualificationsService qualificationsService = new QualificationsService();
                        qualificationsService.showMenu();
                        break;
                    }
                    case 0: {
                        System.out.println("Exiting...");
                        shouldExit = true;
                        System.out.println("=".repeat(40));
                        break;
                    }
                    default: {
                        System.out.println("Invalid choice, try again");
                        System.out.println("=".repeat(40));
                    }
                }
            } catch (Exception e) {
                System.out.println("Invalid input, try again");
                System.out.println("=".repeat(40));
            }
        } while (!shouldExit);
    }
}
