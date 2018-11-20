package com.sd.his.wrapper;

public class ServiceComission {

    private long id;
    private boolean checked ;
    private String comission;

    public ServiceComission(long id, boolean checked, String comission) {
        this.id = id;
        this.checked = checked;
        this.comission = comission;
    }

    public ServiceComission() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getComission() {
        return comission;
    }

    public void setComission(String comission) {
        this.comission = comission;
    }
}
