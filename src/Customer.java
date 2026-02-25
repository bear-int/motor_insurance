import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Customer {

    // Static counter (shared by all customers)
    private static int nextCustomerID = 1;
    private String customerID;
    private String firstName;
    private String surname;
    private String address;
    private String county;
    private String dateOfBirth;
    private boolean gender;  // true = Male, false = Female
    private String phone;
    private String email;
    private Vehicle vehicle;


    // Constructor (NO customerID parameter)

    public Customer(String firstName, String surname, String address,
                    String county, String dateOfBirth, boolean gender,
                   String phoneNumber, String email) {

        this.customerID = String.format("CUST-%04d", nextCustomerID++); // store as string
        this.firstName = firstName;
        this.surname = surname;
        this.address = address;
        this.county = county;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phone = phoneNumber;
        this.email = email;
    }

    // Getter for customerID
    public String getCustomerID() { return customerID; }
    // Other getters (good practice for OOP)
    public String getFirstName() { return firstName; }
    public String getSurname() {
        return surname;
    }
    public String getPhoneNumber() {
        return phone;
    }
    public String getEmail() {
        return email;
    }
    public String getGender() {
        return gender ? "Male" : "Female";
    }


    // Method to display customer details
    public String getCustomerDetails() {

        return "\nCustomer ID: " + customerID +
                "\nName: " + firstName + " " + surname +
                "\nAddress: " + address +
                "\nCounty: " + county +
                "\nDate of Birth: " + dateOfBirth +
                "\nGender: " + getGender() +
                "\nPhone Number: " + phone +
                "\nEmail: " + email;
    }


    public void setVehicle(Vehicle v){
        this.vehicle = v;
    }

    public void showVehicle(){
    if(vehicle == null)
        System.out.println("No vehicle added");
    else
        System.out.println(vehicle.getDetails());
    }

    // method that reads customers.txt
    public static void initializeCustomerID() {

        int maxID = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader("customers.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");
                String idPart = parts[0]; // CUST-0003

                int number = Integer.parseInt(idPart.substring(5)); // 0003 → 3

                if (number > maxID) {
                    maxID = number;
                }
            }

        } catch (IOException e) {
            // File may not exist yet — that's OK
        }

        nextCustomerID = maxID + 1;
    }





    public void saveToFile() {

        String genderLetter = gender ? "M" : "F";
        String data = getCustomerID() + "," +  // use formatted ID
                firstName + "," +
                surname + "," +
                address + "," +
                county + "," +
                dateOfBirth + "," +
                phone + "," +
                email + "," +
                genderLetter;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("customers.txt", true))) {
            writer.write(data);
            writer.newLine();
            System.out.println("Customer saved to file successfully!");
        } catch (IOException e) {
            System.out.println("Error saving customer: " + e.getMessage());
        }

    }
}


