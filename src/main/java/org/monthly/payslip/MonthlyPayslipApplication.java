package org.monthly.payslip;

import org.monthly.payslip.domain.Employee;
import org.monthly.payslip.tax.TaxCalculator;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.function.Function;

public class MonthlyPayslipApplication {

    private MonthlyPayslipGenerator monthlyPayslipGenerator;

    public MonthlyPayslipApplication(MonthlyPayslipGenerator monthlyPayslipGenerator) {
        this.monthlyPayslipGenerator = monthlyPayslipGenerator;
    }

    public static void main(String[] args) {
        // TODO: Usage

        TaxCalculator taxCalculator = new TaxCalculator();
        MonthlyPayslipGenerator monthlyPayslipGenerator = new MonthlyPayslipGenerator(taxCalculator);
        MonthlyPayslipApplication monthlyPayslipApplication = new MonthlyPayslipApplication(monthlyPayslipGenerator);

        monthlyPayslipApplication.generatePayslip(System.in, System.out);
    }

    public void generatePayslip(InputStream inputStream, PrintStream outputStream) {
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        in.lines()
                .map(convertToEmployee())
                .map(monthlyPayslipGenerator::generate)
                .forEach(payslipDetails -> outputStream.println(payslipDetails.toCsv()));

    }

    private Function<String, Employee> convertToEmployee() {
        return csvLine -> {
            String[] fields = csvLine.split(",");
            if (fields.length != 5) {
                throw new IllegalArgumentException("Wrong format of the CSV input");
            }

            int annualSalary = Integer.parseInt(readField(fields[2]));
            float superRate = Float.parseFloat(readField(fields[3]).replace("%", ""));
            return new Employee(readField(fields[0]), readField(fields[1]), annualSalary, superRate, readField(fields[4]));
        };
    }

    private String readField(String field) {
        return field.trim();
    }
}
