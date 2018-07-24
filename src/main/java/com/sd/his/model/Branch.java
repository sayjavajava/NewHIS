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
 * @FileName  : Branch
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Entity
@Table(name = "BRANCH")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Branch extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "NAME")
    private String name;

    @Column(name = "NO_OF_ROOMS")
    private Long noOfRooms;

    @Column(name = "BILLING_NAME")
    private String billingName;

    @Column(name = "BILLING_BRANCH_NAME")
    private String billingBranchName;

    @Column(name = "CITY")
    private String city;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "FAX")
    private String fax;

    @Column(name = "OFFICE_PHONE")
    private String officePhone;

    @Column(name = "STATE")
    private String state;

    @Column(name = "OFFICE_START_TIME")
    private String officeStartTime;

    @Column(name = "OFFICE_END_TIME")
    private String officeEndTime;

    @Column(name = "BILLING_TAX_ID")
    private String billingTaxId;

    @Column(name = "IS_ACTIVE", columnDefinition = "boolean default true", nullable = false)
    private Boolean active;

    @Column(name = "SYSTEM_BRANCH", columnDefinition = "boolean default false", nullable = false)
    private Boolean systemBranch;

    @Column(name = "ZIP_CODE")
    private Integer zipCode;

    @Column(name = "ALLOW_ONLINE_SCHEDULE", columnDefinition = "boolean default true")
    private Boolean allowOnlineSchedule;

    @Column(name = "SHOW_BRANCH_INFO_ONLINE", columnDefinition = "boolean default true")
    private Boolean showBranchInfoOnline;

    @JsonIgnore
    @OneToMany(targetEntity = BranchDoctor.class, mappedBy = "branch")
    private List<BranchDoctor> branchDoctors;

    @JsonIgnore
    @OneToMany(targetEntity = BranchNurse.class, mappedBy = "branch")
    private List<BranchNurse> branchNurses;

    @JsonIgnore
    @OneToMany(targetEntity = BranchCashier.class, mappedBy = "branch")
    private List<BranchCashier> branchCashiers;

    @JsonIgnore
    @OneToMany(targetEntity = BranchReceptionist.class, mappedBy = "branch")
    private List<BranchReceptionist> branchReceptionists;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = Room.class, mappedBy = "branch")
    private List<Room> rooms;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BRANCH_ID")
    private Organization organization;

    @JsonIgnore
    @OneToMany(targetEntity = BranchDepartment.class, mappedBy = "branch")
    private List<BranchDepartment> branchDepartments;



//    @JsonIgnore
//    @OneToMany(targetEntity = Appointment.class, mappedBy = "branch", fetch = FetchType.LAZY)
//    private List<Appointment> appointments;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNoOfRooms() {
        return noOfRooms;
    }

    public void setNoOfRooms(Long noOfRooms) {
        this.noOfRooms = noOfRooms;
    }

    public String getBillingName() {
        return billingName;
    }

    public void setBillingName(String billingName) {
        this.billingName = billingName;
    }

    public String getBillingBranchName() {
        return billingBranchName;
    }

    public void setBillingBranchName(String billingBranchName) {
        this.billingBranchName = billingBranchName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOfficeStartTime() {
        return officeStartTime;
    }

    public void setOfficeStartTime(String officeStartTime) {
        this.officeStartTime = officeStartTime;
    }

    public String getOfficeEndTime() {
        return officeEndTime;
    }

    public void setOfficeEndTime(String officeEndTime) {
        this.officeEndTime = officeEndTime;
    }

    public String getBillingTaxId() {
        return billingTaxId;
    }

    public void setBillingTaxId(String billingTaxId) {
        this.billingTaxId = billingTaxId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getSystemBranch() {
        return systemBranch;
    }

    public void setSystemBranch(Boolean systemBranch) {
        this.systemBranch = systemBranch;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public Boolean getAllowOnlineSchedule() {
        return allowOnlineSchedule;
    }

    public void setAllowOnlineSchedule(Boolean allowOnlineSchedule) {
        this.allowOnlineSchedule = allowOnlineSchedule;
    }

    public Boolean getShowBranchInfoOnline() {
        return showBranchInfoOnline;
    }

    public void setShowBranchInfoOnline(Boolean showBranchInfoOnline) {
        this.showBranchInfoOnline = showBranchInfoOnline;
    }

    public List<BranchDoctor> getBranchDoctors() {
        return branchDoctors;
    }

    public void setBranchDoctors(List<BranchDoctor> branchDoctors) {
        this.branchDoctors = branchDoctors;
    }

    public List<BranchNurse> getBranchNurses() {
        return branchNurses;
    }

    public void setBranchNurses(List<BranchNurse> branchNurses) {
        this.branchNurses = branchNurses;
    }

    public List<BranchCashier> getBranchCashiers() {
        return branchCashiers;
    }

    public void setBranchCashiers(List<BranchCashier> branchCashiers) {
        this.branchCashiers = branchCashiers;
    }

    public List<BranchReceptionist> getBranchReceptionists() {
        return branchReceptionists;
    }

    public void setBranchReceptionists(List<BranchReceptionist> branchReceptionists) {
        this.branchReceptionists = branchReceptionists;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public List<BranchDepartment> getBranchDepartments() {
        return branchDepartments;
    }

    public void setBranchDepartments(List<BranchDepartment> branchDepartments) {
        this.branchDepartments = branchDepartments;
    }
}
