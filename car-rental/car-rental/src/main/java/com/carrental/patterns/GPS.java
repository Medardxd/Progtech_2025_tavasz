package com.carrental.patterns;


// GPS.java
public class GPS extends RentalDecorator {
    public GPS(Rental r) { super(r); }
    @Override public double cost()    { return inner.cost() + 10; }
    @Override public String  details(){ return inner.details() + " +GPS"; }
}