package org.monthly.payslip.tax;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TaxCalculatorTest {

    private TaxCalculator taxCalculator = new TaxCalculator();

    @Before
    public void setup() {
        taxCalculator.addTax(0, 18200, 0, 0);
        taxCalculator.addTax(18201, 37000, 0, 19);
        taxCalculator.addTax(37001, 80000, 3572, 32.5f);
        taxCalculator.addTax(80001, 180000, 17547, 37f);
        taxCalculator.addTax(180001, Integer.MAX_VALUE, 54547, 45f);
    }

    @Test
    public void testGenerateWillCalculateIncomeTaxForSuppliedSalary() {
        assertThat(taxCalculator.calculateIncomeTax(33000), is(2812.00));
        assertThat(taxCalculator.calculateIncomeTax(60050), is(11063.25));
        assertThat(taxCalculator.calculateIncomeTax(120000), is(32347.0));
        assertThat(taxCalculator.calculateIncomeTax(250000), is(86047.0));
    }

    @Test
    public void testGenerateWillReturnZeroIncomeTaxWhenSalaryIsNotTaxable() {
        assertThat(taxCalculator.calculateIncomeTax(15000), is(0.0));
    }

    @Test(expected = IllegalStateException.class)
    public void testThrowExceptionIsTaxIsNotConfigured() {
        new TaxCalculator().calculateIncomeTax(10000);
    }
}