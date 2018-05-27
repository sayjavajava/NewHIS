package com.sd.his.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

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
 * @FileName  : ClinicalDepartmentMedicalService
 *
 * Copyright © 
 * SolutionDots, 
 * All rights reserved.
 * 
 */
@Entity
@Table(name = "CLINICAL_DEPARTMENT_MEDICAL_SERVICE")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClinicalDepartmentMedicalService {

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CLINICAL_DEPARTMENT_ID")
    private ClinicalDepartment clinicalDpt;

    @ManyToOne
    @JoinColumn(name = "MEDICAL_SERVICE_ID")
    private MedicalService medicalService;

    public ClinicalDepartmentMedicalService() {
    }

    public ClinicalDepartmentMedicalService(ClinicalDepartment dpt, MedicalService medicalService) {
        this.clinicalDpt = dpt;
        this.medicalService = medicalService;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClinicalDepartment getClinicalDpt() {
        return clinicalDpt;
    }

    public void setClinicalDpt(ClinicalDepartment clinicalDpt) {
        this.clinicalDpt = clinicalDpt;
    }

    public MedicalService getMedicalService() {
        return medicalService;
    }

    public void setMedicalService(MedicalService medicalService) {
        this.medicalService = medicalService;
    }
}
