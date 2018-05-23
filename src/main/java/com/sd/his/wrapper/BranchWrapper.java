package com.sd.his.wrapper;/*
 * @author    : waqas kamran
 * @Date      : 17-Apr-18
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
 * @Package   : com.sd.his.*
 * @FileName  : UserAuthAPI
 *
 * Copyright © 
 * SolutionDots, 
 * All rights reserved.
 * 
 */

import com.sd.his.model.Branch;
import com.sd.his.model.BranchUser;

import java.util.List;

public class BranchWrapper {

    String name;
    String billingBranchName;
    String billingTaxId;
    int noOfRooms;

    boolean primaryBranch;
    boolean primaryDr;
    boolean billingBranch;

    public BranchWrapper(BranchUser branchUser) {
        this.name = branchUser.getBranch().getName();
        this.billingBranchName = branchUser.getBranch().getBillingBranchName();
        this.billingTaxId = branchUser.getBranch().getBillingTaxId();
        this.noOfRooms = branchUser.getBranch().getNoOfRooms();
        this.primaryBranch = branchUser.isPrimaryBranch();
        this.primaryDr = branchUser.isPrimaryDr();
        this.billingBranch = branchUser.isBillingBranch();

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getNoOfRooms() {
        return noOfRooms;
    }

    public void setNoOfRooms(int noOfRooms) {
        this.noOfRooms = noOfRooms;
    }

    public boolean isPrimaryBranch() {
        return primaryBranch;
    }

    public void setPrimaryBranch(boolean primaryBranch) {
        this.primaryBranch = primaryBranch;
    }

    public boolean isPrimaryDr() {
        return primaryDr;
    }

    public void setPrimaryDr(boolean primaryDr) {
        this.primaryDr = primaryDr;
    }

    public boolean isBillingBranch() {
        return billingBranch;
    }

    public void setBillingBranch(boolean billingBranch) {
        this.billingBranch = billingBranch;
    }
}