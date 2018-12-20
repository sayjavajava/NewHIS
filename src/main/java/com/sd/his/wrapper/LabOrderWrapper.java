package com.sd.his.wrapper;

import com.sd.his.model.Appointment;
import com.sd.his.model.Patient;

import java.util.Date;
import java.util.List;

public class LabOrderWrapper {



    //create wrapper
    private long id;
    private String orderStatus;
    private String doctorSignOff;
    private String comments;
    private LabTest[] labTest;
    private List<LabTest> labTestVales;
    private String orderTestDate;
    private Long patientId;
    private Long appointmentId;
    private Patient patient;



    private  Appointment appointment;

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    private Date testDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LabOrderWrapper() {
    }

    public LabOrderWrapper(String orderStatus, String comments, Date orderTestDate, Patient patientId, Appointment appointmentId) {
        this.orderStatus = orderStatus;
     //   this.doctorSignOff = doctorSignOff;
        this.comments = comments;
       // this.labTestVales = labTest;
     //   this.orderTestDate = orderTestDate;
        this.patient = patientId;
        this.appointment = appointmentId;
       this.testDate = orderTestDate;
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



    public void setLabTest(List<LabTest> labTest) {
        this.labTestVales = labTest;
    }



    public List<LabTest> getLabTestVales() {
        return labTestVales;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}

