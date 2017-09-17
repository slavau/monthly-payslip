package org.monthly.payslip;

import org.monthly.payslip.tax.Tax;
import org.monthly.payslip.tax.TaxService;

import java.util.Optional;

public class PayslipGenerator {

    private static final int MONTHS_IN_YEAR = 12;

    private final TaxService taxService;

    public PayslipGenerator(TaxService taxService) {
        this.taxService = taxService;
    }

    public PayslipDetails generate(Employee employee) {
        double grossIncome = calculateGrossIncome(employee.getAnnualSalary());
        double incomeTax = calculateIncomeTax(employee.getAnnualSalary());
        double netIncome = grossIncome - incomeTax;
        double superAmount = calculateSuperAmount(employee.getSuperRate(), grossIncome);
        return new PayslipDetails(null, null, (int)grossIncome, (int)incomeTax, (int)netIncome, (int)superAmount);
    }

    private long calculateSuperAmount(float superRate, double grossIncome) {
        return Math.round(grossIncome * (superRate / 100));
    }

    private double calculateIncomeTax(int annualSalary) {
        Optional<Tax> tax = taxService.getTaxFor(annualSalary);
        if (tax.isPresent()) {
            Tax taxDetails = tax.get();
            return Math.round((taxDetails.getBaseTax() + ((annualSalary - taxDetails.getFromIncome()) * (taxDetails.getPercentage() / 100))) / MONTHS_IN_YEAR);
        } else {
            return 0;
        }
    }

    private double calculateGrossIncome(int annualSalary) {
        return Math.round(annualSalary / MONTHS_IN_YEAR);
    }
}
