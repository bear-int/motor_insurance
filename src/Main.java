import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int choice;

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
                    System.out.print("Enter First Name: ");
                    String firstName = scanner.nextLine();

                    System.out.print("Enter Surname: ");
                    String surname = scanner.nextLine();

                    System.out.print("Enter Address: ");
                    String address = scanner.nextLine();

                    System.out.print("Enter County: ");
                    String county = scanner.nextLine();

                    System.out.print("Enter DateOfBirth: ");
                    String dateOfBirth = scanner.nextLine();

                    System.out.print("Enter gender: ");
                    String gender = scanner.nextLine();

                    System.out.print("Enter Phone: ");
                    String phone = scanner.nextLine();

                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine();

                    Customer customer = new Customer(firstName, surname, address, county, dateOfBirth, gender, phone, email);
                    System.out.println("\nCustomer Created Successfully!");
                    System.out.println(customer.getCustomerDetails());
                    break;

                case 2:
                    System.out.print("Add V2ehicle: ");
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
}
