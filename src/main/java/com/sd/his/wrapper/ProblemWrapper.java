package com.sd.his.wrapper;

import com.sd.his.enums.StatusEnum;
import com.sd.his.model.Appointment;

/**
 * Created by jamal on 8/13/2018.
 *
 * @version : ver. 1.0.0
 *          <p>
 *          ________________________________________________________________________________________________
 *          <p>
 *          Developer				Date		     Version		Operation		Description
 *          ________________________________________________________________________________________________
 *          <p>
 *          <p>
 *          ________________________________________________________________________________________________
 * @Project : HIS
 * @Package : com.sd.his.Wrapper
 * @FileName : ProblemWrapper
 * <p>
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 */
public class ProblemWrapper {

    private Long id;
    private long selectedCodeId = -1;
    private String codeName;
    private String codeTitle;
    private long selectedICDVersionId = -1;
    private String versionName;

    private String dateDiagnosis;
    private String note;
    private String status = "ACTIVE";
    private AppointmentWrapper appointmentWrapper = new AppointmentWrapper();

    public ProblemWrapper() {
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }


    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getDateDiagnosis() {
        return dateDiagnosis;
    }

    public void setDateDiagnosis(String dateDiagnosis) {
        this.dateDiagnosis = dateDiagnosis;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getSelectedCodeId() {
        return selectedCodeId;
    }

    public void setSelectedCodeId(long selectedCodeId) {
        this.selectedCodeId = selectedCodeId;
    }

    public long getSelectedICDVersionId() {
        return selectedICDVersionId;
    }

    public void setSelectedICDVersionId(long selectedICDVersionId) {
        this.selectedICDVersionId = selectedICDVersionId;
    }

    public String getCodeTitle() {
        return codeTitle;
    }

    public void setCodeTitle(String codeTitle) {
        this.codeTitle = codeTitle;
    }

    public AppointmentWrapper getAppointmentWrapper() {
        return appointmentWrapper;
    }

    public void setAppointmentWrapper(AppointmentWrapper appointmentWrapper) {
        this.appointmentWrapper = appointmentWrapper;
    }
}
