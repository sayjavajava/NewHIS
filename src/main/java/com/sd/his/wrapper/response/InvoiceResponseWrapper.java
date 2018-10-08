package com.sd.his.wrapper.response;

import com.sd.his.model.Invoice;

public class InvoiceResponseWrapper {

    long id;
    String invoiceId;
    String patientName;

    Double paidAmount;
    Double invoiceAmount;
    String status;

    private Double balance;
    private Double totalBill;
    private Double totalPaid;
    private Double advanceBalance;

    public InvoiceResponseWrapper(Invoice invoice) {
        this.id = invoice.getId();
        this.invoiceId = invoice.getInvoiceId();
        this.patientName = invoice.getPatient().getFirstName()+ " " + invoice.getPatient().getLastName();
        this.paidAmount = invoice.getPaidAmount();
        this.invoiceAmount = invoice.getInvoiceAmount();
        this.status = invoice.getStatus();
    }

    public InvoiceResponseWrapper(Double balance, Double totalBill, Double totalPaid, Double advBalance){
        this.balance = balance;
        this.totalBill = totalBill;
        this.totalPaid = totalPaid;
        this.advanceBalance = advBalance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Double getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(Double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(Double totalBill) {
        this.totalBill = totalBill;
    }

    public Double getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(Double totalPaid) {
        this.totalPaid = totalPaid;
    }

    public Double getAdvanceBalance() {
        return advanceBalance;
    }

    public void setAdvanceBalance(Double advanceBalance) {
        this.advanceBalance = advanceBalance;
    }
}
