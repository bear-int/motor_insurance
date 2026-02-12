import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter Surname: ");
        String surname = scanner.nextLine();

        System.out.print("Enter Address: ");
        String address = scanner.nextLine();

        System.out.print("Enter County: ");
        String county = scanner.nextLine();

        System.out.print("Enter Date of Birth: ");
        String dob = scanner.nextLine();

        System.out.print("Enter Gender (Male/Female): ");
        String gender = scanner.nextLine();

        System.out.print("Enter Phone Number: ");
        String phone = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        // Create Customer (ID auto-generated)
        Customer customer = new Customer(
                firstName,
                surname,
                address,
                county,
                dob,
                gender,
                phone,
                email
        );

        System.out.println("\nCustomer Created Successfully!");
        System.out.println(customer.getCustomerDetails());

        scanner.close();
    }
}
