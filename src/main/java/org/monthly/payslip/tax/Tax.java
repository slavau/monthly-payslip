package org.monthly.payslip.tax;

/**
 * Object containing tax data. Used to calculate income tax.
 * <p>
 * All the amount are represented as integers, hence the limitation.
 * More info see: https://en.wikipedia.org/wiki/Income_tax_in_Australia#Personal_income_tax
 */
public class Tax {

    private final float baseTax;
    private final float percentage;
    private final long fromIncome;

    public Tax(float baseTax, float percentage, long fromIncome) {
        this.baseTax = baseTax;
        this.percentage = percentage;
        this.fromIncome = fromIncome;
    }

    public float getBaseTax() {
        return baseTax;
    }

    public float getPercentage() {
        return percentage;
    }

    public long getFromIncome() {
        return fromIncome;
    }
}
