package com.carrental.patterns;

public class SummaryVisitor implements RentalVisitor {

    private final StringBuilder sb = new StringBuilder();
    private double grandTotal = 0;

    /* ===== traditional visitor entry point ========================= */
    @Override                // used anywhere you still pass real rentals
    public void visit(BasicRental r) {
        addLine(r.details(), r.cost());
    }

    /* ===== helper for “I already have the price” =================== */
    public void addLine(String text, double cost) {
        sb.append(text).append("  $")
                .append(String.format("%.2f", cost)).append('\n');
        grandTotal += cost;
    }

    public String report() {
        return sb.append("\nTOTAL: $")
                .append(String.format("%.2f", grandTotal))
                .toString();
    }
}
