package com.sd.his.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
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
 * @FileName  : ClinicalDepartment
 *
 * Copyright © 
 * SolutionDots, 
 * All rights reserved.
 * 
 */
@Entity
@Table(name = "DEPARTMENT")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Department extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "IS_ACTIVE", columnDefinition = "boolean default false", nullable = false)
    private Boolean active;

    @JsonIgnore
    @OneToMany(targetEntity = BranchDepartment.class, mappedBy = "department")
    private List<BranchDepartment> branchDepartments;

    @JsonIgnore
    @OneToMany(targetEntity = DepartmentMedicalService.class, mappedBy = "department")
    private List<DepartmentMedicalService> departments;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<BranchDepartment> getBranchDepartments() {
        return branchDepartments;
    }

    public void setBranchDepartments(List<BranchDepartment> branchDepartments) {
        this.branchDepartments = branchDepartments;
    }
}
