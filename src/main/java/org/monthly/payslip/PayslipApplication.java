package org.monthly.payslip;

import org.monthly.payslip.tax.TaxService;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.function.Function;

public class PayslipApplication {

    private PayslipGenerator payslipGenerator;

    public PayslipApplication(PayslipGenerator payslipGenerator) {
        this.payslipGenerator = payslipGenerator;
    }

    public static void main(String[] args) {
        // TODO: Usage

        TaxService taxService = new TaxService();
        PayslipGenerator payslipGenerator = new PayslipGenerator(taxService);
        PayslipApplication payslipApplication = new PayslipApplication(payslipGenerator);

        payslipApplication.generatePayslip(System.in, System.out);
    }

    public void generatePayslip(InputStream inputStream, PrintStream outputStream) {
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        in.lines()
                .map(convertToEmployee())
                .map(payslipGenerator::generate)
                .forEach(payslipDetails -> outputStream.println(payslipDetails.toCsv()));

    }

    private Function<String, Employee> convertToEmployee() {
        return csvLine -> {
            String[] fields = csvLine.split(",");
            if (fields.length != 5) {
                throw new IllegalArgumentException("Wrong format of the CSV input");
            }

            return new Employee(fields[0].trim(), fields[1].trim(), Integer.parseInt(fields[2].trim()), Float.parseFloat(fields[3].trim().replace("%", "")), fields[4].trim());
        };
    }
}
