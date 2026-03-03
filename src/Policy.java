import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class Policy {

    private static int nextPolicyID = 1;

    private String policyID;
    private Customer customer;
    private Vehicle vehicle;
    private double premium;
    private LocalDate startDate;
    private LocalDate expiryDate;

    public Policy(Customer customer, Vehicle vehicle, double premium) {

        this.policyID = String.format("POL-%04d", nextPolicyID++);
        this.customer = customer;
        this.vehicle = vehicle;
        this.premium = premium;
        this.startDate = LocalDate.now();
        this.expiryDate = startDate.plusYears(1);
    }

    public String getPolicyDetails() {

        return "\n========= MOTOR INSURANCE POLICY =========" +
                "\nPolicy ID: " + policyID +
                "\nCustomer: " + customer.getCustomerID() +
                "\nVehicle: " + vehicle.getDetails() +
                "\nPremium: €" + premium +
                "\nStart Date: " + startDate +
                "\nExpiry Date: " + expiryDate +
                "\n==========================================";
    }

    public void saveToFile() {

        String data = policyID + "," +
                customer.getCustomerID() + "," +
                vehicle.getReg() + "," +
                vehicle.getMake() + "," +
                vehicle.getModel() + "," +
                vehicle.getYear() + "," +
                premium + "," +
                startDate + "," +
                expiryDate;

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter("policies.txt", true))) {

            writer.write(data);
            writer.newLine();

        } catch (IOException e) {
            System.out.println("Error saving policy.");
        }
    }

    public static void initializePolicyID() {

        int maxID = 0;

        try (java.io.BufferedReader reader =
                     new java.io.BufferedReader(
                             new java.io.FileReader("policies.txt"))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");

                String idPart = parts[0]; // POL-0003

                int number = Integer.parseInt(idPart.substring(4));
                // POL-0003 → substring(4) = 0003

                if (number > maxID) {
                    maxID = number;
                }
            }

        } catch (IOException e) {
            // File may not exist yet — that's fine
        }

        nextPolicyID = maxID + 1;
    }
}