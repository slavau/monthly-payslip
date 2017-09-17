package org.monthly.payslip.tax;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TaxCalculatorTest {

    private TaxCalculator taxCalculator = new TaxCalculator();

    @Test
    public void testGetTaxWillReturnTaxDetailsForSpecifiedIncome() {
        Tax tax = setupTax();

        assertThat(taxCalculator.getTaxFor(60050).get(), is(tax));
    }

    @Test
    public void testGetTaxWillReturnEmptyWhenTaxIncomeDidNotMatch() {
        setupTax();

        assertThat(taxCalculator.getTaxFor(15000).isPresent(), is(false));
    }

    @Test
    public void testGenerateWillCalculateIncomeTaxForSuppliedSalary() {
        setupTax();

        assertThat(taxCalculator.calculateIncomeTax(60050), is(11063.25));
    }

    @Test
    public void testGenerateWillReturnZeroIncomeTaxWhenSalaryIsNotTaxable() {
        setupTax();

        assertThat(taxCalculator.calculateIncomeTax(15000), is(0.0));
    }

    private Tax setupTax() {
        Tax tax = new Tax(3572, 32.5f, 37000);
        taxCalculator.addTax(new TaxableIncome(37001, 87000), tax);
        return tax;
    }
}