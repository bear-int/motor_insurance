import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;


public class Quotation {

    private double yearlyPremium;
    private double monthlyPremium;

    private static int nextQuotationID = 1;

    private String quotationID;
    private Customer customer;
    private Vehicle vehicle;

    private int insuranceType;
    private int months;

    private double basePremium;
    private double finalPremium;

    private int age;

    private double ageAdjustment;
    private double countyAdjustment;
    private double vehicleAdjustment;
    private double emissionAdjustment;
    private double insuranceAdjustment;


    public Quotation(Customer customer, Vehicle vehicle, int insuranceType, int months) {

        this.quotationID = String.format("QUOT-%04d", nextQuotationID++);
        this.customer = customer;
        this.vehicle = vehicle;
        this.insuranceType = insuranceType;
        this.months = months;

        calculatePremium();
    }

    private void calculatePremium() {

        resetAdjustments();

        age = customer.getAge();

        if (age >= 80) {
            System.out.println("No quotation available for customers aged 80 or above.");
            finalPremium = -1;
            return;
        }

        calculateBasePremium();
        calculateAgeAdjustment();
        calculateCountyAdjustment();
        calculateVehicleAdjustment();
        calculateEmissionAdjustment();
        calculateInsuranceAdjustment();

        yearlyPremium =
                basePremium +
                        ageAdjustment +
                        countyAdjustment +
                        vehicleAdjustment +
                        emissionAdjustment +
                        insuranceAdjustment;

        monthlyPremium = yearlyPremium / 12;

        finalPremium = monthlyPremium * months;

// rounding
        yearlyPremium = Math.round(yearlyPremium * 100.0) / 100.0;
        monthlyPremium = Math.round(monthlyPremium * 100.0) / 100.0;
        finalPremium = Math.round(finalPremium * 100.0) / 100.0;
    }

    private void resetAdjustments() {
        ageAdjustment = 0;
        countyAdjustment = 0;
        vehicleAdjustment = 0;
        emissionAdjustment = 0;
        insuranceAdjustment = 0;
    }

    private void calculateBasePremium() {

        double generalBase = 1000;

        if (customer.isMale()) {
            basePremium = generalBase * 2;
        } else {
            basePremium = generalBase * 0.8;
        }
    }

    private void calculateAgeAdjustment() {

        if (age < 20) {
            ageAdjustment = basePremium * 0.20;
        }
        else if (age <= 35) {
            ageAdjustment = -(basePremium * 0.40);
        }
        else {
            ageAdjustment = -(basePremium * 0.65);
        }
    }

    private void calculateCountyAdjustment() {

        String county = customer.getCounty();

        if (county == null) return;

        switch (county.toLowerCase()) {

            case "cork":
            case "kerry":
                countyAdjustment = 50;
                break;

            case "clare":
                countyAdjustment = 225;
                break;

            case "limerick":
                countyAdjustment = -75;
                break;

            case "tipperary":
                countyAdjustment = -80;
                break;

            case "waterford":
                countyAdjustment = -100;
                break;
        }
    }

    private void calculateVehicleAdjustment() {

        String make = vehicle.getMake();
        String model = vehicle.getModel();

        if (make == null || model == null) return;

        switch (make.toLowerCase()) {

            case "bmw":
                switch (model.toLowerCase()) {
                    case "convertible": vehicleAdjustment = 200; break;
                    case "gran turismo": vehicleAdjustment = 250; break;
                    case "x6": vehicleAdjustment = 300; break;
                    case "z4": vehicleAdjustment = 175; break;
                }
                break;

            case "opel":
                switch (model.toLowerCase()) {
                    case "corsa": vehicleAdjustment = 50; break;
                    case "astra": vehicleAdjustment = 105; break;
                    case "vectra": vehicleAdjustment = 150; break;
                }
                break;

            case "toyota":
                switch (model.toLowerCase()) {
                    case "yaris": vehicleAdjustment = 50; break;
                    case "auris": vehicleAdjustment = 75; break;
                    case "corolla": vehicleAdjustment = 100; break;
                    case "avensis": vehicleAdjustment = 125; break;
                }
                break;

            case "renault":
                switch (model.toLowerCase()) {
                    case "fluence": vehicleAdjustment = 100; break;
                    case "megane": vehicleAdjustment = 75; break;
                    case "clio": vehicleAdjustment = 50; break;
                }
                break;
        }
    }

    private void calculateEmissionAdjustment() {

        String emission = vehicle.getEmission();

        if (emission == null) return;

        switch (emission.toLowerCase()) {
            case "high": emissionAdjustment = 300; break;
            case "medium": emissionAdjustment = 150; break;
            case "low": emissionAdjustment = -55; break;
        }
    }

    private void calculateInsuranceAdjustment() {

        if (insuranceType == 1) {
            insuranceAdjustment = 200;
        } else {
            insuranceAdjustment = -120;
        }
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
                "\nPolicy Period: " + months + " month(s)" +
                "\n--------------------------------------------" +
                "\nBase Premium: €" + basePremium +
                "\nAge Adjustment: €" + ageAdjustment +
                "\nCounty Adjustment: €" + countyAdjustment +
                "\nVehicle Adjustment: €" + vehicleAdjustment +
                "\nEmission Adjustment: €" + emissionAdjustment +
                "\nInsurance Type Adjustment: €" + insuranceAdjustment +
                "\n--------------------------------------------" +
                "\nYearly Premium: €" + yearlyPremium +
                "\nMonthly Premium: €" + monthlyPremium +
                "\nPolicy Period: " + months + " month(s)" +
                "\nPremium for " + months + " months: €" + finalPremium +
                "\n============================================";
    }

    public static void initializeQuotationID() {

        int maxID = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader("quotations.txt"))) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) continue;

                String[] data = line.split(",");

                if (!data[0].startsWith("QUOT-")) continue;

                int number = Integer.parseInt(data[0].substring(5));

                if (number > maxID) {
                    maxID = number;
                }
            }

        } catch (Exception e) {
            // file may not exist yet
        }

        nextQuotationID = maxID + 1;
    }



    public void saveToFile() {

        String data =
                quotationID + "," +
                        customer.getCustomerID() + "," +
                        customer.getPhoneNumber() + "," +
                        vehicle.getReg() + "," +
                        vehicle.getMake() + "," +
                        vehicle.getModel() + "," +
                        vehicle.getYear() + "," +
                        finalPremium + "," +
                        months;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("quotations.txt", true))) {
            writer.write(data);
            writer.newLine();
        }
        catch (IOException e) {
            System.out.println("Error saving quotation.");
        }
    }

}