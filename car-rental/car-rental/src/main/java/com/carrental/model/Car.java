// Car.java
package com.carrental.model;
public class Car {
    private final int id;
    private final String model;
    private final CarType type;
    private final double pricePerDay;
    public Car(int id,String model,CarType type,double pricePerDay){
        this.id=id; this.model=model; this.type=type; this.pricePerDay =pricePerDay;
    }
    public int id(){return id;}  public String getModel(){return model;}
    public CarType getType(){return type;} public double getPricePerDay(){return pricePerDay;}

    @Override public String toString(){ return model; }
}
