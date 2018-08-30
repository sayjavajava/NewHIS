package com.sd.his.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sd.his.utill.DateTimeUtil;
import com.sd.his.utill.HISConstants;
import com.sd.his.utill.HISCoreUtil;
import com.sd.his.wrapper.MedicationWrapper;

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
@Table(name = "MEDICATION")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Medication extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "DRUG_NAME")
    private String drugName;

    @Column(name = "PRN")
    private boolean prn;

    @Column(name = "SIG_NOTE")
    private String sigNote;

    @Column(name = "INDICATION")
    private String indication;

    @Column(name = "STATUS")
    private String  status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PATIENT_ID")
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPOINTMENT_ID")
    private Appointment appointment;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_PRESCRIBED")
    private Date datePrescribed;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_STARTED_TAKING")
    private Date dateStartedTaking;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_STOPPED_TAKING")
    private Date dateStoppedTaking;

    @Column(name = "DISPENSE_QUANTITY")
    private double dispenseQuantity;

    @Column(name = "DISPENSE_PACKAGE")
    private String dispensePackage;

    @Column(name = "NUMBER_REFILL")
    private long numberRefill;

    @Column(name = "DAW")
    private boolean daw;

    @Column(name = "PHARMACY_NOTE")
    private String pharmacyNote;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "ORDER_STATUS")
    private String orderStatus;

    public Medication() {
    }

    public Medication(MedicationWrapper medicationWrapper) throws ParseException {
        this.drugName = medicationWrapper.getDrugName();
        this.prn = medicationWrapper.isPrn();
        this.sigNote = medicationWrapper.getSigNote();
        this.indication = medicationWrapper.getIndication();
        this.status = medicationWrapper.getStatus();
        this.datePrescribed = HISCoreUtil.convertToDate(medicationWrapper.getDatePrescribed());
        this.dateStartedTaking = HISCoreUtil.convertToDate(medicationWrapper.getDateStartedTaking());
        this.dateStoppedTaking = HISCoreUtil.convertToDate(medicationWrapper.getDateStoppedTaking());
        this.dispenseQuantity = medicationWrapper.getDispenseQuantity();
        this.dispensePackage = medicationWrapper.getDispensePackage();
        this.numberRefill = medicationWrapper.getNumberRefill();
        this.daw = medicationWrapper.isDaw();
        this.pharmacyNote = medicationWrapper.getPharmacyNote();
        this.note = medicationWrapper.getNote();
        this.orderStatus = medicationWrapper.getOrderStatus();
    }

    public Medication(Medication medication, MedicationWrapper medicationWrapper) throws ParseException {
        medication.drugName = medicationWrapper.getDrugName();
        medication.prn = medicationWrapper.isPrn();
        medication.sigNote = medicationWrapper.getSigNote();
        medication.indication = medicationWrapper.getIndication();
        medication.status = medicationWrapper.getStatus();
        medication.datePrescribed = HISCoreUtil.convertToDate(medicationWrapper.getDatePrescribed());
        medication.dateStartedTaking = HISCoreUtil.convertToDate(medicationWrapper.getDateStartedTaking());
        medication.dateStoppedTaking = HISCoreUtil.convertToDate(medicationWrapper.getDateStoppedTaking());
        medication.dispenseQuantity = medicationWrapper.getDispenseQuantity();
        medication.dispensePackage = medicationWrapper.getDispensePackage();
        medication.numberRefill = medicationWrapper.getNumberRefill();
        medication.daw = medicationWrapper.isDaw();
        medication.pharmacyNote = medicationWrapper.getPharmacyNote();
        medication.note = medicationWrapper.getNote();
        medication.orderStatus = medicationWrapper.getOrderStatus();
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public boolean isPrn() {
        return prn;
    }

    public void setPrn(boolean prn) {
        this.prn = prn;
    }

    public String getSigNote() {
        return sigNote;
    }

    public void setSigNote(String sigNote) {
        this.sigNote = sigNote;
    }

    public String getIndication() {
        return indication;
    }

    public void setIndication(String indication) {
        this.indication = indication;
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

    public Date getDatePrescribed() {
        return datePrescribed;
    }

    public void setDatePrescribed(Date datePrescribed) {
        this.datePrescribed = datePrescribed;
    }

    public Date getDateStartedTaking() {
        return dateStartedTaking;
    }

    public void setDateStartedTaking(Date dateStartedTaking) {
        this.dateStartedTaking = dateStartedTaking;
    }

    public Date getDateStoppedTaking() {
        return dateStoppedTaking;
    }

    public void setDateStoppedTaking(Date dateStoppedTaking) {
        this.dateStoppedTaking = dateStoppedTaking;
    }

    public double getDispenseQuantity() {
        return dispenseQuantity;
    }

    public void setDispenseQuantity(double dispenseQuantity) {
        this.dispenseQuantity = dispenseQuantity;
    }

    public long getNumberRefill() {
        return numberRefill;
    }

    public void setNumberRefill(long numberRefill) {
        this.numberRefill = numberRefill;
    }

    public boolean isDaw() {
        return daw;
    }

    public void setDaw(boolean daw) {
        this.daw = daw;
    }

    public String getPharmacyNote() {
        return pharmacyNote;
    }

    public void setPharmacyNote(String pharmacyNote) {
        this.pharmacyNote = pharmacyNote;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDispensePackage() {
        return dispensePackage;
    }

    public void setDispensePackage(String dispensePackage) {
        this.dispensePackage = dispensePackage;
    }
}
