package com.carrental.patterns;
public class SummaryVisitor implements RentalVisitor{
    private final StringBuilder sb=new StringBuilder();
    public void visit(BasicRental r){
        sb.append(r.details()).append("  $")
                .append(String.format("%.2f",r.cost())).append('\n');
    }
    public String report(){return sb.toString();}
}