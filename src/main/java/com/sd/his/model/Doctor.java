package com.sd.his.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/*
 * @author    : Tahir Mehmood
 * @Date      : 18-Jul-2018
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
@Table(name = "DOCTOR")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Doctor extends StaffProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "CHECK_UP_INTERVAL")
    private Long checkUpInterval;

    @ElementCollection
    @Column(name = "WORKING_DAYS")
    private List<String> workingDays;

    @JsonIgnore
    @OneToMany(targetEntity = NurseWithDoctor.class, mappedBy = "doctor")
    private List<NurseWithDoctor> nurseWithDoctorList;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="USER_ID", unique= true)
    private User user;

    @JsonIgnore
    @OneToMany(targetEntity = DutyShift.class, mappedBy = "doctor" ,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DutyShift> dutyShifts;

    @JsonIgnore
    @OneToMany(targetEntity = BranchDoctor.class, mappedBy = "doctor")
    private List<BranchDoctor> branchDoctors;

    @Column(name = "VACATION", columnDefinition = "boolean default false")
    private Boolean vacation;

    @Temporal(TemporalType.DATE)
    @Column(name = "VACATION_FROM")
    private Date vacationFrom;

    @Temporal(TemporalType.DATE)
    @Column(name = "VACATION_TO")
    private Date vacationTO;

    public Boolean getVacation() {
        return vacation;
    }

    public void setVacation(Boolean vacation) {
        this.vacation = vacation;
    }

    public Long getCheckUpInterval() {
        return checkUpInterval;
    }

    public void setCheckUpInterval(Long checkUpInterval) {
        this.checkUpInterval = checkUpInterval;
    }

    public List<String> getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(List<String> workingDays) {
        this.workingDays = workingDays;
    }

    public List<NurseWithDoctor> getNurseWithDoctorList() {
        return nurseWithDoctorList;
    }

    public void setNurseWithDoctorList(List<NurseWithDoctor> nurseWithDoctorList) {
        this.nurseWithDoctorList = nurseWithDoctorList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<DutyShift> getDutyShifts() {
        return dutyShifts;
    }

    public void setDutyShifts(List<DutyShift> dutyShifts) {
        this.dutyShifts = dutyShifts;
    }

    public List<BranchDoctor> getBranchDoctors() {
        return branchDoctors;
    }

    public Date getVacationFrom() {
        return vacationFrom;
    }

    public void setVacationFrom(Date vacationFrom) {
        this.vacationFrom = vacationFrom;
    }

    public Date getVacationTO() {
        return vacationTO;
    }

    public void setVacationTO(Date vacationTO) {
        this.vacationTO = vacationTO;
    }

    public void setBranchDoctors(List<BranchDoctor> branchDoctors) {
        this.branchDoctors = branchDoctors;
    }

}
