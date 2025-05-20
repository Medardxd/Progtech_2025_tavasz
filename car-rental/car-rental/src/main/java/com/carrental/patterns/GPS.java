package com.carrental.patterns;
public class GPS extends RentalDecorator{
    public GPS(Rental r){super(r);}
    public double cost(){return super.cost()+10;}
    public String details(){return super.details()+" +GPS";}
}