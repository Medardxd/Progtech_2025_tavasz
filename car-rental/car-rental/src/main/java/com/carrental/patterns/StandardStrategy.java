package com.carrental.patterns;

import com.carrental.model.Car;

public class StandardStrategy implements PricingStrategy {
    public double price(Car c, int w) {
        double cost = c.getWeeklyRate() * w;
        return w >= 4 ? cost * 0.9 : cost;
    }
}
