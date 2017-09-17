package org.monthly.payslip.tax;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TaxCalculator {

    private final Map<TaxableIncome, Tax> taxBracketsReference = new HashMap<>();

    public void addTax(int incomeFrom, int incomeTo, float baseTax, float taxPerDollarRate) {
        taxBracketsReference.put(new TaxableIncome(incomeFrom, incomeTo), new Tax(baseTax, taxPerDollarRate, incomeFrom - 1));
    }

    public double calculateIncomeTax(int annualSalary) {
        Optional<Tax> tax = getTaxFor(annualSalary);
        if (!tax.isPresent()) {
            throw new IllegalStateException(String.format("Could not file tax for '%s' income", annualSalary));
        }
        Tax taxDetails = tax.get();
        return taxDetails.getBaseTax() + ((annualSalary - taxDetails.getFromIncome()) * (taxDetails.getPercentage() / 100));
    }

    private Optional<Tax> getTaxFor(int income) {
        for (Map.Entry<TaxableIncome, Tax> taxReference : taxBracketsReference.entrySet()) {
            if (taxReference.getKey().isWithinRange(income)) {
                return Optional.of(taxReference.getValue());
            }
        }
        return Optional.empty();
    }
}
