package com.sd.his.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sd.his.utill.DateTimeUtil;
import com.sd.his.utill.HISConstants;
import com.sd.his.wrapper.ProblemWrapper;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/*
 * @author    : Tahir Mehmood
 * @Date      : 31-Jul-2018
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
 * @FileName  : User
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Entity
@Table(name = "PROBLEM")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Problem extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ICD_VERSION_ID")
    private ICDVersion icdVersion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ICD_CODE_ID")
    private ICDCode icdCode;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_DIAGNOSIS")
    private Date dateDiagnosis;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "STATUS")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPOINTMENT_ID")
    private Appointment appointment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PATIENT_ID")
    private Patient patient;

    public Problem() {
    }

    public Problem(ProblemWrapper problemWrapper) throws ParseException {
        if (problemWrapper.getDateDiagnosis() != null) {
            this.dateDiagnosis = DateTimeUtil.getDateFromString(problemWrapper.getDateDiagnosis(), HISConstants.DATE_FORMATE_THREE);
        }
        if (problemWrapper.getStatus() != null) {
            this.status = problemWrapper.getStatus();
        }
        if (problemWrapper.getNote() != null) {
            this.note = problemWrapper.getNote();
        }
    }

    public Problem(Problem problem, ProblemWrapper problemWrapper) throws ParseException {

        if (problemWrapper.getDateDiagnosis() != null) {
            problem.setDateDiagnosis(DateTimeUtil.getDateFromString(problemWrapper.getDateDiagnosis(), HISConstants.DATE_FORMATE_THREE));
        }
        if (problemWrapper.getStatus() != null) {
            problem.setStatus(problemWrapper.getStatus());
        }
        if (problemWrapper.getNote() != null) {
            this.setNote(problemWrapper.getNote());
        }
    }

    public ICDVersion getIcdVersion() {
        return icdVersion;
    }

    public void setIcdVersion(ICDVersion icdVersion) {
        this.icdVersion = icdVersion;
    }

    public ICDCode getIcdCode() {
        return icdCode;
    }

    public void setIcdCode(ICDCode icdCode) {
        this.icdCode = icdCode;
    }

    public Date getDateDiagnosis() {
        return dateDiagnosis;
    }

    public void setDateDiagnosis(Date dateDiagnosis) {
        this.dateDiagnosis = dateDiagnosis;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
