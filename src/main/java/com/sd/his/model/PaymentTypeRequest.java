
package com.sd.his.model;

import java.util.List;

public class PaymentTypeRequest {

    private Integer page;



    private Integer size;
    private String paymentTitle;
    private String paymentGlAccounts;
    private String maxCardCharges;
    private String serviceCharges;
    private String active;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }



    public String getPaymentTitle() {
        return paymentTitle;
    }

    public void setPaymentTitle(String paymentTitle) {
        this.paymentTitle = paymentTitle;
    }

    public String getPaymentGlAccountS() {
        return paymentGlAccounts;
    }

    public void setPaymentGlAccountS(String paymentGlAccounts) {
        this.paymentGlAccounts = paymentGlAccounts;
    }

    public String getMaxCardCharges() {
        return maxCardCharges;
    }

    public void setMaxCardCharges(String maxCardCharges) {
        this.maxCardCharges = maxCardCharges;
    }

    public String getServiceCharges() {
        return serviceCharges;
    }

    public void setServiceCharges(String serviceCharges) {
        this.serviceCharges = serviceCharges;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}

