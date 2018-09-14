package com.sd.his.wrapper.request;

public class PaymentRequestWrapper {


    long id;
    long invoiceId;
    Double paidAmount;
    Double invoiceAmount;
    String status;

    Boolean useAdvancedBal;
    Double  patientAdvanceDeposit;

    public PaymentRequestWrapper() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
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

    public Boolean getUseAdvancedBal() {
        return useAdvancedBal;
    }

    public void setUseAdvancedBal(Boolean useAdvancedBal) {
        this.useAdvancedBal = useAdvancedBal;
    }

    public Double getPatientAdvanceDeposit() {
        return patientAdvanceDeposit;
    }

    public void setPatientAdvanceDeposit(Double patientAdvanceDeposit) {
        this.patientAdvanceDeposit = patientAdvanceDeposit;
    }
}
