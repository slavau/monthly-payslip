package org.monthly.payslip;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.monthly.payslip.domain.Employee;
import org.monthly.payslip.domain.PayslipDetails;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MonthlyPayslipApplicationTest {

    @InjectMocks
    private MonthlyPayslipApplication monthlyPayslipApplication;

    @Mock
    private MonthlyPayslipGenerator monthlyPayslipGenerator;

    @Test
    public void testProducesPayslipDetailsBasedOnEmployeeInput() {
        when(monthlyPayslipGenerator.generate(new Employee("David", "Rudd", 60050, 9f, "01 March – 31 March")))
                .thenReturn(new PayslipDetails("01 March – 31 March", "David", "Rudd", 5004, 922, 4082, 450));

        ByteArrayInputStream inputStream = createStringInputStream("David,Rudd,60050,9%,01 March – 31 March");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        monthlyPayslipApplication.generatePayslip(inputStream, new PrintStream(byteArrayOutputStream));

        assertThat(new String(byteArrayOutputStream.toByteArray()), is("David,Rudd,01 March – 31 March,5004,922,4082,450\n"));
    }

    @Test
    public void testHandlesMultiLineInput() {
        when(monthlyPayslipGenerator.generate(new Employee("David", "Rudd", 60050, 9f, "01 March – 31 March")))
                .thenReturn(new PayslipDetails("01 March – 31 March", "David", "Rudd", 5004, 922, 4082, 450));
        when(monthlyPayslipGenerator.generate(new Employee("Ryan", "Chen", 120000, 10f, "01 March – 31 March")))
                .thenReturn(new PayslipDetails("01 March – 31 March", "Ryan", "Chen", 10000, 2696, 7304, 1000));

        ByteArrayInputStream inputStream = createStringInputStream("David,Rudd,60050,9%,01 March – 31 March","Ryan,Chen,120000,10%,01 March – 31 March");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        monthlyPayslipApplication.generatePayslip(inputStream, new PrintStream(byteArrayOutputStream));

        assertThat(new String(byteArrayOutputStream.toByteArray()), is("David,Rudd,01 March – 31 March,5004,922,4082,450\nRyan,Chen,01 March – 31 March,10000,2696,7304,1000\n"));
    }

    private ByteArrayInputStream createStringInputStream(String ...inputLines) {
        return new ByteArrayInputStream(stream(inputLines).collect(Collectors.joining("\n")).getBytes());
    }
}