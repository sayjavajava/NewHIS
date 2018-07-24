package com.sd.his.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
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







}
