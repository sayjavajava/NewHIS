package com.sd.his.wrapper.response;

import com.sd.his.model.PaymentType;

public class PaymentTypeWrapper {

    private long id;
    private String paymentTitle;
    private String paymentMode;
    private double serviceCharges;
    private double maxCardCharges;
    private boolean active;
    private String paymentGlAccountName;
    private long paymentGlAccountId;



    public PaymentTypeWrapper() {}

    public PaymentTypeWrapper(PaymentType p) {
        this.id = p.getId();
        this.paymentTitle = p.getPaymentTitle();
        this.paymentMode = p.getPaymentMode();
        this.serviceCharges = p.getServiceCharges() == null ? 0D : p.getServiceCharges();
        this.maxCardCharges = p.getMaxCardCharges() == null ? 0D : p.getMaxCardCharges();
        this.active = p.getActive();
        if (p.getPaymentGlAccount() != null) {
            this.paymentGlAccountId = p.getPaymentGlAccount().getId();
            this.paymentGlAccountName = p.getPaymentGlAccount().getName();
        }
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPaymentTitle() {
        return paymentTitle;
    }

    public void setPaymentTitle(String paymentTitle) {
        this.paymentTitle = paymentTitle;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public double getServiceCharges() {
        return serviceCharges;
    }

    public void setServiceCharges(double serviceCharges) {
        this.serviceCharges = serviceCharges;
    }

    public double getMaxCardCharges() {
        return maxCardCharges;
    }

    public void setMaxCardCharges(double maxCardCharges) {
        this.maxCardCharges = maxCardCharges;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPaymentGlAccountName() {
        return paymentGlAccountName;
    }

    public void setPaymentGlAccountName(String paymentGlAccountName) {
        this.paymentGlAccountName = paymentGlAccountName;
    }

    public long getPaymentGlAccountId() {
        return paymentGlAccountId;
    }

    public void setPaymentGlAccountId(long paymentGlAccountId) {
        this.paymentGlAccountId = paymentGlAccountId;
    }
}
