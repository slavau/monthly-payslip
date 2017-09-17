package org.monthly.payslip.tax;

/**
 * Domain objects represents income tax ranges.
 */
public class TaxableIncome {

    private final long from;
    private final long to;

    public TaxableIncome(long from, long to) {
        this.from = from;
        this.to = to;
    }

    /**
     * Returns true if provided income falls between from and to values, false otherwise.
     */
    public boolean isWithinRange(long income) {
        return from <= income && income <= to;
    }
}
