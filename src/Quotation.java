public class Quotation {

    private static int nextQuotationID = 1;
    private String quotationID;
    private Customer customer;
    private Vehicle vehicle;
    private double amount;

    public Quotation(Customer customer, Vehicle vehicle, double amount) {
        this.quotationID = String.format("QUOT-%04d", nextQuotationID++);
        this.customer = customer;
        this.vehicle = vehicle;
        this.amount = amount;
    }

    public String getQuotationID() {
        return quotationID;
    }

    public String getDetails() {
        return "\nQuotation ID: " + quotationID +
                "\nCustomer: " + customer.getCustomerID() +
                "\nVehicle: " + vehicle.getDetails() +
                "\nAmount: €" + amount;
    }
}
