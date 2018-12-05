package com.sd.his.wrapper;

import com.sd.his.model.Patient;
import com.sd.his.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;

public class PatientVitalWrapper extends BaseWrapper{


    @Autowired
    PatientRepository patientRepository;

    public PatientVitalWrapper(long id,String name, String unit, String standardValue, String currentValue, boolean status, String patient) {
        this.id=id;
        this.name = name;
        this.unit = unit;
        this.standardValue = standardValue;
        this.currentValue = currentValue;
        this.status = status;
        this.patientId = patient;
    }

    public PatientVitalWrapper(long id,String name, String unit, String standardValue, String currentValue, boolean status, Patient patient) {
        this.id=id;
        this.name = name;
        this.unit = unit;
        this.standardValue = standardValue;
        this.currentValue = currentValue;
        this.status = status;
        this.patientId = String.valueOf(patient.getId());
    }

    public PatientVitalWrapper(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long id;
    private String name;
    private String unit;
    private String  standardValue;
    private String   currentValue;
    private boolean status;

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    private String patientId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStandardValue() {
        return standardValue;
    }

    public void setStandardValue(String standardValue) {
        this.standardValue = standardValue;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }




    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }





}
