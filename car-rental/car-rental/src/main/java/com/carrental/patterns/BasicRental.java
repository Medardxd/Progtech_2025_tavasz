// BasicRental.java
package com.carrental.patterns;

import com.carrental.auth.User;
import com.carrental.model.Car;
import com.carrental.model.CarType;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BasicRental implements Rental {

    /* immutable state -------------------------------------------------- */
    private final Car       car;
    private final User      user;
    private final LocalDate startDate, endDate;
    private final PricingStrategy strategy;

    public BasicRental(Car car, User user,
                       LocalDate start, LocalDate end,
                       PricingStrategy strategy) {
        this.car = car; this.user = user;
        this.startDate = start; this.endDate = end;
        this.strategy = strategy;
    }

    @Override
    public double cost() {

        int days = (int) (ChronoUnit.DAYS.between(startDate, endDate) + 1);

        /* DAILY price first */
        double base = days * car.getPricePerDay();

        /* Strategy adds surcharge / discount */
        base = strategy.apply(base, days, car.getType());

        /* decorators (GPS / Insurance) are added later by wrapping */
        return base;
    }

    @Override public String details() {
        return user.username() + " renting "
                + car.getModel() + " "
                + startDate + " – " + endDate;
    }

    public void accept(RentalVisitor v) { v.visit(this); }
    public Car       car()  { return car; }
    public User      user() { return user; }
    public LocalDate start(){ return startDate; }
    public LocalDate end()  { return endDate; }
}
