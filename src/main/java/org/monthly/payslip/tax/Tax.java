package org.monthly.payslip.tax;

public class Tax {

    private final float baseTax;
    private final float percentage;
    private final int fromIncome;

    public Tax(float baseTax, float percentage, int fromIncome) {
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

    public int getFromIncome() {
        return fromIncome;
    }
}
