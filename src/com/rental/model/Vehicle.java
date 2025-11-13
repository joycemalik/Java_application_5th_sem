// com/rental/model/Vehicle.java
package com.rental.model;

/**
 * Vehicle model class (POJO)
 * Represents a car or bike in the rental system
 */
public class Vehicle {
    private int id;
    private String type; // "CAR" or "BIKE"
    private String brand;
    private String model;
    private String regNumber;
    private double pricePerDay;
    private boolean available;

    // Default constructor
    public Vehicle() {}

    // Parameterized constructor
    public Vehicle(int id, String type, String brand, String model,
                   String regNumber, double pricePerDay, boolean available) {
        this.id = id;
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.regNumber = regNumber;
        this.pricePerDay = pricePerDay;
        this.available = available;
    }

    // Getters and Setters
    public int getId() { 
        return id; 
    }
    
    public void setId(int id) { 
        this.id = id; 
    }

    public String getType() { 
        return type; 
    }
    
    public void setType(String type) { 
        this.type = type; 
    }

    public String getBrand() { 
        return brand; 
    }
    
    public void setBrand(String brand) { 
        this.brand = brand; 
    }

    public String getModel() { 
        return model; 
    }
    
    public void setModel(String model) { 
        this.model = model; 
    }

    public String getRegNumber() { 
        return regNumber; 
    }
    
    public void setRegNumber(String regNumber) { 
        this.regNumber = regNumber; 
    }

    public double getPricePerDay() { 
        return pricePerDay; 
    }
    
    public void setPricePerDay(double pricePerDay) { 
        this.pricePerDay = pricePerDay; 
    }

    public boolean isAvailable() { 
        return available; 
    }
    
    public void setAvailable(boolean available) { 
        this.available = available; 
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", regNumber='" + regNumber + '\'' +
                ", pricePerDay=" + pricePerDay +
                ", available=" + available +
                '}';
    }
}
