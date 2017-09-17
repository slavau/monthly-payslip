package org.monthly.payslip;

import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PayslipDetails {

    private final Date payStartDate;
    private final Date payEndDate;
    private final int incomeTax;
    private final int grossIncome;
    private final int netIncome;
    private final int superAmount;

    public PayslipDetails(Date payStartDate, Date payEndDate, int grossIncome, int incomeTax, int netIncome, int superAmount) {
        this.payStartDate = payStartDate;
        this.payEndDate = payEndDate;
        this.grossIncome = grossIncome;
        this.incomeTax = incomeTax;
        this.netIncome = netIncome;
        this.superAmount = superAmount;
    }

    public Date getPayStartDate() {
        return payStartDate;
    }

    public Date getPayEndDate() {
        return payEndDate;
    }

    public int getGrossIncome() {
        return grossIncome;
    }

    public int getIncomeTax() {
        return incomeTax;
    }

    public int getNetIncome() {
        return netIncome;
    }

    public int getSuperAmount() {
        return superAmount;
    }

    public String toCsv() {
        return Stream.of(grossIncome, incomeTax, netIncome, superAmount)
                .map(Object::toString)
                .collect(Collectors.joining(","));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PayslipDetails that = (PayslipDetails) o;
        return Objects.equals(incomeTax, that.incomeTax) &&
                Objects.equals(grossIncome, that.grossIncome) &&
                Objects.equals(netIncome, that.netIncome) &&
                Objects.equals(superAmount, that.superAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(incomeTax, grossIncome, netIncome, superAmount);
    }
}
