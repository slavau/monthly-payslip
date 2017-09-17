package org.monthly.payslip;

import org.monthly.payslip.domain.Employee;
import org.monthly.payslip.domain.PayslipDetails;
import org.monthly.payslip.tax.TaxCalculator;

public class MonthlyPayslipGenerator {

    private static final int MONTHS_IN_YEAR = 12;

    private final TaxCalculator taxCalculator;

    public MonthlyPayslipGenerator(TaxCalculator taxCalculator) {
        this.taxCalculator = taxCalculator;
    }

    public PayslipDetails generate(Employee employee) {
        double grossIncome = calculateGrossIncome(employee.getAnnualSalary());
        double incomeTax = calculateIncomeTax(employee.getAnnualSalary());
        double netIncome = grossIncome - incomeTax;
        double superAmount = calculateSuperAmount(employee.getSuperRate(), grossIncome);
        return new PayslipDetails(employee.getPaymentPeriod(), employee.getFirstName(), employee.getLastName(),
                (int) grossIncome, (int) incomeTax, (int) netIncome, (int) superAmount);
    }

    private long calculateIncomeTax(int annualSalary) {
        return Math.round(taxCalculator.calculateIncomeTax(annualSalary) / MONTHS_IN_YEAR);
    }

    private long calculateSuperAmount(float superRate, double grossIncome) {
        return Math.round(grossIncome * (superRate / 100));
    }

    private double calculateGrossIncome(int annualSalary) {
        return Math.round(annualSalary / MONTHS_IN_YEAR);
    }
}
