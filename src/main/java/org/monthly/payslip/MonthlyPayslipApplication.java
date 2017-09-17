package org.monthly.payslip;

import org.monthly.payslip.domain.Employee;
import org.monthly.payslip.tax.TaxCalculator;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.function.Function;

import static java.nio.charset.Charset.defaultCharset;

/**
 * Generates and displays monthly payslips for provided employees.
 * <p>
 * It accepts multiline input in the CSV format from StdIn and prints output in the CSV format to StdOut.
 * <p>
 * It only configures/supports TaxRates for 2012-13 financial year. see more: https://en.wikipedia.org/wiki/Income_tax_in_Australia#Personal_income_tax
 */
public class MonthlyPayslipApplication {

    private MonthlyPayslipGenerator monthlyPayslipGenerator;

    public MonthlyPayslipApplication(MonthlyPayslipGenerator monthlyPayslipGenerator) {
        this.monthlyPayslipGenerator = monthlyPayslipGenerator;
    }

    public static void main(String[] args) {
        // If this was a Spring boot app the following would be done using Spring dependency injection mechanism.
        MonthlyPayslipGenerator monthlyPayslipGenerator = new MonthlyPayslipGenerator(setupSampleTaxCalculator());
        MonthlyPayslipApplication monthlyPayslipApplication = new MonthlyPayslipApplication(monthlyPayslipGenerator);
        monthlyPayslipApplication.generatePayslip(System.in, System.out);
    }

    public void generatePayslip(InputStream inputStream, PrintStream outputStream) {
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, defaultCharset()));
        in.lines()
                .filter(line -> !line.isEmpty())
                .map(convertToEmployee())
                .map(monthlyPayslipGenerator::generate)
                .forEach(payslipDetails -> outputStream.println(payslipDetails.toCsv()));
    }

    private Function<String, Employee> convertToEmployee() {
        return csvLine -> {
            String[] fields = readCsvFields(csvLine);

            long annualSalary = Long.parseLong(fieldData(fields[2]));
            float superRate = Float.parseFloat(fieldData(fields[3]).replace("%", ""));
            return new Employee(fieldData(fields[0]), fieldData(fields[1]), annualSalary,
                    superRate, fieldData(fields[4]));
        };
    }

    private String[] readCsvFields(String csvLine) {
        String[] fields = csvLine.split(",");
        if (fields.length != 5) {
            throw new IllegalArgumentException("Wrong format of the CSV input");
        }
        return fields;
    }

    private String fieldData(String field) {
        return field.trim();
    }

    private static TaxCalculator setupSampleTaxCalculator() {
        TaxCalculator taxCalculator = new TaxCalculator();
        // If this was a Spring boot cli app the following would come form Configuration.
        taxCalculator.addTax(0, 18200, 0, 0);
        taxCalculator.addTax(18201, 37000, 0, 19);
        taxCalculator.addTax(37001, 80000, 3572, 32.5f);
        taxCalculator.addTax(80001, 180000, 17547, 37f);
        taxCalculator.addTax(180001, Long.MAX_VALUE, 54547, 45f);
        return taxCalculator;
    }
}
