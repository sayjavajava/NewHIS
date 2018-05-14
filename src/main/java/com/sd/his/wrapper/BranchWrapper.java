package com.sd.his.wrapper;

import com.sd.his.model.Branch;

/*
 * @author    : Irfan Nasim
 * @Date      : 14-May-18
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
 * @Package   : com.sd.his.wrapper
 * @FileName  : $BranchWrapper
 *
 * Copyright © 
 * SolutionDots, 
 * All rights reserved.
 * 
 */
public class BranchWrapper {

    private Long id;
    private String name;
    private Integer noOfRooms;
    private String billingName;
    private String billingBranchName;
    private String billingTaxId;
    private boolean active;
    private boolean deleted;
    private long updatedOn;
    private long createdOn;

    public BranchWrapper() {
    }

    public BranchWrapper(Branch branch) {
        this.id = branch.getId();
        this.name = branch.getName();
        this.noOfRooms = branch.getNoOfRooms();
        this.billingName = branch.getBillingName();
        this.billingBranchName = branch.getBillingBranchName();
        this.billingTaxId = branch.getBillingTaxId();
        this.active = branch.isActive();
        this.deleted = branch.isDeleted();
        this.updatedOn = branch.getUpdatedOn();
        this.createdOn = branch.getCreatedOn();
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
}
