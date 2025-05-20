package com.carrental.patterns;
public class Insurance extends RentalDecorator{
    public Insurance(Rental r){super(r);}
    public double cost(){return super.cost()+15;}
    public String details(){return super.details()+" +Insurance";}
}