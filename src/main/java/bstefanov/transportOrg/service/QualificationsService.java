package bstefanov.transportOrg.service;

import bstefanov.transportOrg.dao.QualificationsDao;
import bstefanov.transportOrg.dto.QualificationDto;
import jakarta.validation.ConstraintViolationException;

import java.util.List;
import java.util.Scanner;

public class QualificationsService {
    public QualificationsService() {
    }

    public void showMenu() {
        boolean shouldExit = false;
        do {
            System.out.println("\n");
            System.out.println("=".repeat(40));
            System.out.println("Qualifications menu:");
            System.out.println("-".repeat(40));
            System.out.println("1. Add qualification");
            System.out.println("2. List qualifications");
            System.out.println(" ");
            System.out.println("0. Back");
            System.out.println("-".repeat(40));

            try {
                System.out.println("Waiting for input:");
                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();

                System.out.println("=".repeat(40));

                switch (choice) {
                    case 1:
                        addQualification();
                        break;
                    case 2:
                        listQualifications();
                        break;
                    case 0:
                        System.out.println("Exiting...");
                        shouldExit = true;
                        break;
                    default:
                        System.out.println("Invalid choice, try again");
                }
            } catch (Exception e) {
                System.out.println("Invalid input, try again");
            }
        } while (!shouldExit);
    }

    private void addQualification() {
        System.out.println("\n");
        System.out.println("=".repeat(40));
        System.out.println("Add qualification");
        System.out.println("-".repeat(40));

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter qualification:");
            String qualification = scanner.nextLine();

            QualificationsDao.addQualification(qualification);
            System.out.println("Qualification added successfully!");
        } catch (ConstraintViolationException e) {
            System.out.println("Error creating qualification:");
            for (var violation : e.getConstraintViolations()) {
                System.out.println("[!] - " + violation.getMessage());
            }
            System.out.println("=".repeat(40));
        }
    }

    private void listQualifications() {
        boolean shouldExit = false;
        do {
            System.out.println("\n");
            System.out.println("=".repeat(40));
            System.out.println("Qualifications:");
            System.out.println("-".repeat(40));
            System.out.println("Syntax: [OpID] [Operation]");
            System.out.println("edit - Edit qualification");
            System.out.println("delete - Delete qualification");
            System.out.println("-".repeat(40));
            System.out.println("Qualifications (OpID - Name):");

            List<QualificationDto> qualifications = QualificationsDao.getQualifications();

            for (int i = 0; i < qualifications.size(); i++) {
                System.out.println("[" + (i + 1) + "] - " + qualifications.get(i).getName());
            }

            System.out.println("-".repeat(40));
            System.out.println("Waiting for input:");

            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String[] splitInput = input.split(" ");

            if (splitInput.length == 2) {
                switch (splitInput[1]) {
                    case "edit":
                        editQualification(qualifications.get(Integer.parseInt(splitInput[0]) - 1));
                        break;
                    case "delete":
                        QualificationDto qualification = qualifications.get(Integer.parseInt(splitInput[0]) - 1);
                        String name = qualification.getName();
                        QualificationsDao.deleteQualification(qualification.getId());
                        System.out.println("Qualification " + name + " was successfully deleted!");
                        break;
                    default:
                        System.out.println("Invalid operation, try again");
                }
            } else if (input.equals("0")) {
                shouldExit = true;
            } else {
                System.out.println("Invalid input, try again");
            }
            System.out.println("=".repeat(40));
        } while (!shouldExit);
    }

    private void editQualification(QualificationDto qualification) {
        System.out.println("\n");
        System.out.println("=".repeat(40));
        System.out.println("Edit qualification " + qualification.getName() + ":");
        System.out.println("-".repeat(40));

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter new qualification:");
            String newQualification = scanner.nextLine();

            qualification.setName(newQualification);
            QualificationsDao.updateQualification(qualification);
            System.out.println("Qualification updated successfully!");
        } catch (ConstraintViolationException e) {
            System.out.println("Error updating qualification:");
            for (var violation : e.getConstraintViolations()) {
                System.out.println("[!] - " + violation.getMessage());
            }
        }
        System.out.println("=".repeat(40));
    }
}