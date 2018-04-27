package com.sd.his.wrapper;

public class ICDWrapper {


    private long id;
    private String codeVersion;
    private String code;
    private String title;
    private boolean status;
    private boolean deleted;
    private long updatedOn;
    private long createdOn;
    private ICDVersionWrapper iCDVersionWrapper;


    public ICDWrapper() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCodeVersion() {
        return codeVersion;
    }

    public void setCodeVersion(String codeVersion) {
        this.codeVersion = codeVersion;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public long getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(long updatedOn) {
        this.updatedOn = updatedOn;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public ICDVersionWrapper getICDVersionWrapper() {
        return iCDVersionWrapper;
    }

    public void setICDVersionWrapper(ICDVersionWrapper iCDVersionWrapper) {
        this.iCDVersionWrapper = iCDVersionWrapper;
    }
}
