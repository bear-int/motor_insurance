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
    private int insuranceType;

    private double countyAdjustment = 0;
    private double vehicleAdjustment = 0;
    private double emissionAdjustment = 0;
    private double insuranceAdjustment = 0;

    public Quotation(Customer customer, Vehicle vehicle, int insuranceType) {

        this.quotationID = String.format("QUOT-%04d", nextQuotationID++);
        this.customer = customer;
        this.vehicle = vehicle;
        this.insuranceType = insuranceType;

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
            basePremium = generalBase * 2;
        } else {
            basePremium = generalBase * 0.8;
        }

        double adjustedPremium = basePremium;

        // AGE RULE
        if (age < 20) {
            adjustmentAmount = basePremium * 0.20;
            adjustedPremium += adjustmentAmount;
            adjustmentDescription = "+20% (Under 20 loading)";
        }
        else if (age <= 35) {
            adjustmentAmount = -(basePremium * 0.40);
            adjustedPremium += adjustmentAmount;
            adjustmentDescription = "-40% (Age 20–35 discount)";
        }
        else {
            adjustmentAmount = -(basePremium * 0.65);
            adjustedPremium += adjustmentAmount;
            adjustmentDescription = "-65% (Age 36–79 discount)";
        }

        // COUNTY RULE
        String county = customer.getCounty().toLowerCase();

        switch (county) {
            case "cork": countyAdjustment = 50; break;
            case "clare": countyAdjustment = 225; break;
            case "kerry": countyAdjustment = 50; break;
            case "limerick": countyAdjustment = -75; break;
            case "tipperary": countyAdjustment = -80; break;
            case "waterford": countyAdjustment = -100; break;
        }

        adjustedPremium += countyAdjustment;

        // VEHICLE MAKE/MODEL
        String make = vehicle.getMake();
        String model = vehicle.getModel();

        if(make.equalsIgnoreCase("BMW")){

            if(model.equalsIgnoreCase("Convertible")) vehicleAdjustment = 200;
            if(model.equalsIgnoreCase("Gran Turismo")) vehicleAdjustment = 250;
            if(model.equalsIgnoreCase("X6")) vehicleAdjustment = 300;
            if(model.equalsIgnoreCase("Z4")) vehicleAdjustment = 175;
        }

        else if(make.equalsIgnoreCase("Opel")){

            if(model.equalsIgnoreCase("Corsa")) vehicleAdjustment = 50;
            if(model.equalsIgnoreCase("Astra")) vehicleAdjustment = 105;
            if(model.equalsIgnoreCase("Vectra")) vehicleAdjustment = 150;
        }

        else if(make.equalsIgnoreCase("Toyota")){

            if(model.equalsIgnoreCase("Yaris")) vehicleAdjustment = 50;
            if(model.equalsIgnoreCase("Auris")) vehicleAdjustment = 75;
            if(model.equalsIgnoreCase("Corolla")) vehicleAdjustment = 100;
            if(model.equalsIgnoreCase("Avensis")) vehicleAdjustment = 125;
        }

        else if(make.equalsIgnoreCase("Renault")){

            if(model.equalsIgnoreCase("Fluence")) vehicleAdjustment = 100;
            if(model.equalsIgnoreCase("Megane")) vehicleAdjustment = 75;
            if(model.equalsIgnoreCase("Clio")) vehicleAdjustment = 50;
        }

        adjustedPremium += vehicleAdjustment;

        // EMISSION RULE
        String emission = vehicle.getEmission();

        if(emission.equalsIgnoreCase("High")) emissionAdjustment = 300;
        if(emission.equalsIgnoreCase("Medium")) emissionAdjustment = 150;
        if(emission.equalsIgnoreCase("Low")) emissionAdjustment = -55;

        adjustedPremium += emissionAdjustment;

        // INSURANCE TYPE
        if(insuranceType == 1) {
            insuranceAdjustment = 200;
        }
        else {
            insuranceAdjustment = -120;
        }

        adjustedPremium += insuranceAdjustment;

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
                "\nAge Adjustment: €" + (adjustmentDescription.contains("-") ? -adjustmentAmount : adjustmentAmount) +
                "\nCounty Adjustment: €" + countyAdjustment +
                "\nVehicle Adjustment: €" + vehicleAdjustment +
                "\nEmission Adjustment: €" + emissionAdjustment +
                "\nInsurance Type Adjustment: €" + insuranceAdjustment +
                "\n--------------------------------------------" +
                "\nFinal Premium: €" + finalPremium +
                "\n============================================";
    }

    double totalAdjustments = adjustmentAmount + countyAdjustment +
            vehicleAdjustment + emissionAdjustment +
            insuranceAdjustment;

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