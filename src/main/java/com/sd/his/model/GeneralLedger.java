package com.sd.his.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "GENERAL_LEDGER")
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeneralLedger  extends BaseEntity implements Serializable {


    private static final long serialVersionUID = 1L;

    @Column(name = "PARENT_TYPE")
    private String parentType;

    @Column(name = "ACCOUNT_TYPE")
    private String accountType;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CODE")
    private String code;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "BALANCE_TYPE")
    private String balanceType;


    public GeneralLedger() {
    }

    public GeneralLedger(GeneralLedger generalLedger) {
        this.setId(generalLedger.getId());
        this.parentType = generalLedger.getParentType();
        this.accountType = generalLedger.getAccountType();
        this.name = generalLedger.getName();
        this.code = generalLedger.getCode();
        this.description = generalLedger.getDescription();
    }


    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(String balanceType) {
        this.balanceType = balanceType;
    }

}
