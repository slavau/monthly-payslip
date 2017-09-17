package org.monthly.payslip.domain;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Monthly payslip details calculated based on an employee data.
 */
public class PayslipDetails {

    private final String paymentPeriod;
    private final String employeeFirstName;
    private final String employeeLastName;
    private final long incomeTax;
    private final long grossIncome;
    private final long netIncome;
    private final long superAmount;

    public PayslipDetails(String paymentPeriod, String employeeFirstName, String employeeLastName,
                          long grossIncome, long incomeTax, long netIncome, long superAmount) {
        this.paymentPeriod = paymentPeriod;
        this.employeeFirstName = employeeFirstName;
        this.employeeLastName = employeeLastName;
        this.grossIncome = grossIncome;
        this.incomeTax = incomeTax;
        this.netIncome = netIncome;
        this.superAmount = superAmount;
    }

    public long getIncomeTax() {
        return incomeTax;
    }

    public long getGrossIncome() {
        return grossIncome;
    }

    public long getNetIncome() {
        return netIncome;
    }

    public long getSuperAmount() {
        return superAmount;
    }

    /**
     * Converts into CSV format to be returned to user. NOTE: does not handle commas already in the fields.
     */
    public String toCsv() {
        return Stream.of(employeeFirstName, employeeLastName, paymentPeriod, grossIncome, incomeTax, netIncome, superAmount)
                .map(Object::toString)
                .collect(Collectors.joining(","));
    }
}
