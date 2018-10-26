package com.sd.his.model;

import com.sd.his.wrapper.DrugWrapper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by jamal on 10/22/2018.
 */
@Entity
@Table(name = "drug")
public class Drug extends BaseEntity {

    private String name;
    private String url;
    @Column(name = "strength_min")
    private long strengthMin;
    @Column(name = "strength_max")
    private long strengthMax;
    private String oral;
    private String frequency;
    private long duration;
    private long refill;
    private long days;
    private boolean sig;
    private String notes;
    private boolean active;

    public Drug() {
    }

    public Drug(DrugWrapper drugWrapper) {
        this.name = drugWrapper.getName();
        this.url = drugWrapper.getUrl();
        this.strengthMin = drugWrapper.getStrengthMin();
        this.strengthMax = drugWrapper.getStrengthMax();
        this.oral = drugWrapper.getOral();
        this.frequency = drugWrapper.getFrequency();
        this.duration = drugWrapper.getDuration();
        this.refill = drugWrapper.getRefill();
        this.days = drugWrapper.getDays();
        this.sig = drugWrapper.isSig();
        this.notes = drugWrapper.getNotes();
        this.active = drugWrapper.isActive();
    }

    public Drug(Drug drug, DrugWrapper drugWrapper) {
        drug.name = drugWrapper.getName();
        drug.url = drugWrapper.getUrl();
        drug.strengthMin = drugWrapper.getStrengthMin();
        drug.strengthMax = drugWrapper.getStrengthMax();
        drug.oral = drugWrapper.getOral();
        drug.frequency = drugWrapper.getFrequency();
        drug.duration = drugWrapper.getDuration();
        drug.refill = drugWrapper.getRefill();
        drug.days = drugWrapper.getDays();
        drug.sig = drugWrapper.isSig();
        drug.notes = drugWrapper.getNotes();
        drug.active = drugWrapper.isActive();
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
}
