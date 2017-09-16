package org.monthly.payslip;

import org.monthly.payslip.tax.Tax;
import org.monthly.payslip.tax.TaxService;

import java.util.Date;
import java.util.Optional;

public class PayslipGenerator {

    private static final int MONTHS_IN_YEAR = 12;

    private final TaxService taxService;

    public PayslipGenerator(TaxService taxService) {
        this.taxService = taxService;
    }

    public PayslipDetails generateFor(int annualSalary, float superRate, Date paymentStartDate) {
        double grossIncome = calculateGrossIncome(annualSalary);
        double incomeTax = calculateIncomeTax(annualSalary);
        double netIncome = grossIncome - incomeTax;
        double superAmount = calculateSuperAmount(superRate, grossIncome);
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
