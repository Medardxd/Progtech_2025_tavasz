// Car.java
package com.carrental.model;
public class Car {
    private final int id;
    private final String model;
    private final CarType type;
    private final double weeklyRate;
    public Car(int id,String model,CarType type,double weeklyRate){
        this.id=id; this.model=model; this.type=type; this.weeklyRate=weeklyRate;
    }
    public int id(){return id;}  public String getModel(){return model;}
    public CarType getType(){return type;} public double getWeeklyRate(){return weeklyRate;}

    @Override public String toString(){ return model; }
}
