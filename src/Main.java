import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int choice;

        // menu
        do {
            System.out.println("\n==== Munster Motor Insurance ====");
            System.out.println("1. Add Customer");
            System.out.println("2. Add Vehicle");
            System.out.println("3. Create Quotation");
            System.out.println("4. Create Policy");
            System.out.println("5. Generate Report");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            switch (choice) {

                case 1:
                    String firstName = validateName(scanner, "First Name");
                    String surname = validateName(scanner, "Surname");
                    String address = validateAddress(scanner);
                    String county = validateCounty(scanner);
                    String dob = validateDOB(scanner);
                    String phone = validatePhone(scanner);
                    String email = validateEmail(scanner);

                    System.out.print("Enter Gender (M/F): ");
                    String genderInput = scanner.nextLine().trim().toUpperCase();

                    boolean gender;
                    if (genderInput.equals("M")) {
                        gender = true;
                    } else if (genderInput.equals("F")) {
                        gender = false;
                    } else {
                        System.out.println("Invalid input. Please enter M or F.");
                        return; // or repeat input using loop
                    }

                    Customer customer = new Customer(
                            firstName, surname, address,
                            county, dob, gender,
                            phone, email);
                    customer.saveToFile();

                    System.out.println("\nCustomer Created Successfully!");
                    System.out.println(customer.getCustomerDetails());
                    break;

                case 2:
                    System.out.print("Add V21ehicle: ");
                    String reg = scanner.nextLine();

                    System.out.print("Enter Make: ");
                    String make = scanner.nextLine();

                    System.out.print("Enter Model: ");
                    String model = scanner.nextLine();

                    System.out.print("Enter Year: ");
                    int year = scanner.nextInt();
                    scanner.nextLine(); // clear buffer

                    Vehicle vehicle = new Vehicle(reg, make, model, year);

                    System.out.println("\nVehicle Created!");
                    System.out.println(vehicle.getDetails());
                    break;

                       /*

                case 3:
                    System.out.print("Enter quotation amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine(); // clear buffer

                    Quotation quotation = new Quotation(amount);
                    System.out.println("\nQuotation Created!");
                    System.out.println(quotation.getQuotationDetails());
                    break;


                case 4:
                    System.out.print("Enter policy premium: ");
                    double premium = scanner.nextDouble();
                    scanner.nextLine(); // clear buffer

                    Policy policy = new Policy(premium);
                    System.out.println("\nPolicy Created!");
                    System.out.println(policy.getPolicyDetails());
                    break;

                case 5:
                    Reports report = new Reports();
                    report.generateReport();
                    break;

                        */
                case 6:
                    System.out.println("Exiting system...");
                    break;

                default:
                    System.out.println("Invalid option.");
            }

        } while (choice != 5);

        scanner.close();
    }

    // Validation methods

    private static String validateName(Scanner scanner, String fieldName) {
        String input;

        while (true) {
            System.out.print("Enter " + fieldName + ": ");
            input = scanner.nextLine().trim();

            if (!input.matches("[A-Za-z]+")) {
                System.out.println(fieldName + " cannot contain numbers or special characters.");
                continue;
            }

            if (!Character.isUpperCase(input.charAt(0))) {
                System.out.println(fieldName + " must start with a capital letter.");
                continue;
            }

            if (input.length() < 2 || input.length() > 20) {
                System.out.println(fieldName + " must be between 2 and 20 characters.");
                continue;
            }

            return input;
        }
    }

    private static String validateAddress(Scanner scanner) {
        String input;
        while (true) {
            System.out.print("Enter Address: ");
            input = scanner.nextLine();

            if (input.matches("[a-zA-Z0-9\\s]{5,50}")) {
                return input;
            } else {
                System.out.println("Invalid Address (5–50 letters/numbers only).");
            }
        }
    }
    private static String validateCounty(Scanner scanner) {
        String input;
        while (true) {
            System.out.print("Enter County (Example: Roscrea co Tipperary): ");
            input = scanner.nextLine();

            if (input.matches("[A-Z][a-zA-Z]+\\sco\\s[A-Z][a-zA-Z]+")) {
                return input;
            } else {
                System.out.println("Invalid County format.");
            }
        }
    }
    private static String validateDOB(Scanner scanner) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (true) {
            System.out.print("Enter Date of Birth (dd/MM/yyyy): ");
            String input = scanner.nextLine();

            try {
                LocalDate dob = LocalDate.parse(input, formatter);

                if (dob.isAfter(LocalDate.now())) {
                    System.out.println("Date of Birth cannot be in the future.");
                    continue;
                }

                return dob.toString();

            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Please enter a real calendar date.");
            }
        }
    }

    private static boolean validateGender(Scanner scanner) {
        while (true) {
            System.out.print("Enter Gender (M/F): ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("M")) {
                return true;
            } else if (input.equals("F")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter M or F only.");
            }
        }
    }

    private static String validatePhone(Scanner scanner) {
        String countryCode;
        String number;

        while (true) {
            System.out.println("Select Country Code:");
            System.out.println("1. +353 (Ireland)");
            System.out.println("2. +44 (UK)");
            System.out.print("Choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                countryCode = "+353";
            } else if (choice == 2) {
                countryCode = "+44";
            } else {
                System.out.println("Invalid choice.");
                continue;
            }

            System.out.print("Enter Phone Number (7–10 digits): ");
            number = scanner.nextLine();

            if (number.matches("\\d{7,10}")) {
                return countryCode + number;
            } else {
                System.out.println("Invalid phone number.");
            }
        }
    }
    private static String validateEmail(Scanner scanner) {
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        while (true) {
            System.out.print("Enter Email: ");
            String input = scanner.nextLine().trim();

            if (input.matches(emailPattern)) {
                return input;
            } else {
                System.out.println("Invalid email format (example: name@email.com)");
            }
        }
    }





}
