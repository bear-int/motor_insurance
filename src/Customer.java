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
    private String phone;
    private String email;

    // Constructor (NO customerID parameter)
    public Customer(String firstName, String surname, String address,
                    String county, String dateOfBirth, String gender,
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
        return gender;
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
                "\nGender: " + gender +
                "\nPhone Number: " + phone +
                "\nEmail: " + email;
    }
}
