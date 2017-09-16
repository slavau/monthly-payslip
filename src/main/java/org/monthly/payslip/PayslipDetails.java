package org.monthly.payslip;

import java.util.Date;

public class PayslipDetails {

    private final Date payStartDate;
    private final Date payEndDate;
    private final int incomeTax;
    private final int grossIncome;
    private final int netIncome;
    private final int superAmount;

    public PayslipDetails(Date payStartDate, Date payEndDate, int grossIncome, int incomeTax, int netIncome, int superAmount) {
        this.payStartDate = payStartDate;
        this.payEndDate = payEndDate;
        this.grossIncome = grossIncome;
        this.incomeTax = incomeTax;
        this.netIncome = netIncome;
        this.superAmount = superAmount;
    }

    public Date getPayStartDate() {
        return payStartDate;
    }

    public Date getPayEndDate() {
        return payEndDate;
    }

    public int getGrossIncome() {
        return grossIncome;
    }

    public int getIncomeTax() {
        return incomeTax;
    }

    public int getNetIncome() {
        return netIncome;
    }

    public int getSuperAmount() {
        return superAmount;
    }
}
