package com.sd.his.wrapper.response;

import com.sd.his.model.PaymentType;

public class PaymentTypeWrapper {

    private long id;
    private String paymentTitle;
    private String paymentMode;


    public PaymentTypeWrapper() {}

    public PaymentTypeWrapper(PaymentType p) {
        this.id = p.getId();
        this.paymentTitle = p.getPaymentTitle();
        this.paymentMode = p.getPaymentMode();
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
}
