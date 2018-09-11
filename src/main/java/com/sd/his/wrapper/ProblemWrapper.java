package com.sd.his.wrapper;

import org.codehaus.jackson.map.Serializers;

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
public class ProblemWrapper extends BaseWrapper {

    private long appointmentId = -1;

    private long selectedCodeId = -1;
    private String codeName;
    private String codeTitle;

    private long selectedICDVersionId = -1;
    private String versionName;

    private String dateDiagnosis;
    private String note;
    private String status = "ACTIVE";
    private long patientId;

    public ProblemWrapper() {
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

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public void setAppointmentId(long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public long getAppointmentId() {
        return appointmentId;
    }
}
