package org.monthly.payslip;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PayslipApplicationTest {

    @InjectMocks
    private PayslipApplication payslipApplication;

    @Mock
    private PayslipGenerator payslipGenerator;

    @Test
    public void testProducesPayslipDetailsBasedOnEmployeeInput() {
        when(payslipGenerator.generate(new Employee("David", "Rudd", 60050, 9f, "01 March – 31 March")))
                .thenReturn(new PayslipDetails(null, null, 5004, 922, 4082, 450));

        ByteArrayInputStream inputStream = createStringInputStream("David,Rudd,60050,9%,01 March – 31 March");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        payslipApplication.generatePayslip(inputStream, new PrintStream(byteArrayOutputStream));

        assertThat(new String(byteArrayOutputStream.toByteArray()), is("5004,922,4082,450\n"));
    }

    @Test
    public void testHandlesMultiLineInput() {
        when(payslipGenerator.generate(new Employee("David", "Rudd", 60050, 9f, "01 March – 31 March")))
                .thenReturn(new PayslipDetails(null, null, 5004, 922, 4082, 450));
        when(payslipGenerator.generate(new Employee("Ryan", "Chen", 120000, 10f, "01 March – 31 March")))
                .thenReturn(new PayslipDetails(null, null, 10000, 2696, 7304, 1000));

        ByteArrayInputStream inputStream = createStringInputStream("David,Rudd,60050,9%,01 March – 31 March","Ryan,Chen,120000,10%,01 March – 31 March");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        payslipApplication.generatePayslip(inputStream, new PrintStream(byteArrayOutputStream));

        assertThat(new String(byteArrayOutputStream.toByteArray()), is("5004,922,4082,450\n10000,2696,7304,1000\n"));
    }

    private ByteArrayInputStream createStringInputStream(String ...inputLines) {
        return new ByteArrayInputStream(stream(inputLines).collect(Collectors.joining("\n")).getBytes());
    }
}