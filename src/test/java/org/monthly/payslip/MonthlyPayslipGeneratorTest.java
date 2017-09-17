package org.monthly.payslip;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.monthly.payslip.domain.Employee;
import org.monthly.payslip.domain.PayslipDetails;
import org.monthly.payslip.tax.TaxCalculator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MonthlyPayslipGeneratorTest {

    private static final long ANNUAL_SALARY = 60050;

    @InjectMocks
    private MonthlyPayslipGenerator monthlyPayslipGenerator;

    @Mock
    private TaxCalculator taxCalculator;

    @Test
    public void testGenerateWillCalculateGrossIncomeForSuppliedSalary() {
        PayslipDetails payslipDetails = monthlyPayslipGenerator.generate(new Employee(ANNUAL_SALARY, 9.0f));

        assertThat(payslipDetails.getGrossIncome(), is(5004L));
    }

    @Test
    public void testGenerateWillCalculateIncomeTaxForSuppliedSalary() {
        when(taxCalculator.calculateIncomeTax(ANNUAL_SALARY)).thenReturn(11063.25);

        PayslipDetails payslipDetails = monthlyPayslipGenerator.generate(new Employee(ANNUAL_SALARY, 9.0f));

        assertThat(payslipDetails.getIncomeTax(), is(922L));
    }

    @Test
    public void testGenerateWillCalculateNetIncomeForSuppliedSalary() {
        when(taxCalculator.calculateIncomeTax(ANNUAL_SALARY)).thenReturn(11063.25);

        PayslipDetails payslipDetails = monthlyPayslipGenerator.generate(new Employee(ANNUAL_SALARY, 9.0f));

        assertThat(payslipDetails.getNetIncome(), is(4082L));
    }

    @Test
    public void testGenerateWillCalculateSuperForSuppliedSalary() {
        PayslipDetails payslipDetails = monthlyPayslipGenerator.generate(new Employee(ANNUAL_SALARY, 9.0f));

        assertThat(payslipDetails.getSuperAmount(), is(450L));
    }

    @Test
    public void testGenerateWillCalculateNoSuperWhenSuperRateIsZero() {
        PayslipDetails payslipDetails = monthlyPayslipGenerator.generate(new Employee(ANNUAL_SALARY, 0f));

        assertThat(payslipDetails.getSuperAmount(), is(0L));
    }
}