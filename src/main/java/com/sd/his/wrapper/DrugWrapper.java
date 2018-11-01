package com.sd.his.wrapper;

import com.sd.his.utill.HISConstants;
import com.sd.his.utill.HISCoreUtil;

import java.util.Date;

/**
 * Created by jamal on 10/22/2018.
 */
public class DrugWrapper extends BaseWrapper {

    private String name;
    private String url;
    private long strengthMin;
    private long strengthMax;
    private String oral;
    private String frequency;
    private long duration;
    private long refill;
    private long days;
    private boolean sig;
    private String notes;
    private boolean active = true;
    private boolean hasChild;


    public DrugWrapper() {
    }

    public DrugWrapper(Long id, Date createdOn, Date updatedOn, String name, String url, long strengthMin, long strengthMax, String oral, String frequency,
                       long duration, long refill, long days, boolean sig, String notes, boolean active, boolean hasChild) {
        super(id,
                HISCoreUtil.convertDateToString(createdOn, HISConstants.DATE_FORMATE_YYY_MM_dd),
                HISCoreUtil.convertDateToString(updatedOn, HISConstants.DATE_FORMATE_YYY_MM_dd));
        this.name = name;
        this.url = url;
        this.strengthMin = strengthMin;
        this.strengthMax = strengthMax;
        this.oral = oral;
        this.frequency = frequency;
        this.duration = duration;
        this.refill = refill;
        this.days = days;
        this.sig = sig;
        this.notes = notes;
        this.active = active;
        this.hasChild = hasChild;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStrengthMin() {
        return strengthMin;
    }

    public void setStrengthMin(long strengthMin) {
        this.strengthMin = strengthMin;
    }

    public long getStrengthMax() {
        return strengthMax;
    }

    public void setStrengthMax(long strengthMax) {
        this.strengthMax = strengthMax;
    }

    public String getOral() {
        return oral;
    }

    public void setOral(String oral) {
        this.oral = oral;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getRefill() {
        return refill;
    }

    public void setRefill(long refill) {
        this.refill = refill;
    }

    public long getDays() {
        return days;
    }

    public void setDays(long days) {
        this.days = days;
    }

    public boolean isSig() {
        return sig;
    }

    public void setSig(boolean sig) {
        this.sig = sig;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isHasChild() {
        return hasChild;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
