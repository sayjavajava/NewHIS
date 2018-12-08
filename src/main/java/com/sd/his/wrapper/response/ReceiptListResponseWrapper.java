package com.sd.his.wrapper.response;

import com.sd.his.model.PatientInvoicePayment;
import com.sd.his.model.Payment;

import java.util.List;

public class ReceiptListResponseWrapper {

    long id;
    String paymentId;
    String patientName;
    Double discountAmount;
    Double advanceUsedAmount;
    Double paymontAmount;
    String paymentType;


    public ReceiptListResponseWrapper() {
    }


    public ReceiptListResponseWrapper(Payment pmt) {
        this.paymentId=pmt.getPaymentId();
        this.paymontAmount=pmt.getPaymentAmount();
        if (pmt.getPatientInvoicePayment().size() > 0) {
            this.patientName = pmt.getPatientInvoicePayment().get(0).getPatient().getFirstName();
            this.discountAmount=getDiscountOnPayment(pmt.getPatientInvoicePayment());
            this.advanceUsedAmount=getAdvancedUsed(pmt.getPatientInvoicePayment());
        }
/*      this.paymentType=*/
    }


    private double getDiscountOnPayment(List<PatientInvoicePayment> patientInvoicePayment) {
        double discountTotal = 0.00;
        for (PatientInvoicePayment pip : patientInvoicePayment) {
            discountTotal += (pip.getDiscountAmount() == null ? 0 : pip.getDiscountAmount());
        }
        return discountTotal;
    }

    private double getAdvancedUsed(List<PatientInvoicePayment> patientInvoicePayment) {
        double advUsedTotal = 0.00;
        for (PatientInvoicePayment pip : patientInvoicePayment) {
            advUsedTotal += (pip.getAdvanceAmount() == null ? 0 : pip.getAdvanceAmount());
        }
        return advUsedTotal;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getAdvanceUsedAmount() {
        return advanceUsedAmount;
    }

    public void setAdvanceUsedAmount(Double advanceUsedAmount) {
        this.advanceUsedAmount = advanceUsedAmount;
    }

    public Double getPaymontAmount() {
        return paymontAmount;
    }

    public void setPaymontAmount(Double paymontAmount) {
        this.paymontAmount = paymontAmount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
}
