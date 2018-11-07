package com.sd.his.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sd.his.wrapper.ICDCodeCreateRequest;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/*
 * @author    : Irfan Nasim
 * @Date      : 26-Apr-18
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
 * @FileName  : ICDCode
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Entity
@Table(name = "ICD_CODE")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ICDCode extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "CODE")
    private String code;

    @Column(name = "NAME")
    private String name;

    @Column(name = "STATUS", columnDefinition = "boolean default true", nullable = false)
    private Boolean status;

    @Column(name = "DESCRIPTION")
    private String description;


    @JsonIgnore
    @OneToMany(targetEntity = ICDCodeVersion.class, mappedBy = "icd")
    private List<ICDCodeVersion> icdCodes;//// codeVersions

    @JsonIgnore
    @OneToMany(targetEntity = Problem.class, mappedBy = "icdCode")
    private List<Problem> problems;


    public ICDCode() {
    }

    public ICDCode(ICDCodeCreateRequest createRequest) {
        this.code = createRequest.getCode();
        this.status = createRequest.isStatus();
        this.problem = createRequest.getProblem();
        this.description = createRequest.getDescription();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ICDCodeVersion> getIcdCodes() {
        return icdCodes;
    }

    public void setIcdCodes(List<ICDCodeVersion> icdCodes) {
        this.icdCodes = icdCodes;
    }

    public List<Problem> getProblems() {
        return problems;
    }

    public void setProblems(List<Problem> problems) {
        this.problems = problems;
    }
}
