package com.sd.his.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class Insurance implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "COMPANY")
    private String company;

    @Column(name = "INSURANCE_ID")
    private String insuranceID;

    @Column(name = "GROUP_NUMBER")
    private Long groupNumber;

    @Column(name = "PLAN_NAME")
    private String planName;

    @Column(name = "PLAN_TYPE")
    private String planType;

    @Column(name = "CART_ISSUED_DATE")
    private Date cardIssuedDate;

    @Column(name = "CART_EXPIRY_DATE")
    private Date cardExpiryDate;

    @Column(name = "PRIMARY_INSURANCE_NOTES")
    private String primaryInsuranceNotes;

    @Column(name = "PHOTO_FRONT")
    private String photoFront;

    @Column(name = "PHOTO_BACK")
    private String photoBack;

    @Column(name = "CREATED_ON")
    private long created;

    @Column(name = "UPDATED_ON")
    private long updated;
    
    @Column(name = "IS_DELETED", columnDefinition = "boolean default false")
    private boolean deleted;

    public Insurance() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
