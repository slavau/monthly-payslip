package org.monthly.payslip.tax;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TaxCalculatorTest {

    private TaxCalculator taxCalculator = new TaxCalculator();

    @Before
    public void setup() {
        taxCalculator.addTax(37001, 87000, 3572, 32.5f);
    }

    @Test
    public void testGenerateWillCalculateIncomeTaxForSuppliedSalary() {
        assertThat(taxCalculator.calculateIncomeTax(60050), is(11063.25));
    }

    @Test
    public void testGenerateWillReturnZeroIncomeTaxWhenSalaryIsNotTaxable() {
        assertThat(taxCalculator.calculateIncomeTax(15000), is(0.0));
    }
}