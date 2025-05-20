package com.carrental.patterns;

import com.carrental.model.Car;
import com.carrental.model.CarType;

public class PremiumStrategy implements PricingStrategy {

    private final StandardStrategy std = new StandardStrategy();

    @Override
    public double apply(double base, int days, CarType type) {
        double withSurcharge = base * 1.20;          // +20 %
        return std.apply(withSurcharge, days, type); // THEN normal discounts
    }
}
