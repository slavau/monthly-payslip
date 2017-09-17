package org.monthly.payslip;

import org.monthly.payslip.domain.Employee;
import org.monthly.payslip.domain.PayslipDetails;
import org.monthly.payslip.tax.TaxCalculator;

/**
 * Generates monthly payslip for provided Employees details.
 * <p>
 * NOTE: ONLY supports full month calculations. Does not calculate based on days or weeks.
 * All the amount are rounded to a dollar value.
 */
public class MonthlyPayslipGenerator {

    private static final int MONTHS_IN_YEAR = 12;

    private final TaxCalculator taxCalculator;

    public MonthlyPayslipGenerator(TaxCalculator taxCalculator) {
        this.taxCalculator = taxCalculator;
    }

    public PayslipDetails generate(Employee employee) {
        long grossIncome = calculateGrossIncome(employee.getAnnualSalary());
        long incomeTax = calculateIncomeTax(employee.getAnnualSalary());
        long netIncome = grossIncome - incomeTax;
        long superAmount = calculateSuperAmount(employee.getSuperRate(), grossIncome);
        return new PayslipDetails(employee.getPaymentPeriod(), employee.getFirstName(), employee.getLastName(),
                grossIncome, incomeTax, netIncome, superAmount);
    }

    private long calculateIncomeTax(long annualSalary) {
        return Math.round(taxCalculator.calculateIncomeTax(annualSalary) / MONTHS_IN_YEAR);
    }

    private long calculateSuperAmount(float superRate, long grossIncome) {
        return Math.round(grossIncome * (superRate / 100));
    }

    private long calculateGrossIncome(long annualSalary) {
        return Math.round(annualSalary / MONTHS_IN_YEAR);
    }
}
