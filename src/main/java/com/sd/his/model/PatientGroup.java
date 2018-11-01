package com.sd.his.model;

import com.sd.his.wrapper.DrugWrapper;
import com.sd.his.wrapper.PatientGroupWrapper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by jamal on 10/23/2018.
 */
@Entity
@Table(name = "patient_group")
public class PatientGroup extends BaseEntity {

    private String name;
    @Column(name = "description", length = 4000)
    private String description;
    private boolean status;

    public PatientGroup() {
    }


    public PatientGroup(PatientGroupWrapper patientGroupWrapper) {
        this.name = patientGroupWrapper.getName();
        this.description = patientGroupWrapper.getDescription();
        this.status = patientGroupWrapper.isStatus();
    }

    public PatientGroup(PatientGroup patientGroup, PatientGroupWrapper patientGroupWrapper) {
        patientGroup.name = patientGroupWrapper.getName();
        patientGroup.description = patientGroupWrapper.getDescription();
        patientGroup.status = patientGroupWrapper.isStatus();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
