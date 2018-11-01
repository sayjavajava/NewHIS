package com.sd.his.wrapper;

import com.sd.his.utill.HISCoreUtil;

import java.util.Date;

/**
 * Created by jamal on 10/23/2018.
 */
public class PatientGroupWrapper extends BaseWrapper {
    private String name;
    private String description;
    private boolean status;
    private boolean hasChild;
    private long patientCount;

    public PatientGroupWrapper() {
    }

    public PatientGroupWrapper(Long id, Date createdOn, Date updatedOn, String name, String description, boolean status, boolean hasChild) {
        super(id,
                HISCoreUtil.convertDateToString(createdOn),
                HISCoreUtil.convertDateToString(updatedOn));
        this.name = name;
        this.description = description;
        this.status = status;
        this.hasChild = hasChild;
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

    public boolean isHasChild() {
        return hasChild;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }

    public long getPatientCount() {
        return patientCount;
    }

    public void setPatientCount(long patientCount) {
        this.patientCount = patientCount;
    }
}
