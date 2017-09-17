package org.monthly.payslip.tax;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Personal tax calculation module. Calculates tax based on the tax brackets and supplied annualSalary.
 * <p>
 * This tax calculations DO NOT include any of the levies (The Medicare levy of 2%) etc.
 * <p>
 * NOTE: This version only supports 2012-13 tax brackets. Ideally those tax values should be associated with the year. But pay range is currently not being used for any calculations.
 */
public class TaxCalculator {

    private final Map<TaxableIncome, Tax> taxBracketsReference = new HashMap<>();

    /**
     * Configure tax details based on the salary ranges. If tax is not applicable simple provide 0 value for baseTax and taxPerDollarRate.
     * If this was a Spring Boot CLI application, then we would use proper configuration.
     */
    public void addTax(long incomeFrom, long incomeTo, float baseTax, float taxPerDollarRate) {
        taxBracketsReference.put(new TaxableIncome(incomeFrom, incomeTo), new Tax(baseTax, taxPerDollarRate, incomeFrom - 1));
    }

    /**
     * Will calculate tax based on tax brackets and provided salary.
     * <p>
     * Will throw IllegalStateException if tax has not been configured for provided salary.
     */
    public double calculateIncomeTax(long annualSalary) {
        Optional<Tax> tax = getTaxFor(annualSalary);
        if (!tax.isPresent()) {
            throw new IllegalStateException(String.format("Could not file tax for '%s' income", annualSalary));
        }
        Tax taxDetails = tax.get();
        return taxDetails.getBaseTax() + ((annualSalary - taxDetails.getFromIncome()) * (taxDetails.getPercentage() / 100));
    }

    private Optional<Tax> getTaxFor(long income) {
        for (Map.Entry<TaxableIncome, Tax> taxReference : taxBracketsReference.entrySet()) {
            if (taxReference.getKey().isWithinRange(income)) {
                return Optional.of(taxReference.getValue());
            }
        }
        return Optional.empty();
    }
}
