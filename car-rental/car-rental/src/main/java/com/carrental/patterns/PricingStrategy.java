// PricingStrategy.java  (+ two concrete strategies)
package com.carrental.patterns;
import com.carrental.model.Car;
import com.carrental.model.CarType;

public interface PricingStrategy {
    /** adjust base price, car-type aware */
    double apply(double base, int days, CarType type);
}
