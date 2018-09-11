package com.sd.his.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "INVOICE")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Invoice extends BaseEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    @NaturalId
    @Column(name = "INVOICE_ID")
    private String invoiceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PATIENT_ID", nullable = false)
    private Patient patient;

    @OneToOne
    @JoinColumn(name="APPOINTMENT_ID", unique= true)
    private Appointment appointment;

    @JsonIgnore
    @OneToMany(mappedBy = "invoice")
    private List<InvoiceItems> invoiceItems;

    @OneToMany(mappedBy = "invoice")
    private List<PatientInvoicePayment> patientInvoicePayments;

    @Column(name = "PAID_AMOUNT")
    private  Double paidAmount;

    @Column(name = "INVOICE_AMOUNT")
    private Double invoiceAmount;


    @Column(name = "STATUS")
    private String status;

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public List<InvoiceItems> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(List<InvoiceItems> invoiceItems) {
        this.invoiceItems = invoiceItems;
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

    public List<PatientInvoicePayment> getPatientInvoicePayments() {
        return patientInvoicePayments;
    }

    public void setPatientInvoicePayments(List<PatientInvoicePayment> patientInvoicePayments) {
        this.patientInvoicePayments = patientInvoicePayments;
    }
}
