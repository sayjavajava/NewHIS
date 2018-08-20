package com.sd.his.wrapper;

import java.util.Date;

/**
 * Created by jamal on 8/20/2018.
 */
public class BaseWrapper {


    private Long id;

    private Date createdOn;

    private Date updatedOn;

    public BaseWrapper() {
    }

    public BaseWrapper(Long id, Date createdOn, Date updatedOn) {
        this.id = id;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }
}
