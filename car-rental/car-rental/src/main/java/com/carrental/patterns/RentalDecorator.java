// RentalDecorator
package com.carrental.patterns;
public abstract class RentalDecorator implements Rental{
    protected final Rental inner;
    protected RentalDecorator(Rental r){inner=r;}
    public double cost(){return inner.cost();}
    public String details(){return inner.details();}
    public void accept(RentalVisitor v){ inner.accept(v);}   // delegate
}

