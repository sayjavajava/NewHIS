package com.sd.his.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "zone")
public class Zone implements Serializable {
    @Id
    @Column(name = "zone_id")
    private long zoneId;

    @Column(name = "zone_name")
    private String name;

    @Column(name="zone_time")
    private String zoneTime;


    public long getZoneId() {
        return zoneId;
    }

    public void setZoneId(long zoneId) {
        this.zoneId = zoneId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZoneTime() {
        return zoneTime;
    }

    public void setZoneTime(String zoneTime) {
        this.zoneTime = zoneTime;
    }
}
