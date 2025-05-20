package com.carrental.patterns;

import com.carrental.model.Car;
import com.carrental.model.CarType;

public class StandardStrategy implements PricingStrategy {

    @Override
    public double apply(double base, int days, CarType type) {
        if (days >= 14) return base * 0.80;          // 20 % off
        if (days >=  7) return base * 0.90;          // 10 % off
        return base;                                 // no discount
    }
}
