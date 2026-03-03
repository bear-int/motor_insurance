import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

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

    public int getAge() {
        try {
            LocalDate dob = LocalDate.parse(this.dateOfBirth);
            return Period.between(dob, LocalDate.now()).getYears();
        } catch (Exception e) {
            return 0;
        }
    }


    // Constructor (NO customerID parameter)

    public Customer(String firstName, String surname, String address,
                    String county, String dateOfBirth, boolean gender,
                    String phoneNumber, String email) {

        this.customerID = String.format("CUST-%04d", nextCustomerID++);
        this.firstName = firstName;
        this.surname = surname;
        this.address = address;
        this.county = county;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phone = phoneNumber;
        this.email = email;
    }

    public Customer(String customerID, String firstName, String surname,
                    String address, String county, String dateOfBirth,
                    boolean gender, String phoneNumber, String email) {

        this.customerID = customerID;
        this.firstName = firstName;
        this.surname = surname;
        this.address = address;
        this.county = county;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phone = phoneNumber;
        this.email = email;

        int numericID = Integer.parseInt(customerID.substring(5));
        if (numericID >= nextCustomerID) {
            nextCustomerID = numericID + 1;
        }
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
    public boolean isMale() {
        return gender;
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
                "\nGender: " + (gender ? "Male" : "Female") +
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

                line = line.trim();

                if (line.isEmpty()) continue;   // skip empty lines
                if (!line.startsWith("CUST-")) continue; // skip bad lines

                String[] parts = line.split(",");

                if (parts.length == 0) continue;

                String idPart = parts[0]; // CUST-0007

                try {
                    int number = Integer.parseInt(idPart.substring(5));
                    if (number > maxID) {
                        maxID = number;
                    }
                } catch (NumberFormatException e) {
                    // skip corrupted IDs safely
                    System.out.println("Skipping invalid ID: " + idPart);
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


