package org.monthly.payslip.domain;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PayslipDetails {

    private final String paymentPeriod;
    private final String employeeFirstName;
    private final String employeeLastName;
    private final int incomeTax;
    private final int grossIncome;
    private final int netIncome;
    private final int superAmount;

    public PayslipDetails(String paymentPeriod, String employeeFirstName, String employeeLastName,
                          int grossIncome, int incomeTax, int netIncome, int superAmount) {
        this.paymentPeriod = paymentPeriod;
        this.employeeFirstName = employeeFirstName;
        this.employeeLastName = employeeLastName;
        this.grossIncome = grossIncome;
        this.incomeTax = incomeTax;
        this.netIncome = netIncome;
        this.superAmount = superAmount;
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
        return Stream.of(employeeFirstName, employeeLastName, paymentPeriod, grossIncome, incomeTax, netIncome, superAmount)
                .map(Object::toString)
                .collect(Collectors.joining(","));
    }
}
