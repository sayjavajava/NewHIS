package com.sd.his.wrapper;

import com.sd.his.utill.HISConstants;
import com.sd.his.utill.HISCoreUtil;

import java.util.Date;

/**
 * Created by jamal on 9/3/2018.
 */
public class DocumentWrapper extends BaseWrapper {


    private long patientId;
    private String name;
    private String type;
    private String description;
    private String url;
    private byte[] image;

    public DocumentWrapper() {
    }

    public DocumentWrapper(Long id, Date createdOn, Date updatedOn, String name, String type, String description, String url, long patientId) {
        super(id,
                HISCoreUtil.convertDateToString(createdOn, HISConstants.DATE_FORMATE_YYY_MM_DD_T_HH_MM),
                HISCoreUtil.convertDateToString(updatedOn, HISConstants.DATE_FORMATE_YYY_MM_DD_T_HH_MM));
        this.patientId = patientId;
        this.name = name;
        this.type = type;
        this.description = description;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
