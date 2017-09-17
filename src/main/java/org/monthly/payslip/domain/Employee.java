package org.monthly.payslip.domain;

import java.util.Objects;

/**
 * Employee domain object contains information about employee received from user input.
 * <p>
 * annualSalary value must be above 0
 * paymentPeriod is not being used in the calculations so we will just store String value unchanged
 */
public class Employee {

    private final String firstName;
    private final String lastName;
    private final long annualSalary;
    private final float superRate;
    private final String paymentPeriod;

    public Employee(String firstName, String lastName, long annualSalary, float superRate, String paymentPeriod) {
        validateSalary(annualSalary);

        this.firstName = firstName;
        this.lastName = lastName;
        this.annualSalary = annualSalary;
        this.superRate = superRate;
        this.paymentPeriod = paymentPeriod;
    }

    public Employee(long annualSalary, float superRate) {
        this("", "", annualSalary, superRate, "");
    }

    private void validateSalary(long annualSalary) {
        if (annualSalary <= 0) {
            throw new IllegalArgumentException("Employees annual salary must be greater than 0");
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getAnnualSalary() {
        return annualSalary;
    }

    public float getSuperRate() {
        return superRate;
    }

    public String getPaymentPeriod() {
        return paymentPeriod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(annualSalary, employee.annualSalary) &&
                Objects.equals(superRate, employee.superRate) &&
                Objects.equals(firstName, employee.firstName) &&
                Objects.equals(lastName, employee.lastName) &&
                Objects.equals(paymentPeriod, employee.paymentPeriod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, annualSalary, superRate, paymentPeriod);
    }
}
