package bstefanov.transportOrg.service;

import bstefanov.transportOrg.dao.EmployeeDao;
import bstefanov.transportOrg.dao.QualificationsDao;
import bstefanov.transportOrg.data.EmployeeSort;
import bstefanov.transportOrg.dto.EmployeeDto;
import bstefanov.transportOrg.dto.QualificationDto;
import jakarta.validation.ConstraintViolationException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeService {
    private final long companyId;
    private EmployeeSort employeeSort = EmployeeSort.NONE;
    private ArrayList<Long> qualificationFilterIds = new ArrayList<>();

    public EmployeeService(long companyId) {
        this.companyId = companyId;
    }

    public void showMenu() {
        boolean shouldExit = false;
        do {
            System.out.println("\n");
            System.out.println("=".repeat(40));
            System.out.println("Employee menu:");
            System.out.println("-".repeat(40));
            System.out.println("1. Add employee");
            System.out.println("2. List employees");
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
                        addEmployee();
                        break;
                    case 2:
                        System.out.println("=".repeat(40));
                        listEmployees();
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

    private void addEmployee() {
        System.out.println("\n");
        System.out.println("=".repeat(40));
        System.out.println("Add employee");
        System.out.println("-".repeat(40));

        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("First name:");
            String firstName = scanner.nextLine();

            System.out.println("Last name:");
            String lastName = scanner.nextLine();

            System.out.println("Pay rate by hour:");
            BigDecimal payRateByHour = scanner.nextBigDecimal();

            List<QualificationDto> qualifications = new ArrayList<>();
            System.out.println("Available qualifications:");
            List<QualificationDto> availableQualifications = QualificationsDao.getQualifications();
            for (int i = 0; i < availableQualifications.size(); i++) {
                System.out.println("[" + (i + 1) + "] - " + availableQualifications.get(i).getName());
            }

            System.out.println("Enter qualifications (like: '1 2 3'), leave empty if none:");
            scanner.nextLine(); // consume the newline
            String[] qualificationIds = scanner.nextLine().split(" ");

            try {
                for (String qualificationId : qualificationIds) {
                    int id = Integer.parseInt(qualificationId);
                    qualifications.add(availableQualifications.get(id - 1));
                }
            }
            catch (Exception e) {
                // ignore
            }

            EmployeeDto employeeDto = new EmployeeDto(firstName, lastName, payRateByHour, qualifications, companyId);
            EmployeeDao.createEmployee(employeeDto);
            System.out.println("Employee added successfully!");
        } catch (ConstraintViolationException e) {
            System.out.println("Error adding employee:");
            for (var violation : e.getConstraintViolations()) {
                System.out.println("[!] - " + violation.getMessage());
            }
        }
        System.out.println("=".repeat(40));
    }

    private void listEmployees() {
        boolean shouldExit = false;
        do {
            System.out.println("\n");
            System.out.println("=".repeat(40));
            System.out.println("List of employees");
            System.out.println("-".repeat(40));
            System.out.println("Commands:");
            System.out.println("sort desc - Sort by pay rate descending");
            System.out.println("sort asc - Sort by pay rate ascending");
            System.out.println("filter set - Filter by qualifications");
            System.out.println("-".repeat(40));
            System.out.println("Syntax: [Operation] [OpID]");
            System.out.println("edit - Edit employee details");
            System.out.println("delete - Remove employee");
            System.out.println(" ");
            System.out.println("0 - Back");
            System.out.println("-".repeat(40));
            System.out.println("Employees (OpID - Name - Pay rate by hour - Qualifications):");

            List<EmployeeDto> employees = EmployeeDao.getEmployees(companyId, employeeSort, qualificationFilterIds);
            for (int i = 0; i < employees.size(); i++) {
                EmployeeDto employee = employees.get(i);
                StringBuilder qualifications = new StringBuilder();
                for (QualificationDto qualification : employee.getQualifications()) {
                    qualifications.append(qualification.getName()).append(", ");
                }

                System.out.println("[" + (i + 1) + "] - " + employee.getFirstName() + " " + employee.getLastName()
                        + " - " + employee.getPayRateByHour() + " - " + qualifications);
            }

            System.out.println("-".repeat(40));
            System.out.println("Waiting for input:");

            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String[] splitInput = input.split(" ");

            if (splitInput.length == 2) {
                int opId = 0;
                try {
                    opId = Integer.parseInt(splitInput[1]) - 1;
                } catch (Exception e) {
                    // ignore
                }
                EmployeeDto employee = null;
                try {
                    employee = employees.get(opId);
                } catch (Exception e) {
                    // ignore
                }

                switch (splitInput[0]) {
                    case "sort":
                        switch (splitInput[1]) {
                            case "asc":
                                employeeSort = EmployeeSort.PROFIT_ASC;
                                System.out.println("=".repeat(40));
                                break;
                            case "desc":
                                employeeSort = EmployeeSort.PROFIT_DESC;
                                System.out.println("=".repeat(40));
                                break;
                            default:
                                System.out.println("Invalid sort operation, try again");
                                System.out.println("=".repeat(40));
                        }
                        break;
                    case "filter":
                        if (splitInput[1].equals("set")) {
                            System.out.println("-".repeat(40));
                            System.out.println("Available qualifications:");
                            List<QualificationDto> availableQualifications = QualificationsDao.getQualifications();
                            for (int i = 0; i < availableQualifications.size(); i++) {
                                System.out.println("[" + (i + 1) + "] - " + availableQualifications.get(i).getName());
                            }

                            System.out.println("Enter qualifications (like: '1 2 3'), leave empty to clear filter:");
                            String[] qualificationIds = scanner.nextLine().split(" ");

                            qualificationFilterIds.clear();
                            try {
                                for (String qualificationId : qualificationIds) {
                                    int id = Integer.parseInt(qualificationId);
                                    qualificationFilterIds.add(availableQualifications.get(id - 1).getId());
                                }
                            }
                            catch (Exception e) {
                                // ignore
                            }
                        } else {
                            System.out.println("Invalid filter operation, try again");
                        }
                        System.out.println("=".repeat(40));
                        break;
                    case "edit":
                        assert employee != null;
                        editEmployee(employee);
                        System.out.println("=".repeat(40));
                        break;
                    case "delete":
                        assert employee != null;
                        String name = employee.getFirstName() + " " + employee.getLastName();
                        EmployeeDao.deleteEmployee(employee.getId());
                        System.out.println("Employee " + name + " was successfully removed!");
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

    private void editEmployee(EmployeeDto employee) {
        System.out.println("\n");
        System.out.println("=".repeat(40));
        System.out.println("Edit employee " + employee.getFirstName() + " " + employee.getLastName() + ":");
        System.out.println("-".repeat(40));

        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter new first name (leave blank for current: " + employee.getFirstName() + "):");
            String firstName = scanner.nextLine();
            if (!firstName.isBlank()) {
                employee.setFirstName(firstName);
            }

            System.out.println("Enter new last name (leave blank for current: " + employee.getLastName() + "):");
            String lastName = scanner.nextLine();
            if (!lastName.isBlank()) {
                employee.setLastName(lastName);
            }

            System.out.println("Enter new pay rate by hour (leave blank for current: " + employee.getPayRateByHour() + "):");
            String payRateByHour = scanner.nextLine();
            if (payRateByHour != null && !payRateByHour.isBlank()) {
                employee.setPayRateByHour(new BigDecimal(payRateByHour));
            }

            List<QualificationDto> qualifications = new ArrayList<>();
            System.out.println("Available qualifications:");
            List<QualificationDto> availableQualifications = QualificationsDao.getQualifications();
            for (int i = 0; i < availableQualifications.size(); i++) {
                System.out.println("[" + (i + 1) + "] - " + availableQualifications.get(i).getName());
            }

            System.out.println("Enter qualifications (like: '1 2 3'), leave empty if none:");
            String[] qualificationIds = scanner.nextLine().split(" ");

            try {
                for (String qualificationId : qualificationIds) {
                    int id = Integer.parseInt(qualificationId);
                    qualifications.add(availableQualifications.get(id - 1));
                }
            }
            catch (Exception e) {
                // ignore
            }

            employee.setQualifications(qualifications);
            EmployeeDao.updateEmployee(employee);
            System.out.println("Employee updated successfully!");
        } catch (ConstraintViolationException e) {
            System.out.println("Error updating employee:");
            for (var violation : e.getConstraintViolations()) {
                System.out.println("[!] - " + violation.getMessage());
            }
        }
        System.out.println("=".repeat(40));
    }
}
