package com.sd.his.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sd.his.wrapper.MedicalServiceWrapper;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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
 * @Package   : com.sd.his.model
 * @FileName  : MedicalService
 *
 * Copyright © 
 * SolutionDots, 
 * All rights reserved.
 * 
 */
@Entity
@Table(name = "MEDICAL_SERVICE")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MedicalService extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "NAME")
    private String name;

    @Column(name = "COST")
    private Double cost;

    @Column(name = "FEE")
    private Double fee;

    @Column(name = "DURATION")
    private Long duration;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "IMG_URL")
    private String imgURL;

    @Column(name = "STATUS")
    private Boolean status;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TAX_ID")
    private Tax tax;

    @JsonIgnore
    @OneToMany(targetEntity = DepartmentMedicalService.class, mappedBy = "medicalService",orphanRemoval = true)
    private List<DepartmentMedicalService> departmentMedicalServices;

    @JsonIgnore
    @OneToMany(targetEntity = BranchMedicalService.class, mappedBy = "medicalService",orphanRemoval = true)
    private List<BranchMedicalService> branchMedicalServices;

    @JsonIgnore
    @OneToMany(targetEntity = DoctorMedicalService.class, mappedBy = "medicalService",orphanRemoval = true)
    private List<DoctorMedicalService> doctorMedicalServices;

    public MedicalService() {
    }

    public MedicalService(MedicalServiceWrapper createRequest) {
        this.name = createRequest.getName();
        this.cost = createRequest.getCost();
        this.fee = createRequest.getFee();
        this.duration = createRequest.getDuration();
        this.description = createRequest.getDescription();
//        this.getImgURL() = createRequest.get
        this.status = createRequest.isStatus();
    }

    public MedicalService(MedicalService medicalService, MedicalServiceWrapper createRequest) {
        medicalService.setName(createRequest.getName());
        medicalService.setCost(createRequest.getCost());
        medicalService.setFee(createRequest.getFee());
        medicalService.setDuration(createRequest.getDuration());
        medicalService.setDescription(createRequest.getDescription());
//      medicalService.getImgURL() = createRequest.get
        medicalService.setStatus(createRequest.isStatus());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<DepartmentMedicalService> getDepartmentMedicalServices() {
        return departmentMedicalServices;
    }

    public void setDepartmentMedicalServices(List<DepartmentMedicalService> departmentMedicalServices) {
        this.departmentMedicalServices = departmentMedicalServices;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public List<BranchMedicalService> getBranchMedicalServices() {
        return branchMedicalServices;
    }

    public void setBranchMedicalServices(List<BranchMedicalService> branchMedicalServices) {
        this.branchMedicalServices = branchMedicalServices;
    }


}
