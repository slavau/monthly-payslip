package org.monthly.payslip.tax;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TaxService {

    private final Map<TaxableIncome, Tax> taxBracketsReference = new HashMap<>();

    // TODO: initialize it in a better way
    public void addTax(TaxableIncome taxableIncome, Tax tax) {
        taxBracketsReference.put(taxableIncome, tax);
    }

    public Optional<Tax> getTaxFor(int income) {
        for (Map.Entry<TaxableIncome, Tax> taxReference : taxBracketsReference.entrySet()) {
            if (taxReference.getKey().isWithinRange(income)) {
                return Optional.of(taxReference.getValue());
            }
        }

        return Optional.empty();
    }
}
