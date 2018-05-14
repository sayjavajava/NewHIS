package com.sd.his.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

/*
 * @author    : Irfan Nasim
 * @Date      : 24-Apr-18
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
public class Branch {

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "NO_OF_ROOMS")
    private Integer noOfRooms;

    @Column(name = "BILLING_NAME")
    private String billingName;

    @Column(name = "BILLING_BRANCH_NAME")
    private String billingBranchName;

    @Column(name = "BILLING_TAX_ID")
    private String billingTaxId;

    @Column(name = "IS_ACTIVE", columnDefinition = "boolean default true", nullable = false)
    private boolean active;

    @Column(name = "IS_DELETED", columnDefinition = "boolean default false", nullable = false)
    private boolean deleted;

    @Column(name = "UPDATED_ON")
    private long updatedOn;

    @Column(name = "CREATED_ON")
    private long createdOn;

    @JsonIgnore
    @OneToMany(targetEntity = BranchUser.class, mappedBy = "branch", fetch = FetchType.LAZY)
    private List<BranchUser> users;

    @JsonIgnore
    @OneToMany(targetEntity = BranchClinicalDepartment.class, mappedBy = "branch", fetch = FetchType.LAZY)
    private List<BranchClinicalDepartment> clinicalDepartments;

    @JsonIgnore
    @OneToMany(targetEntity = BranchServices.class, mappedBy = "branch", fetch = FetchType.LAZY)
    private List<BranchServices> services;

    @JsonIgnore
    @OneToMany(targetEntity = BranchMedicalService.class, mappedBy = "branch", fetch = FetchType.LAZY)
    private List<BranchMedicalService> medicalServices;

    public Branch() {
    }

    @Override
    public String toString() {
        return "Branch{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", noOfRooms=" + noOfRooms +
                ", billingName='" + billingName + '\'' +
                ", billingBranchName='" + billingBranchName + '\'' +
                ", billingTaxId='" + billingTaxId + '\'' +
                ", active=" + active +
                ", deleted=" + deleted +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNoOfRooms() {
        return noOfRooms;
    }

    public void setNoOfRooms(Integer noOfRooms) {
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

    public String getBillingTaxId() {
        return billingTaxId;
    }

    public void setBillingTaxId(String billingTaxId) {
        this.billingTaxId = billingTaxId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public long getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(long updatedOn) {
        this.updatedOn = updatedOn;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public List<BranchUser> getUsers() {
        return users;
    }

    public void setUsers(List<BranchUser> users) {
        this.users = users;
    }

    public List<BranchClinicalDepartment> getClinicalDepartments() {
        return clinicalDepartments;
    }

    public void setClinicalDepartments(List<BranchClinicalDepartment> clinicalDepartments) {
        this.clinicalDepartments = clinicalDepartments;
    }

    public List<BranchServices> getServices() {
        return services;
    }

    public void setServices(List<BranchServices> services) {
        this.services = services;
    }

    public List<BranchMedicalService> getMedicalServices() {
        return medicalServices;
    }

    public void setMedicalServices(List<BranchMedicalService> medicalServices) {
        this.medicalServices = medicalServices;
    }
}
