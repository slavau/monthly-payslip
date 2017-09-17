package org.monthly.payslip;

import java.util.Objects;

public class Employee {

    private final String firstName;
    private final String lastName;
    private final int annualSalary;
    private final float superRate;
    private final String paymentMonth;

    public Employee(String firstName, String lastName, int annualSalary, float superRate, String paymentMonth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.annualSalary = annualSalary;
        this.superRate = superRate;
        this.paymentMonth = paymentMonth;
    }

    public Employee(int annualSalary, float superRate) {
        this("", "", annualSalary, superRate, "");
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAnnualSalary() {
        return annualSalary;
    }

    public float getSuperRate() {
        return superRate;
    }

    public String getPaymentMonth() {
        return paymentMonth;
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
                Objects.equals(paymentMonth, employee.paymentMonth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, annualSalary, superRate, paymentMonth);
    }
}
