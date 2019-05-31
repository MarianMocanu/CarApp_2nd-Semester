package dk.kea.dat18i.spring.demo;

import java.util.List;

public class CarForm {

    private int carId;
    private String reg;
    private String brand;
    private String color;
    private double maxSpeed;
    private List<Integer> owners;

    public CarForm(int carId, String reg, String brand, String color, double maxSpeed, List<Integer> owners) {
        this.carId = carId;
        this.reg = reg;
        this.brand = brand;
        this.color = color;
        this.maxSpeed = maxSpeed;
        this.owners = owners;
    }

    public CarForm() {}

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public List<Integer> getOwners() {
        return owners;
    }

    public void setOwners(List<Integer> owners) {
        this.owners = owners;
    }
}
