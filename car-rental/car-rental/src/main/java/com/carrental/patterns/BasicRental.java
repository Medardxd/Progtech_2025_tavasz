// BasicRental.java
package com.carrental.patterns;
import com.carrental.model.Car;
import com.carrental.auth.User;
public class BasicRental implements Rental{
    private final Car car; private final User user; private final int weeks;
    private final PricingStrategy strat;
    public BasicRental(Car c,User u,int w,PricingStrategy s){
        car=c;user=u;weeks=w;strat=s;
    }
    public double cost(){ return strat.price(car,weeks); }
    public String details(){ return user.username()+" renting "+car.getModel()+" x"+weeks+"w"; }
    public int weeks(){return weeks;} public Car car(){return car;} public User user(){return user;}
    public void accept(RentalVisitor v){ v.visit(this); }
}
