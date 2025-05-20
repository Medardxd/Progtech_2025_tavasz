// PricingStrategy.java  (+ two concrete strategies)
package com.carrental.patterns;
import com.carrental.model.Car;
public interface PricingStrategy { double price(Car c,int weeks); }
