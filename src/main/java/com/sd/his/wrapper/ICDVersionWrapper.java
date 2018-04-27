package com.sd.his.wrapper;

import com.sd.his.model.ICDVersion;

public class ICDVersionWrapper {

    private long id;
    private String name;
    private boolean active;
    private boolean deleted;
    private long updatedOn;
    private long createdOn;
    private ICDVersion iCDVersion;

    public ICDVersionWrapper() {
    }

    public ICDVersionWrapper(ICDVersion iCDVersion) {
        this.id = iCDVersion.getId();
        this.name = iCDVersion.getName();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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
}
