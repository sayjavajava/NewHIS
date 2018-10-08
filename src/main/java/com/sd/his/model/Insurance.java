package com.sd.his.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*
 * @author    : Irfan Nasim
 * @Date      : 04-Jun-18
 * @version   : ver. 1.0.0
 *
 * ________________________________________________________________________________________________
 *
 *  Developer				Date		     Version		Operation		Description
 * ________________________________________________________________________________________________
 *
 *
 * ________________________________________________________________________________________________
 *
 * @Project   : HIS
 * @Package   : com.sd.his.model
 * @FileName  : Insurance
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Entity
@Table(name = "INSURANCE")
public class Insurance extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "COMPANY")
    private String company;

    @Column(name = "INSURANCE_ID")
    private String insuranceIDNumber;//this is not primary key

    @Column(name = "GROUP_NUMBER")
    private String groupNumber;

    @Column(name = "PLAN_NAME")
    private String planName;

    @Column(name = "PLAN_TYPE")
    private String planType;

    @Temporal(TemporalType.DATE)
    @Column(name = "CARD_ISSUED_DATE")
    private Date cardIssuedDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "CARD_EXPIRY_DATE")
    private Date cardExpiryDate;

    @Column(name = "PRIMARY_INSURANCE_NOTES")
    private String primaryInsuranceNotes;

    @Column(name = "PHOTO_FRONT_URL")
    private String photoFrontURL;

    @Column(name = "PHOTO_BACK_URL")
    private String photoBackURL;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getInsuranceIDNumber() {
        return insuranceIDNumber;
    }

    public void setInsuranceIDNumber(String insuranceIDNumber) {
        this.insuranceIDNumber = insuranceIDNumber;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
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

    public String getPhotoFrontURL() {
        return photoFrontURL;
    }

    public void setPhotoFrontURL(String photoFrontURL) {
        this.photoFrontURL = photoFrontURL;
    }

    public String getPhotoBackURL() {
        return photoBackURL;
    }

    public void setPhotoBackURL(String photoBackURL) {
        this.photoBackURL = photoBackURL;
    }
}
