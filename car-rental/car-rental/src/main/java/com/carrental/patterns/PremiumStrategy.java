package com.carrental.patterns;

import com.carrental.model.Car;

public class PremiumStrategy implements PricingStrategy {
    public double price(Car c, int w) {
        return c.getWeeklyRate() * w * 1.2;
    }
}
