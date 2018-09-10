package com.sd.his.wrapper.response;

import com.sd.his.model.Invoice;

public class InvoiceResponseWrapper {

    long id;
    String invoiceId;
    String patientName;

    Double paidAmount;
    Double invoiceAmount;
    String status;

    public InvoiceResponseWrapper(Invoice invoice) {
        this.id = invoice.getId();
        this.invoiceId = invoice.getInvoiceId();
        this.patientName = invoice.getPatient().getFirstName()+ " " + invoice.getPatient().getLastName();
        this.paidAmount = invoice.getPaidAmount();
        this.invoiceAmount = invoice.getInvoiceAmount();
        this.status = invoice.getStatus();
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
}
