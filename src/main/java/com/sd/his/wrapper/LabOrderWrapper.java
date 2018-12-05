package com.sd.his.wrapper;

import java.util.Date;
import java.util.List;

public class LabOrderWrapper {

    //create wrapper
    private String orderStatus;
    private String doctorSignOff;
    private String comments;
    private LabTest[] labTest;
    private String orderTestDate;
    private Long patientId;
    private Long appointmentId;

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    private Date testDate;



    public LabOrderWrapper() {
    }


    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public LabTest[] getLabTest() {
        return labTest;
    }

    public void setLabTest(LabTest[] labTest) {
        this.labTest = labTest;
    }

    public String getOrderTestDate() {
        return orderTestDate;
    }

    public void setOrderTestDate(String orderTestDate) {
        this.orderTestDate = orderTestDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getDoctorSignOff() {
        return doctorSignOff;
    }

    public void setDoctorSignOff(String doctorSignOff) {
        this.doctorSignOff = doctorSignOff;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}

