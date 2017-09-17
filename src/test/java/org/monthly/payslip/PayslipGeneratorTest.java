package org.monthly.payslip;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.monthly.payslip.tax.Tax;
import org.monthly.payslip.tax.TaxService;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PayslipGeneratorTest {

    private static final int ANNUAL_SALARY = 60050;

    @InjectMocks
    private PayslipGenerator payslipGenerator;

    @Mock
    private TaxService taxService;

    @Test
    public void testGenerateWillCalculateGrossIncomeForSuppliedSalary() {
        PayslipDetails payslipDetails = payslipGenerator.generate(new Employee(ANNUAL_SALARY, 9.0f));

        assertThat(payslipDetails.getGrossIncome(), is(5004));
    }

    @Test
    public void testGenerateWillCalculateIncomeTaxForSuppliedSalary() {
        when(taxService.getTaxFor(ANNUAL_SALARY)).thenReturn(Optional.of(new Tax(3572, 32.5f, 37000)));

        PayslipDetails payslipDetails = payslipGenerator.generate(new Employee(ANNUAL_SALARY, 9.0f));

        assertThat(payslipDetails.getIncomeTax(), is(922));
    }

    @Test
    public void testGenerateWillReturnZeroIncomeTaxWhenSalaryIsNotTaxable() {
        PayslipDetails payslipDetails = payslipGenerator.generate(new Employee(15000, 9.0f));

        assertThat(payslipDetails.getIncomeTax(), is(0));
    }

    @Test
    public void testGenerateWillCalculateNetIncomeForSuppliedSalary() {
        when(taxService.getTaxFor(ANNUAL_SALARY)).thenReturn(Optional.of(new Tax(3572, 32.5f, 37000)));

        PayslipDetails payslipDetails = payslipGenerator.generate(new Employee(ANNUAL_SALARY, 9.0f));

        assertThat(payslipDetails.getNetIncome(), is(4082));
    }

    @Test
    public void testGenerateWillCalculateSuperForSuppliedSalary() {
        when(taxService.getTaxFor(ANNUAL_SALARY)).thenReturn(Optional.of(new Tax(3572, 32.5f, 37000)));

        PayslipDetails payslipDetails = payslipGenerator.generate(new Employee(ANNUAL_SALARY, 9.0f));

        assertThat(payslipDetails.getSuperAmount(), is(450));
    }
}