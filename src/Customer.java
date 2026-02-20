import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Customer {

    // Static counter (shared by all customers)
    private static int nextCustomerID = 1;

    // Attributes
    private int customerID;
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

        this.customerID = nextCustomerID++; // auto-generate ID
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
    public int getCustomerID() {
        return customerID;
    }

    // Other getters (good practice for OOP)
    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }

    public String getCounty() {
        return county;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

     public String getGender() {
        return gender ? "Male" : "Female";
    }

    public String getPhoneNumber() {
        return phone;
    }

    public String getEmail() {
        return email;
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

    public void saveToFile() {

        String genderLetter = gender ? "M" : "F";

        String data = customerID + "," +
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

