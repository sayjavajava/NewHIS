package com.sd.his.request;

public class ICDCreateRequest {

    boolean status;
    long selectedICDVersion;
    String code;
    String title;

    public ICDCreateRequest() {
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public long getSelectedICDVersion() {
        return selectedICDVersion;
    }

    public void setSelectedICDVersion(long selectedICDVersion) {
        this.selectedICDVersion = selectedICDVersion;
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
}
