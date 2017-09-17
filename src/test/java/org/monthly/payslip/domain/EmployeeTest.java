package org.monthly.payslip.domain;

import org.junit.Test;

public class EmployeeTest {

    @Test(expected = IllegalArgumentException.class)
    public void testThrowsValidationErrorWhenEmployeesSalaryAmountIsNegative() {
        new Employee("", "", -1, 0.0f, "");
    }
}