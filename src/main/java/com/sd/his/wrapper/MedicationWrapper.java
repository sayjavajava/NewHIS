package com.sd.his.wrapper;

import com.sd.his.model.Medication;
import com.sd.his.utill.HISCoreUtil;

/**
 * Created by jamal on 8/28/2018.
 */
public class MedicationWrapper extends BaseWrapper {


    private String drugName;
    private boolean prn;
    private String sigNote;
    private String indication;
    private String status;
    private long patientId;
    private long appointmentId = -1;
    private String datePrescribed;
    private String dateStartedTaking;
    private String dateStoppedTaking;
    private double dispenseQuantity;
    private String dispensePackage;
    private long numberRefill;
    private boolean daw;
    private String pharmacyNote;
    private String note;
    private String orderStatus;


    public MedicationWrapper() {
    }

    public MedicationWrapper(Medication medication) {

        super(medication.getId(),
                HISCoreUtil.convertDateAndTimeToString(medication.getCreatedOn()),
                HISCoreUtil.convertDateAndTimeToString(medication.getUpdatedOn()));
        this.drugName = medication.getDrugName();
        this.prn = medication.isPrn();
        this.sigNote = medication.getSigNote();
        this.indication = medication.getIndication();
        this.status = medication.getStatus();
        if (medication.getPatient() != null) {
            this.patientId = medication.getPatient().getId();
        }
        if (medication.getAppointment() != null) {
            this.appointmentId = medication.getAppointment().getId();
        }
        this.datePrescribed = HISCoreUtil.convertDateAndTimeToString(medication.getDatePrescribed());
        this.dateStartedTaking = HISCoreUtil.convertDateAndTimeToString(medication.getDateStartedTaking());
        this.dateStoppedTaking = HISCoreUtil.convertDateAndTimeToString(medication.getDateStoppedTaking());
        this.dispenseQuantity = medication.getDispenseQuantity();
        this.dispensePackage = medication.getDispensePackage();
        this.numberRefill = medication.getNumberRefill();
        this.daw = medication.isDaw();
        this.pharmacyNote = medication.getPharmacyNote();
        this.note = medication.getNote();
        this.orderStatus = medication.getOrderStatus();
    }

    public MedicationWrapper(Long id, String createdOn, String updatedOn) {
        super(id, createdOn, updatedOn);
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

    public void setStatus(String status) {
        this.status = status;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getDatePrescribed() {
        return datePrescribed;
    }

    public void setDatePrescribed(String datePrescribed) {
        this.datePrescribed = datePrescribed;
    }

    public String getDateStartedTaking() {
        return dateStartedTaking;
    }

    public void setDateStartedTaking(String dateStartedTaking) {
        this.dateStartedTaking = dateStartedTaking;
    }

    public String getDateStoppedTaking() {
        return dateStoppedTaking;
    }

    public void setDateStoppedTaking(String dateStoppedTaking) {
        this.dateStoppedTaking = dateStoppedTaking;
    }

    public double getDispenseQuantity() {
        return dispenseQuantity;
    }

    public void setDispenseQuantity(double dispenseQuantity) {
        this.dispenseQuantity = dispenseQuantity;
    }

    public String getDispensePackage() {
        return dispensePackage;
    }

    public void setDispensePackage(String dispensePackage) {
        this.dispensePackage = dispensePackage;
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
}
