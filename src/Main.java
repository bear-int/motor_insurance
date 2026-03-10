import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Main {

    public static void main(String[] args) {

        Customer.initializeCustomerID();
        Quotation.initializeQuotationID();
        Policy.initializePolicyID();
        Vehicle.initializeVehicleID();
        Scanner scanner = new Scanner(System.in);

        int choice;

        // menu
        do {
            System.out.println("\n==== Munster Motor Insurance ====");
            System.out.println("1. Add Customer");
            System.out.println("2. Search/Edit Customer");
            System.out.println("3. Add Vehicle");
            System.out.println("4. Search/Edit Vehicle");
            System.out.println("5. Generate Quotation");
            System.out.println("6. Search Quotation");
            System.out.println("7. Create Policy / Certificate");
            System.out.println("8. Extend Policy");
            System.out.println("9. Generate Report");
            System.out.println("10. Exit");
            System.out.print("Enter choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            switch (choice) {

                case 1:
                    String firstName = validateName(scanner, "First Name");
                    String surname = validateName(scanner, "Surname");
                    String address = validateAddress(scanner);
                    String county = validateCounty(scanner);
                    String town = validateTown(scanner);
                    String dob = validateDOB(scanner);
                    String phone = validatePhone(scanner);
                    String email = validateEmail(scanner);
                    boolean gender = validateGender(scanner);

                    Customer customer = new Customer(
                            firstName, surname, address,
                            county, town,  dob, gender,
                            phone, email);
                    customer.saveToFile();

                    System.out.println("\nCustomer Created Successfully!");
                    System.out.println(customer.getCustomerDetails());
                    System.out.println("Calculated Age: " + customer.getAge());
                    break;


                case 2: // Search Customer
                    System.out.println("\nSearch By:");
                    System.out.println("1. Customer ID");
                    System.out.println("2. First Name");
                    System.out.println("3. Surname");
                    System.out.println("4. Email");
                    System.out.print("Choose option: ");

                    int searchOption = scanner.nextInt();
                    scanner.nextLine();

                    String searchValue;
                    if (searchOption == 1) {

                        System.out.print("Enter Customer ID: (example CUST-0001)");
                        searchValue = scanner.nextLine().trim().toUpperCase();

                        while (!searchValue.matches("CUST-\\d{4}")) {
                            System.out.println("Error, Enter valid customer ID (example CUST-0001)");
                            System.out.print("Enter Customer ID: ");
                            searchValue = scanner.nextLine().trim().toUpperCase();
                        }

                    } else {

                        System.out.print("Enter search value: ");
                        searchValue = scanner.nextLine().trim();
                    }

                    searchCustomer(searchOption, searchValue);
                    break;


                case 3: // Add Vehicle

                    String reg = validateRegistration(scanner);

                    System.out.print("Enter Make: ");
                    String make = scanner.nextLine();

                    System.out.print("Enter Model: ");
                    String model = scanner.nextLine();

                    System.out.print("Enter Year: ");
                    int year = scanner.nextInt();
                    scanner.nextLine();


                    System.out.println("Select Emission Level:");
                    System.out.println("1. High");
                    System.out.println("2. Medium");
                    System.out.println("3. Low");
                    System.out.print("Choice: ");

                    int emissionChoice = scanner.nextInt();
                    scanner.nextLine();

                    String emission = switch (emissionChoice) {
                        case 1 -> "High";
                        case 2 -> "Medium";
                        case 3 -> "Low";
                        default -> "";
                    };


                    Vehicle vehicle = new Vehicle(reg, make, model, year, emission);
                    vehicle.saveToFile();

                    System.out.println("\nVehicle Created!");
                    System.out.println(vehicle.getDetails());
                    break;

                case 4: {

                    System.out.println("\nSearch Vehicle By:");
                    System.out.println("1. Vehicle ID");
                    System.out.println("2. Registration");
                    System.out.println("3. Make");
                    System.out.println("4. Model");
                    System.out.print("Choose option: ");

                    int vehicleSearchOption = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter search value: ");
                    String vehicleSearchValue = scanner.nextLine().trim();

                    searchVehicle(vehicleSearchOption, vehicleSearchValue);

                    break;
                }


                case 5:

                    System.out.print("Enter Customer ID: (example CUST-0001)");
                    String custID = scanner.nextLine();

                    Customer selectedCustomer = findCustomerByID(custID);

                    if (selectedCustomer == null) {
                        System.out.println("Customer not found.");
                        break;
                    }

                    System.out.print("Enter Vehicle Registration: ");
                    String regNumber = scanner.nextLine();

                    Vehicle selectedVehicle = findVehicleByReg(regNumber);

                    if (selectedVehicle == null) {
                        System.out.println("Vehicle not found.");
                        break;
                    }

                    System.out.println("Insurance Category:");
                    System.out.println("1. Fully Comprehensive");
                    System.out.println("2. Third Party Fire and Theft");

                    int insuranceType = scanner.nextInt();
                    scanner.nextLine();

                    Quotation quotation = new Quotation(selectedCustomer, selectedVehicle, insuranceType);
                    if (quotation.getFinalPremium() == -1) {
                        System.out.println("Quotation cannot be generated for this customer.");
                        break;
                    }

                    quotation.saveToFile();

                    System.out.println("\nQUOTATION GENERATED & SAVED");
                    System.out.println(quotation.getDetails());

                    break;


                case 6:

                    System.out.print("Enter Quotation ID (e.g., QUOT-0001): ");
                    String quotationID = scanner.nextLine();

                    searchQuotation(quotationID);

                    break;
/*
                case 6:

                    System.out.print("Enter Quotation ID (e.g., QUOT-0001): ");
                    String quotationID = scanner.nextLine();

                    boolean found = false;

                    try (Scanner fileScanner = new Scanner(new java.io.File("quotations.txt"))) {

                        while (fileScanner.hasNextLine()) {

                            String line = fileScanner.nextLine();
                            String[] data = line.split(",");

                            if (data[0].equalsIgnoreCase(quotationID)) {

                                found = true;

                                System.out.println("\nQuotation Found:");
                                System.out.println(line);

                                System.out.print("\nAccept this quotation and create policy? (Y/N): ");
                                String decision = scanner.nextLine().toUpperCase();

                                if (decision.equals("Y")) {

                                    String customerID = data[1];
                                    String quotationReg = data[2];
                                    String quotationMake = data[3];
                                    String quotationModel = data[4];
                                    int quotationYear = Integer.parseInt(data[5]);
                                    double premium = Double.parseDouble(data[6]);

                                    Customer quotationCustomer = findCustomerByID(customerID);
                                    Vehicle quotationVehicle = new Vehicle(
                                            quotationReg,
                                            quotationMake,
                                            quotationModel,
                                            quotationYear
                                    );

                                    Policy policy = new Policy(quotationCustomer, quotationVehicle, premium);
                                    policy.saveToFile();

                                    System.out.println("\nPOLICY CREATED SUCCESSFULLY!");
                                    System.out.println(policy.getPolicyDetails());
                                }

                                break;
                            }
                        }

                    } catch (Exception e) {
                        System.out.println("Error reading quotations file.");
                    }

                    if (!found) {
                        System.out.println("Quotation not found.");
                    }

                    break;
*/

                case 7:

                    System.out.print("Enter Quotation ID to convert to Policy: ");
                    String quotationIDForPolicy = scanner.nextLine();

                    createPolicyFromQuotation(quotationIDForPolicy);

                    break;


                case 8:
                    extendPolicy();
                    break;


                case 9:
                    generateRenewalReport();
                    break;


                case 10:
                    System.out.println("Exiting system...");
                    break;


                default:
                    System.out.println("Invalid option.");
            }

        } while (choice != 10);

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
            System.out.print("Enter County: ");
            input = scanner.nextLine().trim();

            if (!input.matches("[A-Z][a-zA-Z]{2,30}")) {
                System.out.println("Invalid County format. Example: Tipperary");
                continue;
            }

            return input;
        }
    }

    private static String validateDOB(Scanner scanner) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (true) {
            System.out.print("Enter Date of Birth (dd/MM/yyyy): ");
            String input = scanner.nextLine();

            try {
                LocalDate dob = LocalDate.parse(input, formatter);
                LocalDate today = LocalDate.now();

                if (dob.isAfter(today)) {
                    System.out.println("Date of Birth cannot be in the future.");
                    continue;
                }

                // Calculate age
                int age = java.time.Period.between(dob, today).getYears();

                if (age < 18) {
                    System.out.println("Customer must be at least 18 years old.");
                    continue;
                }

                System.out.println("Customer Age: " + age);
                return dob.toString();  // store in ISO format (yyyy-MM-dd)

            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Please enter a real calendar date.");
            }
        }
    }

    private static boolean validateGender(Scanner scanner) {
        while (true) {
            System.out.print("Enter Gender (M/F): ");
            String genderInput = scanner.nextLine().trim().toUpperCase();

            if (genderInput.equals("M")) {
                return true;
            } else if (genderInput.equals("F")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter M or F.");
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

    public static Customer findCustomerByID(String id) {

        try (Scanner fileScanner = new Scanner(new java.io.File("customers.txt"))) {

            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();
                String[] data = line.split(",");

                // Safety check to avoid crashes
                if (data.length < 10) {
                    continue;
                }

                if (data[0].equalsIgnoreCase(id)) {

                    String firstName = data[1];
                    String surname = data[2];
                    String address = data[3];

                    // File format is Address,County,Town
                    String county = data[4];
                    String town = data[5];

                    String dob = data[6];
                    String phone = data[7];
                    String email = data[8];

                    boolean gender = data[9].equalsIgnoreCase("M");

                    return new Customer(
                            data[0],      // Customer ID
                            firstName,
                            surname,
                            address,
                            county,
                            town,
                            dob,
                            gender,
                            phone,
                            email
                    );
                }
            }

        } catch (Exception e) {
            System.out.println("Error reading customers file: " + e.getMessage());
        }

        return null;
    }

    public static Vehicle findVehicleByReg(String input) {

        try (Scanner fileScanner = new Scanner(new java.io.File("vehicle.txt"))) {

            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();
                String[] data = line.split(",");

                String vehicleID = data[0].trim();
                String reg = data[1].trim();
                String make = data[2].trim();
                String model = data[3].trim();
                int year = Integer.parseInt(data[4].trim());
                String emission = data[5].trim();

                if (reg.equalsIgnoreCase(input) || vehicleID.equalsIgnoreCase(input)) {
                    return new Vehicle(vehicleID, reg, make, model, year, emission);
                }
            }

        } catch (Exception e) {
            System.out.println("Error reading vehicle file: " + e.getMessage());
        }

        return null;
    }


    private static void searchCustomer(int option, String value) {

        boolean found = false;

        try (Scanner fileScanner = new Scanner(new java.io.File("customers.txt"))) {

            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();
                String[] data = line.split(",");

                if (data.length < 10) {
                    continue;
                }



                String id = data[0];
                String firstName = data[1];
                String surname = data[2];
                String phone = data[7];
                String email = data[8];

                boolean match = false;

                switch (option) {

                    case 1:
                        if (id.equalsIgnoreCase(value)) match = true;
                        break;

                    case 2:
                        if (firstName.equalsIgnoreCase(value)) match = true;
                        break;

                    case 3:
                        if (surname.equalsIgnoreCase(value)) match = true;
                        break;

                    case 4:
                        if (email.equalsIgnoreCase(value)) match = true;
                        break;

                    case 5:
                        if (phone.equalsIgnoreCase(value)) match = true;
                        break;
                }

                if (match) {

                    System.out.println("\nCustomer Found:");
                    System.out.println(line);

                    found = true;

                    Scanner inputScanner = new Scanner(System.in);
                    System.out.print("Would you like to edit this customer? (Y/N): ");
                    String editChoice = inputScanner.nextLine().toUpperCase();

                    if (editChoice.equals("Y")) {
                        editCustomer(data);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        if (!found) {
            System.out.println("No matching customer found.");
        }
    } // closes searchCustomer


    public static void editCustomer(String[] data) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("\n--- Edit Customer ---");

        System.out.println("1. First Name: " + data[1]);
        System.out.print("Enter new First Name (or press Enter to keep current): ");
        String firstName = scanner.nextLine();
        if (!firstName.isEmpty()) data[1] = firstName;

        System.out.println("2. Surname: " + data[2]);
        System.out.print("Enter new Surname (or press Enter to keep current): ");
        String surname = scanner.nextLine();
        if (!surname.isEmpty()) data[2] = surname;

        System.out.println("3. Address: " + data[3]);
        System.out.print("Enter new Address (or press Enter to keep current): ");
        String address = scanner.nextLine();
        if (!address.isEmpty()) data[3] = address;

        System.out.println("4. County: " + data[4]);
        System.out.print("Enter new County (or press Enter to keep current): ");
        String county = scanner.nextLine();
        if (!county.isEmpty()) data[4] = county;

        System.out.println("5. Phone: " + data[7]);
        System.out.print("Enter new Phone (or press Enter to keep current): ");
        String phone = scanner.nextLine();
        if (!phone.isEmpty()) data[7] = phone;

        System.out.println("6. Email: " + data[8]);
        System.out.print("Enter new Email (or press Enter to keep current): ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) data[8] = email;

        updateCustomerFile(data);

        System.out.println("Customer updated successfully.");
    }

    public static void updateCustomerFile(String[] updatedData) {

        try {

            java.io.File inputFile = new java.io.File("customers.txt");
            java.io.File tempFile = new java.io.File("customers_temp.txt");

            Scanner fileScanner = new Scanner(inputFile);
            java.io.PrintWriter writer = new java.io.PrintWriter(tempFile);

            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();
                String[] currentData = line.split(",");

                if (currentData[0].equalsIgnoreCase(updatedData[0])) {
                    writer.println(String.join(",", updatedData));
                } else {
                    writer.println(line);
                }
            }

            fileScanner.close();
            writer.close();

            if (!inputFile.delete()) {
                System.out.println("Could not delete original file.");
            }

            if (!tempFile.renameTo(inputFile)) {
                System.out.println("Could not rename temp file.");
            }

        } catch (Exception e) {
            System.out.println("Error updating customer file.");
        }
    }

    private static String validateRegistration(Scanner scanner) {

        String regPattern = "^[0-9]{2,3}[A-Z]{1,3}[0-9]{1,5}$";

        while (true) {

            System.out.print("Enter Vehicle Registration (e.g., 162MO1185): ");
            String input = scanner.nextLine().trim().toUpperCase();

            // Format validation
            if (!input.matches(regPattern)) {
                System.out.println("Invalid Irish registration format.");
                System.out.println("Format: 2-3 digits + 1-3 capital letters + 1-5 digits");
                continue;
            }
            // Duplicate check
            if (registrationExists(input)) {
                System.out.println("Registration already exists in the system.");
                continue;
            }
            return input;
        }
    }

    // Prevent Duplicate Registration
    public static boolean registrationExists(String regNumber) {

        try (Scanner fileScanner = new Scanner(new java.io.File("vehicle.txt"))) {

            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();
                String[] data = line.split(",");

                // Registration is now at index 1
                if (data[1].equalsIgnoreCase(regNumber)) {
                    return true;
                }
            }

        } catch (Exception e) {
            System.out.println("Error checking vehicle file.");
        }
        return false;
    }


    public static void generateRenewalReport() {

        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        LocalDate startDate;
        LocalDate endDate;

        // Validate start date
        while (true) {
            System.out.print("Enter start date (yyyy-MM-dd): ");
            String input = scanner.nextLine();

            try {
                startDate = LocalDate.parse(input, formatter);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            }
        }

        // Validate end date
        while (true) {
            System.out.print("Enter end date (yyyy-MM-dd): ");
            String input = scanner.nextLine();

            try {
                endDate = LocalDate.parse(input, formatter);

                if (endDate.isBefore(startDate)) {
                    System.out.println("End date cannot be before start date.");
                    continue;
                }

                break;

            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            }
        }

        System.out.println("\n===== POLICY REPORT =====");

        try (
                Scanner fileScanner = new Scanner(new java.io.File("policies.txt"));
                java.io.PrintWriter writer =
                        new java.io.PrintWriter(new java.io.FileWriter("report.txt"))
        ) {

            writer.println("===== POLICY REPORT =====");
            writer.println("Period: " + startDate + " to " + endDate);
            writer.println("---------------------------------");

            boolean found = false;

            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();
                String[] data = line.split(",");

                if (data.length < 8) continue; // safety check

                LocalDate policyStart = LocalDate.parse(data[7]);

                if ((policyStart.isEqual(startDate) || policyStart.isAfter(startDate)) &&
                        (policyStart.isEqual(endDate) || policyStart.isBefore(endDate))) {

                    System.out.println(line);
                    writer.println(line);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No policies found in this period.");
                writer.println("No policies found in this period.");
            }

            writer.println("---------------------------------");
            System.out.println("Report saved to report.txt");

        } catch (Exception e) {
            System.out.println("Error generating report.");
            System.out.println("Error: " + e.getMessage());
        }
    }



    public static void searchVehicle(int option, String value) {

        boolean found = false;

        try (Scanner fileScanner = new Scanner(new java.io.File("vehicle.txt"))) {

            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();
                String[] data = line.split(",");

                if (data.length < 5) continue;

                String vehicleID = data[0];
                String reg = data[1];
                String make = data[2];
                String model = data[3];

                boolean match = false;

                switch (option) {

                    case 1:
                        if (vehicleID.equalsIgnoreCase(value)) match = true;
                        break;

                    case 2:
                        if (reg.equalsIgnoreCase(value)) match = true;
                        break;

                    case 3:
                        if (make.equalsIgnoreCase(value)) match = true;
                        break;

                    case 4:
                        if (model.equalsIgnoreCase(value)) match = true;
                        break;

                    default:
                        System.out.println("Invalid search option.");
                        return;
                }

                if (match) {

                    System.out.println("\nVehicle Found:");
                    System.out.println(line);
                    found = true;

                    Scanner inputScanner = new Scanner(System.in);
                    System.out.print("Would you like to edit this vehicle? (Y/N): ");
                    String editChoice = inputScanner.nextLine().toUpperCase();

                    if (editChoice.equals("Y")) {
                        editVehicle(data);
                    }

                    break; // stop after first match
                }
            }

        } catch (Exception e) {
            System.out.println("Error reading vehicle file.");
        }

        if (!found) {
            System.out.println("No matching vehicle found.");
        }
    }




    public static void editVehicle(String[] data) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("\n--- Edit Vehicle ---");

        System.out.println("1. Registration: " + data[1]);
        System.out.print("Enter new Registration (or press Enter to keep current): ");
        String reg = scanner.nextLine();
        if (!reg.isEmpty()) data[1] = reg;

        System.out.println("2. Make: " + data[2]);
        System.out.print("Enter new Make (or press Enter to keep current): ");
        String make = scanner.nextLine();
        if (!make.isEmpty()) data[2] = make;

        System.out.println("3. Model: " + data[3]);
        System.out.print("Enter new Model (or press Enter to keep current): ");
        String model = scanner.nextLine();
        if (!model.isEmpty()) data[3] = model;

        System.out.println("4. Year: " + data[4]);
        System.out.print("Enter new Year (or press Enter to keep current): ");
        String year = scanner.nextLine();
        if (!year.isEmpty()) data[4] = year;

        updateVehicleFile(data);

        System.out.println("Vehicle updated successfully.");
    }

    private static String validateTown(Scanner scanner) {

        String input;

        while (true) {
            System.out.print("Enter Town/City/Village: ");
            input = scanner.nextLine().trim();

            if (!input.matches("[A-Z][a-zA-Z]{2,30}")) {
                System.out.println("Town must start with a capital letter and contain only letters.");
                continue;
            }

            return input;
        }
    }


    public static void updateVehicleFile(String[] updatedData) {

        try {

            java.io.File inputFile = new java.io.File("vehicle.txt");
            java.io.File tempFile = new java.io.File("vehicle_temp.txt");

            Scanner fileScanner = new Scanner(inputFile);
            java.io.PrintWriter writer = new java.io.PrintWriter(tempFile);

            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();
                String[] currentData = line.split(",");

                if (currentData[0].equalsIgnoreCase(updatedData[0])) {
                    writer.println(String.join(",", updatedData));
                } else {
                    writer.println(line);
                }
            }

            fileScanner.close();
            writer.close();


        } catch (Exception e) {
            System.out.println("Error updating vehicle file.");
        }
    }

    public static void searchQuotation(String quotationID) {

        boolean found = false;

        try (Scanner fileScanner = new Scanner(new java.io.File("quotations.txt"))) {

            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();
                String[] data = line.split(",");

                if (data[0].equalsIgnoreCase(quotationID)) {
                    System.out.println("\nQuotation Found:");
                    System.out.println(line);
                    found = true;
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println("Error reading quotations file.");
        }

        if (!found) {
            System.out.println("Quotation not found.");
        }
    }


    public static void extendPolicy() {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Policy ID: ");
        String policyID = scanner.nextLine();

        try {

            java.io.File inputFile = new java.io.File("policies.txt");
            java.io.File tempFile = new java.io.File("policies_temp.txt");

            Scanner fileScanner = new Scanner(inputFile);
            java.io.PrintWriter writer = new java.io.PrintWriter(tempFile);

            boolean found = false;

            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();
                String[] data = line.split(",");

                if (data.length < 6) {
                    writer.println(line);
                    continue;
                }

                if (data[0].equalsIgnoreCase(policyID)) {

                    found = true;

                    System.out.println("\nPolicy Found:");
                    System.out.println(line);

                    System.out.print("Would you like to extend this policy? (Y/N): ");
                    String decision = scanner.nextLine().toUpperCase();

                    if (decision.equals("Y")) {

                        LocalDate startDate = LocalDate.parse(data[7]);
                        LocalDate endDate = LocalDate.parse(data[8]);

                        double currentPremium = Double.parseDouble(data[6]);

                        long currentMonths = java.time.temporal.ChronoUnit.MONTHS.between(startDate, endDate);

// reconstruct yearly premium
                        double yearlyPremium = (currentPremium / currentMonths) * 12;

// round yearly premium
                        yearlyPremium = Math.round(yearlyPremium * 100.0) / 100.0;

                        double monthlyPremium = yearlyPremium / 12;

                        System.out.print("Enter extension period (1–12 months): ");
                        int months = scanner.nextInt();
                        scanner.nextLine();

                        while (months < 1 || months > 12) {
                            System.out.print("Invalid. Enter between 1 and 12 months: ");
                            months = scanner.nextInt();
                            scanner.nextLine();
                        }

                        double extensionCost = monthlyPremium * months;

// round final price
                        extensionCost = Math.round(extensionCost * 100.0) / 100.0;

                        String formattedCost = String.format("%.2f", extensionCost);

                        LocalDate newStart = endDate;
                        LocalDate newEnd = endDate.plusMonths(months);

                        data[6] = formattedCost;
                        data[7] = newStart.toString();
                        data[8] = newEnd.toString();

                        String updatedLine = String.join(",", data);

                        writer.println(updatedLine);

                        System.out.println("Policy extended successfully.");
                        System.out.println("Extension Cost: €" + formattedCost);

                    } else {
                        writer.println(line);
                        System.out.println("Policy not extended.");
                    }

                } else {

                    writer.println(line);
                }
            }

            fileScanner.close();
            writer.close();

            inputFile.delete();
            tempFile.renameTo(inputFile);

            if (!found) {
                System.out.println("Policy not found.");
            }

        } catch (Exception e) {
            System.out.println("Error extending policy.");
            e.printStackTrace();
        }
    }


    public static void createPolicyFromQuotation(String quotationID) {

        try (Scanner fileScanner = new Scanner(new java.io.File("quotations.txt"))) {

            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();
                String[] data = line.split(",");

                if (data[0].equalsIgnoreCase(quotationID)) {

                    String customerID = data[1];
                    String reg = data[2];
                    double premium = Double.parseDouble(data[6]);

                    Customer quotationCustomer = findCustomerByID(customerID);
                    Vehicle quotationVehicle = findVehicleByReg(reg);

                    Policy policy = new Policy(quotationCustomer, quotationVehicle, premium);
                    policy.saveToFile();

                    System.out.println("\nPOLICY CREATED SUCCESSFULLY!");
                    System.out.println(policy.getPolicyDetails());

                    return;
                }
            }

            System.out.println("Quotation not found.");

        } catch (Exception e) {
            System.out.println("Error creating policy.");
            e.printStackTrace();
        }

    }


}  //  closes Main class