package com.sd.his.model;

import com.sd.his.enums.AppointmentStatusTypeEnum;
import com.sd.his.enums.AppointmentTypeEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*
 * @author    : Irfan Nasim
 * @Date      : 08-Jun-18
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
 * @FileName  : Branch
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Entity
@Table(name = "APPOINTMENT")
public class Appointment extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "NAME")
    private String name;

    @Column(name = "NOTES")
    private String notes;

    @Column(name = "REASON")
    private String reason;

    @Column(name = "COLOR")
    private String color;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppointmentStatusTypeEnum status;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private AppointmentTypeEnum type;

    @Column(name = "DURATION") //minutes
    private Long duration;

    @Column(name = "AGE")
    private Long age;

    @Column(name = "FOLLOW_UP_REMINDER")
    private Boolean followUpReminder;

    @Column(name = "FOLLOW_UP_REMINDER_REASON")
    private String followUpReasonReminder;

    @Temporal(TemporalType.TIME)
    @Column(name = "STARTED_ON")
    private Date startedOn;

    @Temporal(TemporalType.TIME)
    @Column(name = "ENDED_ON")
    private Date endedOn;

    @ManyToOne
    @JoinColumn(name = "PATIENT_ID")
    private User patient;

    @ManyToOne
    @JoinColumn(name = "BRANCH_ID")
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "EXAM_ROOM_ID")
    private Room room;

    @Column(name = "IS_RECURRING")
    private Boolean recurring;

    @Column(name = "RECURRING_DAYS")
    private String recurringDays; //should be save json of days

    @Column(name = "FIRST_APPOINTMENT_ON")
    private Long firstAppointmentOn;

    @Column(name = "FOLLOW_UP_DATE")
    private Long followUpDate;

    @Column(name = "LAST_APPOINTMENT_ON")
    private Long lastAppointmentOn;



}
