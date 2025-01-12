package bstefanov.transportOrg.service;

import bstefanov.transportOrg.dao.CompanyDao;
import bstefanov.transportOrg.data.CompanySort;
import bstefanov.transportOrg.dto.CompanyListDto;
import bstefanov.transportOrg.dto.CreateCompanyDto;
import jakarta.validation.ConstraintViolationException;

import java.util.List;
import java.util.Scanner;

public class InteractionService {

    public InteractionService() {
    }

    public void showMenu() {
        System.out.println("\n");
        System.out.println("=".repeat(40));
        System.out.println("Company menu:");
        System.out.println("-".repeat(40));
        System.out.println("1. Create company");
        System.out.println("2. Select company");
        System.out.println(" ");
        System.out.println("0. Exit");
        System.out.println("-".repeat(40));

        try {
            System.out.println("Waiting for input:");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();

            System.out.println("=".repeat(40));

            switch (choice) {
                case 1:
                    createCompany();
                    break;
                case 2:
                    listCompanies();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice, try again");
                    showMenu();
            }
        } catch (Exception e) {
            System.out.println("Invalid input, try again");
            System.out.println("=".repeat(40));
            showMenu();
        }
    }

    private void createCompany() {
        try {
            System.out.println("\n");
            System.out.println("=".repeat(40));
            System.out.println("Create company");
            System.out.println("-".repeat(40));

            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter company name:");
            String name = scanner.nextLine();

            System.out.println("Enter company address:");
            String address = scanner.nextLine();

            CreateCompanyDto createCompanyDto = new CreateCompanyDto(name, address);

            CompanyDao.createCompany(createCompanyDto);

            System.out.println("Company successfully created!");
            System.out.println("=".repeat(40));
        } catch (ConstraintViolationException e) {
            System.out.println("Error creating company:");
            for (var violation : e.getConstraintViolations()) {
                System.out.println("[!] - " + violation.getMessage());
            }
            System.out.println("=".repeat(40));
        }

        showMenu();
    }

    private void listCompanies() {
        listCompanies(CompanySort.NONE);
    }

    private void listCompanies(CompanySort sort) {
        System.out.println("\n");
        System.out.println("=".repeat(40));
        System.out.println("List companies");
        System.out.println("-".repeat(40));
        System.out.println("back - go back");
        System.out.println("sort name asc - sort by name ascending");
        System.out.println("sort name desc - sort by name descending");
        System.out.println("sort profit asc - sort by profit ascending");
        System.out.println("sort profit desc - sort by profit descending");
        System.out.println("-".repeat(40));
        System.out.println("Syntax: [id] [operation]");
        System.out.println("Operations:");
        System.out.println("manage - manage company");
        System.out.println("edit - edit company details");
        System.out.println("delete - delete company");
        System.out.println("-".repeat(40));
        System.out.println("Companies ([ID] - Name - Address - Profit):");

        List<CompanyListDto> companies = CompanyDao.getCompanies();

        if (sort != CompanySort.NONE) {
            companies.sort((a, b) -> {
                return switch (sort) {
                    case NAME_ASC -> a.getName().compareTo(b.getName());
                    case NAME_DESC -> b.getName().compareTo(a.getName());
                    case PROFIT_ASC -> a.getProfit().compareTo(b.getProfit());
                    case PROFIT_DESC -> b.getProfit().compareTo(a.getProfit());
                    default -> 0;
                };
            });
        }

        for (int i = 0; i < companies.size(); i++) {
            CompanyListDto company = companies.get(i);
            System.out.println("[" + (i + 1) + "] - " + company.getName() + " - "
                    + company.getAddress() + " - " + company.getProfit());
        }

        boolean valid = true;

        System.out.println("-".repeat(40));
        System.out.println("Waiting for input:");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        String[] outputSplit = input.split(" ");

        if (input.equals("back")) {
            System.out.println("=".repeat(40));
            showMenu();
        } else if (outputSplit.length == 3 && outputSplit[0].equals("sort")) {
            if (outputSplit[1].equals("name")) {
                if (outputSplit[2].equals("asc")) {
                    listCompanies(CompanySort.NAME_ASC);
                } else if (outputSplit[2].equals("desc")) {
                    listCompanies(CompanySort.NAME_DESC);
                } else {
                    valid = false;
                }
            } else if (outputSplit[1].equals("profit")) {
                if (outputSplit[2].equals("asc")) {
                    listCompanies(CompanySort.PROFIT_ASC);
                } else if (outputSplit[2].equals("desc")) {
                    listCompanies(CompanySort.PROFIT_DESC);
                } else {
                    valid = false;
                }
            }
        } else if (outputSplit.length == 2) {
            switch (outputSplit[1])
            {
                case "manage":
                    // Open management menu
                    //manageCompany(Long.parseLong(outputSplit[0]));
                    break;
                case "edit":
                    editCompany(companies.get(Integer.parseInt(outputSplit[0]) - 1));
                    listCompanies();
                    break;
                case "delete":
                    CompanyListDto company = companies.get(Integer.parseInt(outputSplit[0]) - 1);
                    String name = company.getName();
                    CompanyDao.deleteCompany(company.getId());
                    System.out.println("Company " + name + " was successfully deleted!");
                    listCompanies();
                    break;
                default:
                    valid = false;
            }
        }
        else {
            valid = false;
        }

        if (!valid) {
            System.out.println("Invalid input, try again");
            System.out.println("=".repeat(40));
            listCompanies();
        }
    }

    private void editCompany(CompanyListDto company) {
        System.out.println("\n");
        System.out.println("=".repeat(40));
        System.out.println("Edit company");
        System.out.println("-".repeat(40));

        try {
            System.out.println("Editing company: " + company.getName());
            System.out.println("Enter new name:");
            Scanner scanner = new Scanner(System.in);
            company.setName(scanner.nextLine());

            System.out.println("Enter new address:");
            company.setAddress(scanner.nextLine());

            CompanyDao.editCompany(company);

            System.out.println("Company successfully updated!");
            System.out.println("=".repeat(40));
        } catch (ConstraintViolationException e) {
            System.out.println("Error editing company:");
            for (var violation : e.getConstraintViolations()) {
                System.out.println("[!] - " + violation.getMessage());
            }
            System.out.println("=".repeat(40));
        }
    }
}
