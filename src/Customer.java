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
    private String gender;
    private String phoneNumber;
    private String email;

    // Constructor (NO customerID parameter)
    public Customer(String firstName, String surname, String address, String county, String dateOfBirth, String gender, String phoneNumber, String email) {

        this.customerID = nextCustomerID++; // auto-generate ID
        this.firstName = firstName;
        this.surname = surname;
        this.address = address;
        this.county = county;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // Getter for ID
    public int getCustomerID() {
        return customerID;
    }

    // Display customer details
    public String getCustomerDetails() {
        return "Customer ID: " + customerID +
                "\nName: " + firstName + " " + surname +
                "\nAddress: " + address +
                "\nCounty: " + county +
                "\nDate of Birth: " + dateOfBirth +
                "\nGender: " + gender +
                "\nPhone: " + phoneNumber +
                "\nEmail: " + email;
    }
}