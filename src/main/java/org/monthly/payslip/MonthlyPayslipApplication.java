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
        if (args.length > 0) {
            printUsage();
            System.exit(1);
        }

        TaxCalculator taxCalculator = new TaxCalculator();
        setupTaxBracketsSampleData(taxCalculator);
        MonthlyPayslipGenerator monthlyPayslipGenerator = new MonthlyPayslipGenerator(taxCalculator);
        MonthlyPayslipApplication monthlyPayslipApplication = new MonthlyPayslipApplication(monthlyPayslipGenerator);

        monthlyPayslipApplication.generatePayslip(System.in, System.out);
    }

    private static void printUsage() {
        System.out.println("Usage: monthly-payslip takes input from StdIn in the CSV format");
    }

    public void generatePayslip(InputStream inputStream, PrintStream outputStream) {
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        in.lines()
                .filter(line -> !line.isEmpty())
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
            return new Employee(readField(fields[0]), readField(fields[1]), annualSalary,
                    superRate, readField(fields[4]));
        };
    }

    private String readField(String field) {
        return field.trim();
    }

    private static void setupTaxBracketsSampleData(TaxCalculator taxCalculator) {
        // If this was a Spring boot cli app the following would come form Configuration.
        taxCalculator.addTax(0, 18200, 0, 0);
        taxCalculator.addTax(18201, 37000, 0, 19);
        taxCalculator.addTax(37001, 80000, 3572, 32.5f);
        taxCalculator.addTax(80001, 180000, 17547, 37f);
        taxCalculator.addTax(180001, Integer.MAX_VALUE, 54547, 45f);
    }
}
