package com.sd.his.wrapper;

import com.sd.his.model.Branch;
import com.sd.his.model.Room;
import com.sd.his.model.User;

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
 * @Package   : com.sd.his.wrapper
 * @FileName  : AppointmentWrapper
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
public class AppointmentWrapper {

    private Long id;
    private String name;
    private String notes;
    private String reason;
    private String color;
    private String status;//AppointmentStatusTypeEnum
    private String type;//AppointmentTypeEnum
    private long duration; //minutes
    private Boolean followUpReminder;
    private String followUpReasonReminder;
    private long startedOn;
    private long endedOn;
    private long createdOn;
    private long updatedOn;
    private boolean recurring;
    private String recurringDays; //should be save json of days
    private long recurringPeriod;
    private long firstAppointmentOn;
    private long lastAppointmentOn;
    private long patientId;
    private String patientFirstName;
    private String patientLastName;
    private long branchId;
    private String branchName;
    private long roomId;
    private String roomName;

    public AppointmentWrapper() {
    }

    public AppointmentWrapper(long id, String name, String notes, String reason, String color, String status, String type,
                              long duration, Boolean followUpReminder, String followUpReasonReminder, long startedOn,
                              long endedOn, long createdOn, long updatedOn, boolean recurring, String recurringDays, long recurringPeriod,
                              long firstAppointmentOn, long lastAppointmentOn, long patientId, String patientFirstName,
                              String patientLastName, long branchId, String branchName, long roomId, String roomName) {
        this.id = id;
        this.name = name;
        this.notes = notes;
        this.reason = reason;
        this.color = color;
        this.status = status;
        this.type = type;
        this.duration = duration;
        this.followUpReminder = followUpReminder;
        this.followUpReasonReminder = followUpReasonReminder;
        this.startedOn = startedOn;
        this.endedOn = endedOn;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.recurring = recurring;
        this.recurringDays = recurringDays;
        this.recurringPeriod = recurringPeriod;
        this.firstAppointmentOn = firstAppointmentOn;
        this.lastAppointmentOn = lastAppointmentOn;
        this.patientId = patientId;
        this.patientFirstName = patientFirstName;
        this.patientLastName = patientLastName;
        this.branchId = branchId;
        this.branchName = branchName;
        this.roomId = roomId;
        this.roomName = roomName;
    }

    @Override
    public String toString() {
        return "AppointmentWrapper{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", notes='" + notes + '\'' +
                ", reason='" + reason + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", duration=" + duration +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Boolean getFollowUpReminder() {
        return followUpReminder;
    }

    public void setFollowUpReminder(Boolean followUpReminder) {
        this.followUpReminder = followUpReminder;
    }

    public String getFollowUpReasonReminder() {
        return followUpReasonReminder;
    }

    public void setFollowUpReasonReminder(String followUpReasonReminder) {
        this.followUpReasonReminder = followUpReasonReminder;
    }

    public long getStartedOn() {
        return startedOn;
    }

    public void setStartedOn(long startedOn) {
        this.startedOn = startedOn;
    }

    public long getEndedOn() {
        return endedOn;
    }

    public void setEndedOn(long endedOn) {
        this.endedOn = endedOn;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public long getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(long updatedOn) {
        this.updatedOn = updatedOn;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }

    public String getRecurringDays() {
        return recurringDays;
    }

    public void setRecurringDays(String recurringDays) {
        this.recurringDays = recurringDays;
    }

    public long getRecurringPeriod() {
        return recurringPeriod;
    }

    public void setRecurringPeriod(long recurringPeriod) {
        this.recurringPeriod = recurringPeriod;
    }

    public long getFirstAppointmentOn() {
        return firstAppointmentOn;
    }

    public void setFirstAppointmentOn(long firstAppointmentOn) {
        this.firstAppointmentOn = firstAppointmentOn;
    }

    public long getLastAppointmentOn() {
        return lastAppointmentOn;
    }

    public void setLastAppointmentOn(long lastAppointmentOn) {
        this.lastAppointmentOn = lastAppointmentOn;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    public long getBranchId() {
        return branchId;
    }

    public void setBranchId(long branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
