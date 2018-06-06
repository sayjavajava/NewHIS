package com.sd.his.wrapper;

import com.sd.his.model.Insurance;

import java.util.Date;

public class InsuranceWrapper {

    private long id;
    private String company;
    private String insuranceID;
    private Long groupNumber;
    private String planName;
    private String planType;
    private Date cardIssuedDate;
    private Date cardExpiryDate;
    private String primaryInsuranceNotes;
    private String photoFront;
    private String photoBack;


    public InsuranceWrapper() {
    }

    public InsuranceWrapper(Insurance insurance) {
        this.id = insurance.getId();
        this.company = insurance.getCompany();
        this.insuranceID = insurance.getInsuranceID();
        this.groupNumber = insurance.getGroupNumber();
        this.planName = insurance.getPlanName();
        this.planType = insurance.getPlanType();
        this.cardIssuedDate = insurance.getCardIssuedDate();
        this.cardExpiryDate = insurance.getCardExpiryDate();
        this.primaryInsuranceNotes = insurance.getPrimaryInsuranceNotes();
        this.photoFront = insurance.getPhotoFront();
        this.photoBack = insurance.getPhotoBack();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getInsuranceID() {
        return insuranceID;
    }

    public void setInsuranceID(String insuranceID) {
        this.insuranceID = insuranceID;
    }

    public Long getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(Long groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public Date getCardIssuedDate() {
        return cardIssuedDate;
    }

    public void setCardIssuedDate(Date cardIssuedDate) {
        this.cardIssuedDate = cardIssuedDate;
    }

    public Date getCardExpiryDate() {
        return cardExpiryDate;
    }

    public void setCardExpiryDate(Date cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate;
    }

    public String getPrimaryInsuranceNotes() {
        return primaryInsuranceNotes;
    }

    public void setPrimaryInsuranceNotes(String primaryInsuranceNotes) {
        this.primaryInsuranceNotes = primaryInsuranceNotes;
    }

    public String getPhotoFront() {
        return photoFront;
    }

    public void setPhotoFront(String photoFront) {
        this.photoFront = photoFront;
    }

    public String getPhotoBack() {
        return photoBack;
    }

    public void setPhotoBack(String photoBack) {
        this.photoBack = photoBack;
    }
}
