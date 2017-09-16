package org.monthly.payslip.tax;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TaxServiceTest {

    private TaxService taxService = new TaxService();

    @Test
    public void testGetTaxWillReturnTaxDetailsForSpecifiedIncome() {
        Tax tax = new Tax(3572, 32.5f, 37000);
        taxService.addTax(new TaxableIncome(37001, 87000), tax);

        assertThat(taxService.getTaxFor(60050).get(), is(tax));
    }

    @Test
    public void testGetTaxWillReturnEmptyWhenTaxIncomeDidNotMatch() {
        Tax tax = new Tax(3572, 32.5f, 37000);
        taxService.addTax(new TaxableIncome(37001, 87000), tax);

        assertThat(taxService.getTaxFor(15000).isPresent(), is(false));
    }
}