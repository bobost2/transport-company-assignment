package bstefanov.transportOrg.service;

import bstefanov.transportOrg.dao.ClientDao;
import bstefanov.transportOrg.dao.QualificationsDao;
import bstefanov.transportOrg.dto.ClientDto;
import bstefanov.transportOrg.dto.QualificationDto;
import jakarta.validation.ConstraintViolationException;

import java.util.List;
import java.util.Scanner;

public class ClientService {
    public ClientService() {
    }

    public void showMenu() {
        boolean shouldExit = false;
        do {
            System.out.println("\n");
            System.out.println("=".repeat(40));
            System.out.println("Client menu:");
            System.out.println("-".repeat(40));
            System.out.println("1. Create client");
            System.out.println("2. List clients");
            System.out.println(" ");
            System.out.println("0. Go back");
            System.out.println("-".repeat(40));

            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Waiting for input:");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("=".repeat(40));
                        createClient();
                        break;
                    case 2:
                        System.out.println("=".repeat(40));
                        listClients();
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

    private void createClient() {
        System.out.println("\n");
        System.out.println("=".repeat(40));
        System.out.println("Create client");
        System.out.println("-".repeat(40));

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter first name:");
            String firstName = scanner.nextLine();

            System.out.println("Enter last name:");
            String lastName = scanner.nextLine();

            System.out.println("Is this client a legal entity? (yes/no)");
            String isLegalEntity = scanner.nextLine();

            ClientDto clientDto;

            if (isLegalEntity.equals("yes")) {
                System.out.println("Enter company name:");
                String companyName = scanner.nextLine();
                clientDto = new ClientDto(firstName, lastName, companyName);
            }
            else {
                clientDto = new ClientDto(firstName, lastName);
            }

            ClientDao.createClient(clientDto);
            System.out.println("Client created successfully!");
        } catch (ConstraintViolationException e) {
            System.out.println("Error creating client:");
            for (var violation : e.getConstraintViolations()) {
                System.out.println("[!] - " + violation.getMessage());
            }
        }
        System.out.println("=".repeat(40));
    }

    private void listClients() {
        boolean shouldExit = false;
        do {
            System.out.println("\n");
            System.out.println("=".repeat(40));
            System.out.println("List clients");
            System.out.println("-".repeat(40));
            System.out.println("Syntax: [Operation] [OpID]");
            System.out.println("edit - Edit client details");
            System.out.println("manage - Manage client");
            System.out.println("delete - Delete client");
            System.out.println(" ");
            System.out.println("0 - Back");
            System.out.println("-".repeat(40));
            System.out.println("Clients (OpID - Name - IsLegalEntity - Company):");

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
            System.out.println("Waiting for input:");

            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String[] splitInput = input.split(" ");

            if (splitInput.length == 2) {
                ClientDto client = clients.get(Integer.parseInt(splitInput[1]) - 1);

                switch (splitInput[0]) {
                    case "manage":
                        System.out.println("Not implemented yet");
                        System.out.println("=".repeat(40));
                        break;
                    case "edit":
                        System.out.println("=".repeat(40));
                        editClient(client);
                        break;
                    case "delete":
                        System.out.println("=".repeat(40));
                        String name = client.getFirstName() + " " + client.getLastName();
                        ClientDao.deleteClient(client.getId());
                        System.out.println("Client " + name + " was successfully deleted!");
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

    private void editClient(ClientDto client) {
        System.out.println("\n");
        System.out.println("=".repeat(40));
        System.out.println("Edit client " + client.getFirstName() + " " + client.getLastName() + ":");
        System.out.println("-".repeat(40));

        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter new first name (leave blank for current: '" + client.getFirstName() + "'):");
            String newFirstName = scanner.nextLine();
            if (!newFirstName.isBlank()) {
                client.setFirstName(newFirstName);
            }

            System.out.println("Enter new last name (leave blank for current: '" + client.getLastName() + "'):");
            String newLastName = scanner.nextLine();
            if (!newLastName.isBlank()) {
                client.setLastName(newLastName);
            }

            System.out.println("Is this client a legal entity? (yes/no) (leave blank for current: '"
                    + (client.isLegalEntity() ? "yes" : "no") + "'):");
            String isLegalEntity = scanner.nextLine();
            if (!isLegalEntity.isBlank()) {
                client.setLegalEntity(isLegalEntity.equals("yes"));
            }

            if (client.isLegalEntity()) {
                System.out.println("Enter new company name (leave blank for current: '"
                        + client.getCompanyName() + "'):");
                String newCompanyName = scanner.nextLine();
                if (!newCompanyName.isBlank()) {
                    client.setCompanyName(newCompanyName);
                }
            } else {
                client.setCompanyName(null);
            }

            ClientDao.editClient(client);
            System.out.println("Client edited successfully!");
        } catch (ConstraintViolationException e) {
            System.out.println("Error editing client:");
            for (var violation : e.getConstraintViolations()) {
                System.out.println("[!] - " + violation.getMessage());
            }
        }
        System.out.println("=".repeat(40));
    }
}
