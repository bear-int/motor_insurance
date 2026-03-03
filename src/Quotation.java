import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class Quotation {

    private static int nextQuotationID = 1;

    private String quotationID;
    private Customer customer;
    private Vehicle vehicle;
    private double basePremium;
    private double finalPremium;
    private int age;
    private double adjustmentAmount;
    private String adjustmentDescription;

    public Quotation(Customer customer, Vehicle vehicle) {

        this.quotationID = String.format("QUOT-%04d", nextQuotationID++);
        this.customer = customer;
        this.vehicle = vehicle;

        calculatePremium();
    }

    private void calculatePremium() {

        age = customer.getAge();

        if (age >= 80) {
            System.out.println("No quotation available for customers aged 80 or above.");
            finalPremium = -1;
            return;
        }

        double generalBase = 1000;

        // Gender base
        if (customer.isMale()) {
            basePremium = generalBase * 2;   // 2000
        } else {
            basePremium = generalBase * 0.8; // 800
        }

        double adjustedPremium = basePremium;

        // Age adjustments
        if (age < 20) {
            adjustmentAmount = basePremium * 0.20;
            adjustedPremium += adjustmentAmount;
            adjustmentDescription = "+20% (Under 20 loading)";
        }
        else if (age <= 35) {
            adjustmentAmount = basePremium * 0.40;
            adjustedPremium -= adjustmentAmount;
            adjustmentDescription = "-40% (Age 20–35 discount)";
        }
        else if (age < 80) {
            adjustmentAmount = basePremium * 0.65;
            adjustedPremium -= adjustmentAmount;
            adjustmentDescription = "-65% (Age 36–79 discount)";
        }

        finalPremium = adjustedPremium;
    }

    public String getQuotationID() {
        return quotationID;
    }

    public double getFinalPremium() {
        return finalPremium;
    }

    public String getDetails() {

        return "\n========= MOTOR INSURANCE QUOTATION =========" +
                "\nQuotation ID: " + quotationID +
                "\nCustomer ID: " + customer.getCustomerID() +
                "\nCustomer Name: " + customer.getFirstName() + " " + customer.getSurname() +
                "\nCustomer Age: " + age +
                "\nGender: " + customer.getGender() +
                "\nVehicle: " + vehicle.getDetails() +
                "\n--------------------------------------------" +
                "\nBase Premium: €" + basePremium +
                "\nAdjustment: " + adjustmentDescription +
                "\nAdjustment Amount: €" + adjustmentAmount +
                "\n--------------------------------------------" +
                "\nFinal Premium: €" + finalPremium +
                "\n============================================";
    }

    public void saveToFile() {

        String data = quotationID + "," +
                customer.getCustomerID() + "," +
                vehicle.getReg() + "," +
                vehicle.getMake() + "," +
                vehicle.getModel() + "," +
                vehicle.getYear() + "," +
                finalPremium + "," +
                java.time.LocalDate.now();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("quotations.txt", true))) {
            writer.write(data);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving quotation.");
        }
    }

    public static void initializeQuotationID() {

        int maxID = 0;

        try (java.io.BufferedReader reader =
                     new java.io.BufferedReader(
                             new java.io.FileReader("quotations.txt"))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");

                String idPart = parts[0]; // QUOT-0003

                int number = Integer.parseInt(idPart.substring(5));

                if (number > maxID) {
                    maxID = number;
                }
            }

        } catch (IOException e) {
            // File may not exist yet — that's fine
        }

        nextQuotationID = maxID + 1;
    }

}