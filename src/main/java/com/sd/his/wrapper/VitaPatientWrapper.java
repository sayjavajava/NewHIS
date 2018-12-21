package com.sd.his.wrapper;

public class VitaPatientWrapper {
    String currentValue;
    long id;
    String name;
    boolean status;
    String unit;
    public VitaPatientWrapper(){

    }
    public VitaPatientWrapper(String currentValue, long id, String name, boolean status, String unit) {
        this.currentValue = currentValue;
        this.id = id;
        this.name = name;
        this.status = status;
        this.unit = unit;
    }

    public VitaPatientWrapper(String name,String unit,boolean status, long id,String currentValue) {
        this.currentValue = currentValue;
        this.id = id;
        this.name = name;
        this.status = status;
        this.unit = unit;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }


}
