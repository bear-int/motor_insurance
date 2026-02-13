public class Vehicle {

    String reg;
    String make;
    String model;
    int year;

    public Vehicle(String reg, String make, String model, int year){
        this.reg = reg;
        this.make = make;
        this.model = model;
        this.year = year;
    }

    public String getDetails(){
        return reg + " " + make + " " + model + " " + year;
    }
}
