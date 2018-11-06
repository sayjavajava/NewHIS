package com.sd.his.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "PAYMENT_TYPE")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentType extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;



   /* @Column(name = "gl_account")
    private  String paymentGlAccount;*/




    @ManyToOne
    @JoinColumn(name = "gl_account")
    private GeneralLedger paymentGlAccount;


    /*@OneToMany(mappedBy = "PAYMENT_TYPE_ID")
    private List<GeneralLedger> generalLedger;*/



    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gl_account", nullable = false)
    private GeneralLedger paymentGlAccount;*/

    @Column(name = "is_active", columnDefinition = "boolean default true", nullable = false)
    private Boolean active;

    @Column(name = "payment_title")
    private  String paymentTitle;

    @Column(name = "payment_mode")
    private  String paymentMode;

    @Column(name = "payment_purpose")
    private  String paymentPurpose;

    @Column(name="payment_servicecharges")
    private String serviceCharges;

    @Column(name="payment_maxCardCharges")
    private String maxCardCharges;

    @Column(name="payment_payCredit")
    private String payCredit;

    /*@Column(name="payment_bankGlCharges")
    private String bankGlCharges;*/



    @ManyToOne
    @JoinColumn(name = "payment_bankGlCharges")
    private GeneralLedger bankGlCharges;

    public String getPaymentMode() {
        return paymentMode;
    }


    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getServiceCharges() {
        return serviceCharges;
    }

    public void setServiceCharges(String serviceCharges) {
        this.serviceCharges = serviceCharges;
    }

    public String getMaxCardCharges() {
        return maxCardCharges;
    }

    public void setMaxCardCharges(String maxCardCharges) {
        this.maxCardCharges = maxCardCharges;
    }

    public String getPayCredit() {
        return payCredit;
    }

    public void setPayCredit(String payCredit) {
        this.payCredit = payCredit;
    }

 /*   public String getBankGlCharges() {
        return bankGlCharges;
    }

    public void setBankGlCharges(String bankGlCharges) {
        this.bankGlCharges = bankGlCharges;
    }
*/


    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getPaymentTitle() {
        return paymentTitle;
    }

    public void setPaymentTitle(String paymentTitle) {
        this.paymentTitle = paymentTitle;
    }

    public String getPaymentPurpose() {
        return paymentPurpose;
    }

    public void setPaymentPurpose(String paymentPurpose) {
        this.paymentPurpose = paymentPurpose;
    }

    public GeneralLedger getPaymentGlAccount() {
        return paymentGlAccount;
    }

    public void setPaymentGlAccount(GeneralLedger paymentGlAccount) {
        this.paymentGlAccount = paymentGlAccount;
    }
    public GeneralLedger getBankGlCharges() {
        return bankGlCharges;
    }

    public void setBankGlCharges(GeneralLedger bankGlCharges) {
        this.bankGlCharges = bankGlCharges;
    }

}
