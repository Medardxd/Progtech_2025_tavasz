// Rental.java
package com.carrental.patterns;
public interface Rental { double cost(); String details(); void accept(RentalVisitor v); }
