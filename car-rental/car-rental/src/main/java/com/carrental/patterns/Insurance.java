package com.carrental.patterns;


// Insurance.java
public class Insurance extends RentalDecorator {
    public Insurance(Rental r){ super(r); }
    @Override public double cost()    { return inner.cost() + 15; }
    @Override public String  details(){ return inner.details() + " +Insurance"; }
}