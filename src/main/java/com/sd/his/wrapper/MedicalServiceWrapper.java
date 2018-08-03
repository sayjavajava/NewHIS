package com.sd.his.wrapper;

import com.sd.his.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/*
 * @author    : Jamal
 * @Date      : 31-July-18
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
 * @FileName  : MedicalServiceWrapper
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
public class MedicalServiceWrapper {
    private long id;
    private String name;
    private double fee;
    private double cost;
    private boolean status;
    private String description;
    private long duration;

    @Autowired
    private DepartmentWrapper departmentWrapper;
    @Autowired
    private TaxWrapper tax;
    @Autowired
    private BranchWrapper branchWrapper;
    private List<BranchWrapper> branches;//checkedBranch
    private List<BranchWrapper> checkedBranches;
    private List<DepartmentWrapper> departments;
    private List<DepartmentWrapper> checkedDepartments;
    private List<TaxWrapper> taxes;

    public MedicalServiceWrapper() {
    }

    public MedicalServiceWrapper(MedicalServiceWrapper medicalServiceWrapper) {
        this.id = medicalServiceWrapper.getId();
        this.name = medicalServiceWrapper.getName();
        this.fee = medicalServiceWrapper.getFee();
        this.cost = medicalServiceWrapper.getCost();
        this.status = medicalServiceWrapper.isStatus();
        this.description = medicalServiceWrapper.getDescription();
        this.duration = medicalServiceWrapper.getDuration();
    }

    public MedicalServiceWrapper(MedicalService ms) {
        if (ms.getTax() == null) {
            this.tax = new TaxWrapper();
        } else {
            this.tax = new TaxWrapper(ms.getTax());
        }
        this.id = ms.getId();
        this.name = ms.getName();
        this.fee = ms.getFee();
        this.cost = ms.getCost();
        this.status = ms.getStatus();
        this.description = ms.getDescription();
        this.duration = ms.getDuration();
    }

    public MedicalServiceWrapper(MedicalService ms, List<DepartmentWrapper> allDepartmentWrappers, List<BranchWrapper> allBranchWrappers) {

        /*if (ms.getDepartmentMedicalServices() != null) {
            for (DepartmentMedicalService dMS : ms.getDepartmentMedicalServices()) {
                if (dMS.getDepartment() != null){
                    for (DepartmentWrapper departmentWrapper:allDepartmentWrappers){
                        if(dMS.getDepartment().getId() == departmentWrapper.getId()){
                            departmentWrapper.setCheckedDepartment(true);
                            break;
                        }
                    }
                }
            }
        }
        this.getDepartments = new ArrayList<>();
        this.setDepartments(allDepartmentWrappers);
        if (ms.getBranchMedicalServices() != null) {
            for (BranchMedicalService branchMedicalService : ms.getBranchMedicalServices()) {
                if (branchMedicalService.getBranch() != null){
                    for (BranchWrapper br:allBranchWrappers){
                        if(branchMedicalService.getBranch().getId() == br.getId()){
                            br.setCheckedBranch(true);
                            break;
                        }
                    }
                }
            }
        }

        this.branches = new ArrayList<>();
        this.setBranches(allBranchWrappers);*/
        if (ms.getTax() == null) {
            this.tax = new TaxWrapper();
        } else {
            this.tax = new TaxWrapper(ms.getTax());
        }
        this.id = ms.getId();
        this.name = ms.getName();
        this.fee = ms.getFee();
        this.cost = ms.getCost();
        this.status = ms.getStatus();
        this.description = ms.getDescription();
        this.duration = ms.getDuration();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public DepartmentWrapper getDepartmentWrapper() {
        return departmentWrapper;
    }

    public void setDepartmentWrapper(DepartmentWrapper departmentWrapper) {
        this.departmentWrapper = departmentWrapper;
    }

    public TaxWrapper getTax() {
        return tax;
    }

    public void setTax(TaxWrapper tax) {
        this.tax = tax;
    }

    public BranchWrapper getBranchWrapper() {
        return branchWrapper;
    }

    public void setBranchWrapper(BranchWrapper branchWrapper) {
        this.branchWrapper = branchWrapper;
    }

    public List<BranchWrapper> getBranches() {
        return branches;
    }

    public void setBranches(List<BranchWrapper> branches) {
        this.branches = branches;
    }

    public List<DepartmentWrapper> getDepartments() {
        return departments;
    }

    public void setDepartments(List<DepartmentWrapper> departments) {
        this.departments = departments;
    }

    public List<TaxWrapper> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<TaxWrapper> taxes) {
        this.taxes = taxes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public List<BranchWrapper> getCheckedBranches() {
        return checkedBranches;
    }

    public void setCheckedBranches(List<BranchWrapper> checkedBranches) {
        this.checkedBranches = checkedBranches;
    }

    public List<DepartmentWrapper> getCheckedDepartments() {
        return checkedDepartments;
    }

    public void setCheckedDepartments(List<DepartmentWrapper> checkedDepartments) {
        this.checkedDepartments = checkedDepartments;
    }
}
