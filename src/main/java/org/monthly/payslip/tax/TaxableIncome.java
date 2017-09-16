package org.monthly.payslip.tax;

public class TaxableIncome {

    private final int from;
    private final int to;

    public TaxableIncome(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public boolean isWithinRange(int income) {
        return from <= income && income <= to;
    }
}
