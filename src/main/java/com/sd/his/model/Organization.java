package com.sd.his.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/*
 * @author    : Tahir Mehmood
 * @Date      : 16-Jul-18
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
 * @FileName  : Organization
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Entity
@Table(name = "ORGANIZATION")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Organization extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "COMPANY_NAME")
    private String companyName;

    @Column(name = "DURATION_OF_EXAM")
    private Long durationOFExam;

    @Column(name = "TIMEZONE")
    private String timezone;

    @Column(name = "DURATION_FOLLOW_UP")
    private Long durationFollowUp;

    @Column(name = "OFFICE_PHONE")
    private String officePhone;

    @Column(name = "HOME_PHONE")
    private String homePhone;

    @Column(name = "CELL_PHONE")
    private String cellPhone;

    @Column(name = "WEBSITE")
    private String website;

    @Column(name = "SPECIALTY")
    private String specialty;

    @Column(name = "EMAIL")
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "organization", cascade = CascadeType.PERSIST)
    private List<S3Bucket> bucketList;

    @JsonIgnore
    @OneToMany(mappedBy = "organization", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Prefix> prefixList;

    @JsonIgnore
    @OneToMany(mappedBy = "organization", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Branch> branches;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getDurationOFExam() {
        return durationOFExam;
    }

    public void setDurationOFExam(Long durationOFExam) {
        this.durationOFExam = durationOFExam;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Long getDurationFollowUp() {
        return durationFollowUp;
    }

    public void setDurationFollowUp(Long durationFollowUp) {
        this.durationFollowUp = durationFollowUp;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public List<S3Bucket> getBucketList() {
        return bucketList;
    }

    public void setBucketList(List<S3Bucket> bucketList) {
        this.bucketList = bucketList;
    }

    public List<Prefix> getPrefixList() {
        return prefixList;
    }

    public void setPrefixList(List<Prefix> prefixList) {
        this.prefixList = prefixList;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Organization() {
    }

    public Organization(String companyName, Long durationOFExam, String timezone, Long durationFollowUp, String officePhone, String homePhone, String cellPhone, String website, String specialty, String email) {
        this.companyName = companyName;
        this.durationOFExam = durationOFExam;
        this.timezone = timezone;
        this.durationFollowUp = durationFollowUp;
        this.officePhone = officePhone;
        this.homePhone = homePhone;
        this.cellPhone = cellPhone;
        this.website = website;
        this.specialty = specialty;
        this.email = email;
    }
}
