package com.sd.his.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "PAYMENT")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Payment  extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @NaturalId
    @Column(name = "PAYMENT_ID")
    private String paymentId;


    @Column(name = "PAYMENT_AMOUNT")
    private  Double paymentAmount;


    @OneToMany(mappedBy = "payment")
    private List<PatientInvoicePayment> patientInvoicePayment;


    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public List<PatientInvoicePayment> getPatientInvoicePayment() {
        return patientInvoicePayment;
    }

    public void setPatientInvoicePayment(List<PatientInvoicePayment> patientInvoicePayment) {
        this.patientInvoicePayment = patientInvoicePayment;
    }
}
